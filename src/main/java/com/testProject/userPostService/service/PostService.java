package com.testProject.userPostService.service;

import com.testProject.userPostService.entity.Post;
import com.testProject.userPostService.repository.PostRepos;

import java.util.List;

public interface PostService {



    public Post createPost(Post post);

    public List<Post> getAllPosts();

    public Post getPostById(Long id);

    public void deletePost(Long id);

}
