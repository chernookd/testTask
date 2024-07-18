package com.testProject.userPostService.service;

import com.testProject.userPostService.controllers.dto.PostDto;
import com.testProject.userPostService.entity.Post;
import com.testProject.userPostService.repository.PostRepos;

import java.sql.Timestamp;
import java.util.List;

public interface PostService {



    public PostDto createPost(PostDto post);

    public List<PostDto> getAllPosts();

    public PostDto getPostById(Long id);

    public void deletePost(Long id);

    List<PostDto> getPostsByUserId(Long userId);
    List<PostDto> getPostsByUserIdAndTimeRange(Long userId, Timestamp startTime, Timestamp endTime);
    PostDto updatePost(Long id, PostDto post);


}
