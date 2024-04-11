package com.management.user.controllers;

//
import com.management.user.Request.PatientRequest;
import com.management.user.dto.UserDto;
import com.management.user.exceptions.UserNotFoundException;

import com.management.user.mapper.UserMapper;
import com.management.user.models.UserEntity;
import com.management.user.repository.UserRepository;
import com.management.user.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

//
@RefreshScope
@RestController
@AllArgsConstructor
public class UserController {
    @Autowired
    private UserRepository userRepository;
//
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserService userService;

    @Autowired
    private RestTemplate restTemplate;

    @PostMapping(path="/registerPatient/{age}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UserDto> createPatient(@RequestBody UserDto userDto, @RequestParam int age) {
        userDto.setPasswordHash(passwordEncoder.encode(userDto.getPasswordHash()));
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
            userDto.setPasswordHash(passwordEncoder.encode(userDto.getPasswordHash()));
            UserDto createdUserDto = userService.createPsychologist(userDto);

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




