package com.management.user.controllers;

//
import com.management.user.dto.UserDto;
import com.management.user.exceptions.UserNotFoundException;

import com.management.user.Repository.UserRepository;
import com.management.user.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

//

@RestController
@AllArgsConstructor
@RequestMapping(path="/demo")
public class UserController {
    @Autowired
    private UserRepository userRepository;
//
    @Autowired
    private PasswordEncoder passwordEncoder;

    private UserService userService;

    @PostMapping(path="/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        userDto.setPasswordHash(passwordEncoder.encode(userDto.getPasswordHash()));
        UserDto createdUserDto = userService.createUser(userDto);
        return new ResponseEntity<>(createdUserDto, HttpStatus.CREATED);
    }

    @GetMapping(path = "/users")
    public ResponseEntity<List<UserDto>> getAllUsers(){
        List<UserDto> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/user/{email}")
    public ResponseEntity<UserDto> getUserByEmail(@PathVariable String email) {
        try {
            UserDto user = userService.getUserByEmail(email);
            return ResponseEntity.ok(user);
        } catch (UserNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }


    @PutMapping("/user/{email}")
    public ResponseEntity<UserDto> updateUserByEmail(@PathVariable String email, @RequestBody UserDto userDto) {
        UserDto updatedUserDto = userService.updateUser(userDto, email);
        if (updatedUserDto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedUserDto);
    }



    @DeleteMapping("/user/delete")
    public ResponseEntity<Void> deleteUserByEmail(@RequestBody UserDto userDto) {
        try {
            userService.deleteUser(userDto);
            return ResponseEntity.noContent().build(); // User deleted successfully
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build(); // User not found
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // Other errors
        }
    }



}




