package com.testProject.userPostService.controllers;


import com.testProject.userPostService.controllers.dto.PostDto;
import com.testProject.userPostService.controllers.exception.CustomExceptionHandler;
import com.testProject.userPostService.controllers.exception.InvalidIdException;
import com.testProject.userPostService.controllers.exception.InvalidPostException;
import com.testProject.userPostService.entity.Post;
import com.testProject.userPostService.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CustomExceptionHandler
public class PostController {

    private final PostService postServiceImpl;

    @GetMapping("/posts")
    public ResponseEntity<List<PostDto>> getAllPosts() {
        List<Post> postList = postServiceImpl.getAllPosts();

        if (postList == null || postList.isEmpty()) {
            throw new RuntimeException("No posts found");
        }

        List<PostDto> postDtoList = postList.stream()
                .map(post -> new PostDto(post.getId(), post.getTitle(), post.getContent(), post.getUserId(), post.getCreatedAt(), post.getUpdatedAt()))
                .toList();

        return ResponseEntity.ok().body(postDtoList);
    }

    @GetMapping("/posts/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable Long id) {
        if (id == null) {
            throw new InvalidIdException("ID cannot be null");
        }

        Post post = postServiceImpl.getPostById(id);

        if (post == null) {
            throw new InvalidIdException("Post not found");
        }

        return ResponseEntity.ok()
                .body(new PostDto(post.getId(), post.getTitle(), post.getContent(), post.getUserId(), post.getCreatedAt(), post.getUpdatedAt()));
    }

    @PostMapping("/createpost")
    public ResponseEntity<PostDto> createPost(@RequestBody Post post) {
        if (post == null) {
            throw new InvalidPostException("Post cannot be null");
        }

        Post createdPost = postServiceImpl.createPost(post);

        return ResponseEntity.ok()
                .body(new PostDto(createdPost.getId(), createdPost.getTitle(), createdPost.getContent(),
                        createdPost.getUserId(), createdPost.getCreatedAt(), createdPost.getUpdatedAt()));
    }

    @DeleteMapping("/deletepost/{id}")
    public void deletePost(@PathVariable Long id) {
        if (id == null) {
            throw new InvalidIdException("ID cannot be null");
        }

        postServiceImpl.deletePost(id);
    }

}

