package com.testProject.userPostService.controllers;


import com.testProject.userPostService.controllers.dto.PostDto;
import com.testProject.userPostService.controllers.dto.UserDto;
import com.testProject.userPostService.controllers.exception.CustomExceptionHandler;

import com.testProject.userPostService.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;


@RestController
@RequiredArgsConstructor
@CustomExceptionHandler
public class UserController {

    private final UserService userServiceImpl;


    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> userList = userServiceImpl.getAllUsers();

        return ResponseEntity.ok().body(userList);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable @NotNull @Min(0) Long id) {
        UserDto user = userServiceImpl.getUserById(id);

        return ResponseEntity.ok().body(user);
    }

    @PostMapping("/createuser")
    public ResponseEntity<UserDto> createUser(@RequestBody @NotNull UserDto user) {
        UserDto createdUser = userServiceImpl.createUser(user);

        return ResponseEntity.ok().body(createdUser);
    }

    @DeleteMapping("/deleteuser/{id}")
    public void deleteUser(@PathVariable @NotNull @Min(0) Long id) {
        userServiceImpl.deleteUser(id);
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable @NotNull @Min(0) Long id, @RequestBody @Valid @NotNull UserDto userDetails) {
        UserDto updatedUser = userServiceImpl.updateUser(id, userDetails);

        return ResponseEntity.ok()
                .body(updatedUser);
    }


    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PostDto>> getPostsByUserId(@PathVariable @NotNull @Min(0) Long userId) {
        List<PostDto> posts = userServiceImpl.getAllPostsByUserId(userId);
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/user/{userId}/time-range")
    public ResponseEntity<List<PostDto>> getPostsByUserIdAndTimeRange(@PathVariable @NotNull @Min(0) Long userId,
                                                                      @RequestParam Timestamp startTime,
                                                                      @RequestParam Timestamp endTime) {
        List<PostDto> posts = userServiceImpl.getAllPostsByUserIdAndTimeRange(userId, startTime, endTime);
        return ResponseEntity.ok(posts);
    }

}

