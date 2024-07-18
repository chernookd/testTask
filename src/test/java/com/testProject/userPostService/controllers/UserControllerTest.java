package com.testProject.userPostService.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.testProject.userPostService.controllers.dto.PostDto;
import com.testProject.userPostService.controllers.dto.UserDto;
import com.testProject.userPostService.controllers.exception.InvalidUserException;
import com.testProject.userPostService.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetAllUsers() throws Exception {
        List<UserDto> users = Arrays.asList(new UserDto(1L, "User1", "user1@example.com", Collections.emptyList()));
        when(userService.getAllUsers()).thenReturn(users);

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("User1"))
                .andExpect(jsonPath("$[0].email").value("user1@example.com"));

        verify(userService).getAllUsers();
    }

    @Test
    void testGetUserById() throws Exception {
        UserDto user = new UserDto(1L, "User1", "user1@example.com", Collections.emptyList());
        when(userService.getUserById(1L)).thenReturn(user);

        mockMvc.perform(get("/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("User1"))
                .andExpect(jsonPath("$.email").value("user1@example.com"));

        verify(userService).getUserById(1L);
    }

    @Test
    void testGetUserById_NotFound() throws Exception {
        when(userService.getUserById(1L)).thenThrow(new InvalidUserException("User not found"));

        mockMvc.perform(get("/users/1"))
                .andExpect(status().isBadRequest());

        verify(userService).getUserById(1L);
    }

    @Test
    void testCreateUser() throws Exception {
        UserDto newUser = new UserDto(null, "New User", "newuser@example.com", Collections.emptyList());
        UserDto createdUser = new UserDto(1L, "New User", "newuser@example.com", Collections.emptyList());

        when(userService.createUser(any(UserDto.class))).thenReturn(createdUser);

        mockMvc.perform(post("/createuser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("New User"))
                .andExpect(jsonPath("$.email").value("newuser@example.com"));

        verify(userService).createUser(any(UserDto.class));
    }


    @Test
    void testDeleteUser() throws Exception {
        long userId = 1L;

        mockMvc.perform(delete("/deleteuser/{id}", userId))
                .andExpect(status().isOk());

        verify(userService).deleteUser(userId);
    }

    @Test
    void testUpdateUser() throws Exception {
        long userId = 1L;
        UserDto updatedUserDto = new UserDto(userId, "Updated User", "updateduser@example.com", Collections.emptyList());

        when(userService.updateUser(eq(userId), any(UserDto.class))).thenReturn(updatedUserDto);

        mockMvc.perform(put("/update/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedUserDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userId))
                .andExpect(jsonPath("$.name").value("Updated User"))
                .andExpect(jsonPath("$.email").value("updateduser@example.com"));

        verify(userService).updateUser(eq(userId), any(UserDto.class));
    }

    @Test
    void testGetPostsByUserId() throws Exception {
        long userId = 1L;
        List<PostDto> posts = Arrays.asList(new PostDto(1L, "Title1", "Content1", new UserDto(), null, null));

        when(userService.getAllPostsByUserId(userId)).thenReturn(posts);

        mockMvc.perform(get("/user/{userId}", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].title").value("Title1"))
                .andExpect(jsonPath("$[0].content").value("Content1"));

        verify(userService).getAllPostsByUserId(userId);
    }

    @Test
    void testGetPostsByUserIdAndTimeRange() throws Exception {
        long userId = 1L;
        Timestamp startTime = Timestamp.valueOf("2023-01-01 00:00:00");
        Timestamp endTime = Timestamp.valueOf("2023-12-31 23:59:59");
        List<PostDto> posts = Arrays.asList(new PostDto(1L, "Title1", "Content1", new UserDto(), null, null));

        when(userService.getAllPostsByUserIdAndTimeRange(userId, startTime, endTime)).thenReturn(posts);

        mockMvc.perform(get("/user/{userId}/time-range", userId)
                        .param("startTime", startTime.toString())
                        .param("endTime", endTime.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].title").value("Title1"))
                .andExpect(jsonPath("$[0].content").value("Content1"));

        verify(userService).getAllPostsByUserIdAndTimeRange(userId, startTime, endTime);
    }
}

