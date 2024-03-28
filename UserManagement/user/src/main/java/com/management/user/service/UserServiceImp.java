package com.management.user.service;

import com.management.user.Repository.UserRepository;
import com.management.user.Repository.UserRoleRepository;
import com.management.user.dto.UserDto;
import com.management.user.exceptions.UserNotFoundException;
import com.management.user.mapper.UserMapper;
import com.management.user.models.UserEntity;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImp implements UserService {

    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    private UserRoleRepository userRoleRepository;


    @Override
    public UserDto createUser(UserDto userDto) {
        UserEntity user = UserMapper.mapToUser(userDto);

        UserEntity savedUser = userRepository.save(user);

        // Convert User JPA entity to UserDto
        UserDto savedUserDto;
        savedUserDto = UserMapper.mapToUserDto(savedUser);

        return savedUserDto;
    }
    @Override
    public List<UserDto> getAllUsers() {
        Iterable<UserEntity> users = userRepository.findAll();
        List<UserDto> userDtos = new ArrayList<>();
        for (UserEntity userEntity : users) {
            userDtos.add(UserMapper.mapToUserDto(userEntity));
        }
        return userDtos;
   //     return null;
   }


    @Override
    public UserDto getUserByEmail(String email) {
        Optional<UserEntity> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            UserEntity user = optionalUser.get();
            return UserMapper.mapToUserDto(user);
        }
        throw new UserNotFoundException("User not found with email: " + email);
    }

    @Override
    public UserDto updateUser(UserDto userDto, String email) {
        Optional<UserEntity> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            UserEntity user = optionalUser.get();
            user.setType(userDto.getType());
            user.setName(userDto.getName());
            user.setPasswordHash(passwordEncoder.encode(userDto.getPasswordHash()));
            UserEntity updatedUser = userRepository.save(user);
            return UserMapper.mapToUserDto(updatedUser);
        }
        throw new UserNotFoundException("User not found with email: " + email);


    }



//    @Override
//    public void deleteUser(String email) {
//        Optional<UserEntity> optionalUser = userRepository.findByEmail(email);
//        if (optionalUser.isPresent()) {
//            userRepository.delete(optionalUser.get());
//        } else {
//            throw new EntityNotFoundException("User not found with email: " + email);
//        }
//    }



    @Override
    public void deleteUser(UserDto userDto) {
        Optional<UserEntity> optionalUser = userRepository.findByEmail(userDto.getEmail());
        if (optionalUser.isPresent()) {
            UserEntity user = optionalUser.get();
            userRoleRepository.deleteByUser(user); // Delete user's roles from the intermediate table
            userRepository.delete(user); // Delete the user
        } else {
            throw new EntityNotFoundException("User not found with email: " + userDto.getEmail());
        }
    }






}
