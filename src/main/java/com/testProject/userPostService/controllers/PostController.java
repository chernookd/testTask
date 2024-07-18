package com.testProject.userPostService.controllers;


import com.testProject.userPostService.controllers.dto.PostDto;
import com.testProject.userPostService.controllers.exception.CustomExceptionHandler;
import com.testProject.userPostService.controllers.exception.InvalidIdException;
import com.testProject.userPostService.controllers.exception.InvalidPostException;
import com.testProject.userPostService.entity.Post;
import com.testProject.userPostService.service.PostService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.hibernate.sql.Update;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequiredArgsConstructor
@CustomExceptionHandler
public class PostController {

    private final PostService postServiceImpl;

    @GetMapping("/posts")
    public ResponseEntity<List<PostDto>> getAllPosts() {
        List<PostDto> postList = postServiceImpl.getAllPosts();

        return ResponseEntity.ok().body(postList);
    }

    @GetMapping("/posts/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable @NotNull Long id) {
        PostDto post = postServiceImpl.getPostById(id);

        return ResponseEntity.ok()
                .body(post);
    }

    @PostMapping("/post/create")
    public ResponseEntity<PostDto> createPost(@RequestBody @NotNull PostDto post) {
        PostDto createdPost = postServiceImpl.createPost(post);

        return ResponseEntity.ok()
                .body(createdPost);
    }

    @DeleteMapping("/post/delete/{id}")
    public void deletePost(@PathVariable @NotNull @Min(0) Long id) {
        postServiceImpl.deletePost(id);
    }

    @GetMapping("/post/{userId}")
    public ResponseEntity<List<PostDto>> getPostsByUserId(@PathVariable @NotNull @Min(0) Long userId) {
        List<PostDto> posts = postServiceImpl.getPostsByUserId(userId);

        return ResponseEntity.ok(posts);
    }

    @GetMapping("/post/{userId}/time-range")
    public ResponseEntity<List<PostDto>> getPostsByUserIdAndTimeRange(@PathVariable @NotNull @Min(0) Long userId,
                                                                      @RequestParam String startTime,
                                                                      @RequestParam String endTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        LocalDateTime startDateTime = LocalDateTime.parse(startTime, formatter);
        LocalDateTime endDateTime = LocalDateTime.parse(endTime, formatter);
        Timestamp startTimestamp = Timestamp.valueOf(startDateTime);
        Timestamp endTimestamp = Timestamp.valueOf(endDateTime);

        List<PostDto> posts = postServiceImpl.getPostsByUserIdAndTimeRange(userId, startTimestamp, endTimestamp);
        return ResponseEntity.ok(posts);
    }

    @PutMapping("/post/update/{id}")
    public ResponseEntity<PostDto> updatePost(@PathVariable @NotNull @Min(0) Long id, @RequestBody @NotNull PostDto postDetails) {
        PostDto updatedPost = postServiceImpl.updatePost(id, postDetails);

        return ResponseEntity.ok()
                .body(updatedPost);
    }

}

