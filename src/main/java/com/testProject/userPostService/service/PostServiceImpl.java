package com.testProject.userPostService.service;

import com.testProject.userPostService.Utils.EntityUtils;
import com.testProject.userPostService.controllers.dto.PostDto;
import com.testProject.userPostService.controllers.exception.InvalidPostException;
import com.testProject.userPostService.entity.Post;
import com.testProject.userPostService.repository.PostRepos;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepos postRepository;

    public PostDto createPost(PostDto post) {
        return EntityUtils.entityPostToPostDto(postRepository.save(EntityUtils.PostDtoToPost(post)));
    }

    public List<PostDto> getAllPosts() {
        return postRepository.findAll().stream()
                .map(EntityUtils::entityPostToPostDto)
                .collect(Collectors.toList());
    }

    public PostDto getPostById(Long id) {
        return EntityUtils.entityPostToPostDto(postRepository.findById(id).orElseThrow());
    }

    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }

    @Override
    public List<PostDto> getPostsByUserId(Long userId) {
        List<Post> list = postRepository.findByUserId(userId).orElseThrow();

        return list.stream()
                .map(EntityUtils::entityPostToPostDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<PostDto> getPostsByUserIdAndTimeRange(Long userId, Timestamp startTime, Timestamp endTime) {
        List<Post> list = postRepository.findByUserIdAndCreatedAtBetween(userId, startTime, endTime).orElseThrow();

        return list.stream()
                .map(EntityUtils::entityPostToPostDto)
                .collect(Collectors.toList());
    }



    @Override
    @Transactional
    public PostDto updatePost(Long id, PostDto postDetails) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new InvalidPostException("Post not found"));

        post.setTitle(postDetails.getTitle());
        post.setContent(postDetails.getContent());
        post.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));

        return EntityUtils.entityPostToPostDto(postRepository.save(post));
    }
}

