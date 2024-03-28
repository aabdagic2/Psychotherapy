package com.management.user.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.management.user.Repository.UserRepository;
import com.management.user.controllers.UserController;
import com.management.user.dto.UserDto;
import com.management.user.mapper.UserMapper;
import com.management.user.models.UserEntity;
import com.management.user.service.UserService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;



import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;




@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class UserControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    private UserEntity userEntity;

    @BeforeEach
    public void init() {
        userEntity = new UserEntity();
        userEntity.setEmail("user@example.com");
        userEntity.setPasswordHash("password");
        userEntity.setType("guest");
        userEntity.setName("John Doe");
    }



    @Test
    public void UserController_GetAllUsers_ReturnList() throws Exception {
        List<UserEntity> userList = Collections.singletonList(userEntity);
        given(userService.getAllUsers()).willReturn(userList.stream().map(UserMapper::mapToUserDto).collect(Collectors.toList()));

        mockMvc.perform(get("/demo/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].email", CoreMatchers.is(userEntity.getEmail())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].type", CoreMatchers.is(userEntity.getType())));
    }



    @Test
    public void UserController_GetUserByEmail_ReturnUserDto() throws Exception {
        given(userService.getUserByEmail(userEntity.getEmail())).willReturn(UserMapper.mapToUserDto(userEntity));

        mockMvc.perform(get("/demo/user/{email}", userEntity.getEmail())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", CoreMatchers.is(userEntity.getEmail())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.type", CoreMatchers.is(userEntity.getType())));
    }

    @Test
    public void UserController_UpdateUserByEmail_ReturnUpdatedUserDto() throws Exception {
        given(userService.updateUser(any(), ArgumentMatchers.eq(userEntity.getEmail())))
                .willAnswer(invocation -> {
                    UserDto userDto = invocation.getArgument(0);
                    userDto.setEmail(userEntity.getEmail()); // Ensure email remains the same
                    return userDto;
                });

        mockMvc.perform(put("/demo/user/{email}", userEntity.getEmail())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(UserMapper.mapToUserDto(userEntity))))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", CoreMatchers.is(userEntity.getEmail())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.type", CoreMatchers.is(userEntity.getType())));
    }

    @Test
    public void UserController_DeleteUserByEmail_ReturnNoContent() throws Exception {
        String email = "admin@example.com";

        mockMvc.perform(delete("/demo/user/{email}", email)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }


}



