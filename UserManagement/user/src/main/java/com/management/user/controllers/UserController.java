package com.management.user.controllers;

import com.management.user.Request.PatientRequest;
import com.management.user.Request.PsychologistRequest;
import com.management.user.dto.LoginRequestDto;
import com.management.user.dto.LoginResponseDto;
import com.management.user.dto.UserDto;
import com.management.user.dto.ValidateTokenRequestDto;
import com.management.user.repository.UserRepository;
import com.management.user.security.JwtTokenHelper;
import com.management.user.service.RoleService;
import com.management.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;

//
@RefreshScope
@RestController
@AllArgsConstructor
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private JwtTokenHelper tokenHelper;

    @PostMapping(path = "/login")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto) {
        var user = userService.getUserByEmail(loginRequestDto.getEmail());
        var userRole = roleService.getRoleById(user.getRoleId());
        String token = tokenHelper.generateToken(user, userRole);
        return ResponseEntity.ok(new LoginResponseDto(token));
    }

    @PostMapping(path="/validate-token")
    public ResponseEntity<Void> validateToken(
            @RequestBody ValidateTokenRequestDto validateTokenRequestDto) {
        try {
            var attributes = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes());
            var req = attributes.getRequest();
            var authHeader = req.getHeader("Authorization");
            var token = authHeader != null ? authHeader.substring(7) : "";
            if (tokenHelper.validateTokenAndItsClaims(token, validateTokenRequestDto.getRoles())) {
                return ResponseEntity.status(HttpStatus.ACCEPTED).build();
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping(path="/registerPatient")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UserDto> createPatient(@RequestBody UserDto userDto, @RequestParam int age) {
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        UserDto createdUserDto = userService.createPatient(userDto);
        String urlPatient = "http://appointmentservice/patients/save";

        String userId = createdUserDto.getUserId();

        PatientRequest patientRequest = new PatientRequest();
        patientRequest.setUserId(userId);
        patientRequest.setAge(age);
        patientRequest.setSelectedPsychologistId(null);
       // return new ResponseEntity<>(patientRequest, HttpStatus.CREATED);
        restTemplate.postForObject(urlPatient,patientRequest, PatientRequest.class);
        return new ResponseEntity<>(createdUserDto, HttpStatus.CREATED);
    }

    @GetMapping("/userId/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable String id) {
        UserDto user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }
    @PostMapping(path = "/registerPsychologist")
        @ResponseStatus(HttpStatus.CREATED)
        public ResponseEntity<UserDto> createPsychologist(@RequestBody UserDto userDto) {
            userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
            UserDto createdUserDto = userService.createPsychologist(userDto);

            String urlPsychologist = "http://appointmentservice/psychologists/save";
        PsychologistRequest psychologistRequest = new PsychologistRequest();
        psychologistRequest.setUserId(createdUserDto.getUserId());
        restTemplate.postForObject(urlPsychologist,psychologistRequest,PsychologistRequest.class);


            return new ResponseEntity<>(createdUserDto, HttpStatus.CREATED);
    }

    @GetMapping(path = "/users")
    public ResponseEntity<List<UserDto>> getAllUsers(){
        List<UserDto> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/user/{email}")
    public ResponseEntity<UserDto> getUserByEmail(@PathVariable String email) {
        UserDto user = userService.getUserByEmail(email);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/user/{email}")
    public ResponseEntity<UserDto> updateUserByEmail(@PathVariable String email, @RequestBody UserDto userDto) {
        UserDto updatedUserDto = userService.updateUser(userDto, email);

        return ResponseEntity.ok(updatedUserDto);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteUserByEmail(@RequestBody UserDto userDto) {

            userService.deleteUser(userDto);
            return ResponseEntity.noContent().build(); // User deleted successfully

    }

    @GetMapping("/search")
    public ResponseEntity<List<UserDto>> searchUsersByName(@RequestParam String name) {
        List<UserDto> users = userService.searchUsersByName(name);
        return ResponseEntity.ok(users);
    }

}




