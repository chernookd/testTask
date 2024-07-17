package com.testProject.userPostService.controllers;


import com.testProject.userPostService.controllers.dto.UserDto;
import com.testProject.userPostService.controllers.exception.CustomExceptionHandler;
import com.testProject.userPostService.controllers.exception.InvalidIdException;
import com.testProject.userPostService.controllers.exception.InvalidUserException;
import com.testProject.userPostService.entity.User;
import com.testProject.userPostService.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@CustomExceptionHandler
public class UserController {

    private final UserService userServiceImpl;

    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<User> userList = userServiceImpl.getAllUsers();

        if (userList == null || userList.isEmpty()) {
            throw new RuntimeException("No users found");
        }

        List<UserDto> userDtoList = userList.stream()
                .map(user -> new UserDto(user.getId(), user.getName(), user.getEmail()))
                .toList();

        return ResponseEntity.ok().body(userDtoList);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        if (id == null) {
            throw new InvalidIdException("ID cannot be null");
        }

        User user = userServiceImpl.getUserById(id);

        if (user == null) {
            throw new InvalidIdException("User not found");
        }

        return ResponseEntity.ok().body(new UserDto(user.getId(), user.getName(), user.getEmail()));
    }

    @PostMapping("/createuser")
    public ResponseEntity<UserDto> createUser(@RequestBody User user) {
        if (user == null) {
            throw new InvalidUserException("User cannot be null");
        }

        User createdUser = userServiceImpl.createUser(user);

        return ResponseEntity.ok().body(new UserDto(createdUser.getId(), createdUser.getName(), createdUser.getEmail()));
    }

    @DeleteMapping("/deleteuser/{id}")
    public void deleteUser(@PathVariable Long id) {
        if (id == null) {
            throw new InvalidIdException("ID cannot be null");
        }

        userServiceImpl.deleteUser(id);
    }

}

