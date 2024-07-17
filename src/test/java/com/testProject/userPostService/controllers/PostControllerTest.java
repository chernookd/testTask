package com.testProject.userPostService.controllers;


import com.testProject.userPostService.controllers.dto.PostDto;
import com.testProject.userPostService.controllers.exception.InvalidIdException;
import com.testProject.userPostService.controllers.exception.InvalidPostException;
import com.testProject.userPostService.entity.Post;
import com.testProject.userPostService.service.PostService;
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

public class PostControllerTest {

    @InjectMocks
    private PostController postController;

    @Mock
    private PostService postService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllPosts() {
        List<Post> posts = Arrays.asList(new Post(1L, "Title", "Content", 1L, null, null));
        when(postService.getAllPosts()).thenReturn(posts);

        ResponseEntity<List<PostDto>> response = postController.getAllPosts();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
    }

    @Test
    public void testGetPostById() {
        Post post = new Post(1L, "Title", "Content", 1L, null, null);
        when(postService.getPostById(1L)).thenReturn(post);

        ResponseEntity<PostDto> response = postController.getPostById(1L);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Title", response.getBody().getTitle());
    }

    @Test
    public void testGetPostById_InvalidId() {
        assertThrows(InvalidIdException.class, () -> postController.getPostById(null));
    }

    @Test
    public void testCreatePost() {
        Post post = new Post(1L, "Title", "Content", 1L, null, null);
        when(postService.createPost(post)).thenReturn(post);

        ResponseEntity<PostDto> response = postController.createPost(post);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Title", response.getBody().getTitle());
    }

    @Test
    public void testCreatePost_InvalidPost() {
        assertThrows(InvalidPostException.class, () -> postController.createPost(null));
    }

    @Test
    public void testDeletePost() {
        doNothing().when(postService).deletePost(1L);
        postController.deletePost(1L);
        verify(postService, times(1)).deletePost(1L);
    }
}
