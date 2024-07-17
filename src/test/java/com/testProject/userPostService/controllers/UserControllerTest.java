package com.testProject.userPostService.controllers;

import com.testProject.userPostService.controllers.dto.UserDto;
import com.testProject.userPostService.controllers.exception.InvalidIdException;
import com.testProject.userPostService.controllers.exception.InvalidUserException;
import com.testProject.userPostService.entity.User;
import com.testProject.userPostService.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllUsers() {
        List<User> users = Arrays.asList(new User(1L, "Name", "email@example.com"));
        when(userService.getAllUsers()).thenReturn(users);

        ResponseEntity<List<UserDto>> response = userController.getAllUsers();
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
    }

    @Test
    public void testGetUserById() {
        User user = new User(1L, "Name", "email@example.com");
        when(userService.getUserById(1L)).thenReturn(user);

        ResponseEntity<UserDto> response = userController.getUserById(1L);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Name", response.getBody().getName());
    }

    @Test
    public void testGetUserById_InvalidId() {
        assertThrows(InvalidIdException.class, () -> userController.getUserById(null));
    }

    @Test
    public void testCreateUser() {
        User user = new User(1L, "Name", "email@example.com");
        when(userService.createUser(user)).thenReturn(user);

        ResponseEntity<UserDto> response = userController.createUser(user);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Name", response.getBody().getName());
    }

    @Test
    public void testCreateUser_InvalidUser() {
        assertThrows(InvalidUserException.class, () -> userController.createUser(null));
    }

    @Test
    public void testDeleteUser() {
        doNothing().when(userService).deleteUser(1L);
        userController.deleteUser(1L);
        verify(userService, times(1)).deleteUser(1L);
    }
}
