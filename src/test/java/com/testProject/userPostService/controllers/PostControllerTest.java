package com.testProject.userPostService.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.testProject.userPostService.controllers.dto.PostDto;
import com.testProject.userPostService.controllers.dto.UserDto;
import com.testProject.userPostService.service.PostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.sql.Timestamp;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class PostControllerTest {

    @Mock
    private PostService postService;

    @InjectMocks
    private PostController postController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    private static final String POSTS_ENDPOINT = "/posts";
    private static final String POST_BY_ID_ENDPOINT = "/posts/{id}";
    private static final String POST_CREATE_ENDPOINT = "/post/create";
    private static final String POST_DELETE_ENDPOINT = "/post/delete/{id}";
    private static final String POST_BY_USER_ID_ENDPOINT = "/post/{userId}";
    private static final String POST_BY_USER_ID_TIME_RANGE_ENDPOINT = "/post/{userId}/time-range";

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(postController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void testGetAllPosts() throws Exception {
        when(postService.getAllPosts()).thenReturn(Collections.singletonList(createSamplePostDto()));

        mockMvc.perform(get(POSTS_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").exists())
                .andExpect(jsonPath("$[0].title").exists())
                .andExpect(jsonPath("$[0].content").exists());

        verify(postService).getAllPosts();
    }

    @Test
    void testGetPostById() throws Exception {
        long postId = 1L;
        when(postService.getPostById(postId)).thenReturn(createSamplePostDto());

        mockMvc.perform(get(POST_BY_ID_ENDPOINT, postId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(postId))
                .andExpect(jsonPath("$.title").exists())
                .andExpect(jsonPath("$.content").exists());

        verify(postService).getPostById(postId);
    }

    @Test
    void testCreatePost() throws Exception {
        PostDto postDto = createSamplePostDto();
        when(postService.createPost(any(PostDto.class))).thenReturn(postDto);

        mockMvc.perform(post(POST_CREATE_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.title").value(postDto.getTitle()))
                .andExpect(jsonPath("$.content").value(postDto.getContent()));

        verify(postService).createPost(any(PostDto.class));
    }

    @Test
    void testCreatePostWithInvalidData() throws Exception {
        PostDto invalidPostDto = null;


        mockMvc.perform(post(POST_CREATE_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidPostDto)))
                .andExpect(status().isBadRequest());

        verify(postService, never()).createPost(any(PostDto.class));
    }

    @Test
    void testDeletePost() throws Exception {
        long postId = 1L;

        mockMvc.perform(delete(POST_DELETE_ENDPOINT, postId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(postService).deletePost(postId);
    }

    @Test
    void testGetPostsByUserId() throws Exception {
        long userId = 1L;
        when(postService.getPostsByUserId(userId)).thenReturn(Collections.singletonList(createSamplePostDto()));

        mockMvc.perform(get(POST_BY_USER_ID_ENDPOINT, userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").exists())
                .andExpect(jsonPath("$[0].title").exists())
                .andExpect(jsonPath("$[0].content").exists());

        verify(postService).getPostsByUserId(userId);
    }

    @Test
    void testGetPostsByUserIdAndTimeRange() throws Exception {
        long userId = 1L;
        Timestamp startTime = Timestamp.valueOf("2023-07-18 00:00:00");
        Timestamp endTime = Timestamp.valueOf("2023-07-19 00:00:00");
        when(postService.getPostsByUserIdAndTimeRange(eq(userId), eq(startTime), eq(endTime)))
                .thenReturn(Collections.singletonList(createSamplePostDto()));

        mockMvc.perform(get(POST_BY_USER_ID_TIME_RANGE_ENDPOINT, userId)
                        .param("startTime", String.valueOf(startTime))
                        .param("endTime", String.valueOf(endTime))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").exists())
                .andExpect(jsonPath("$[0].title").exists())
                .andExpect(jsonPath("$[0].content").exists());

        verify(postService).getPostsByUserIdAndTimeRange(eq(userId), eq(startTime), eq(endTime));
    }

    @Test
    void testUpdatePost() throws Exception {
        long postId = 1L;
        PostDto updatedPostDto = createSamplePostDto();
        updatedPostDto.setTitle("Updated Title");
        updatedPostDto.setContent("Updated Content");

        when(postService.updatePost(eq(postId), any(PostDto.class))).thenReturn(updatedPostDto);

        mockMvc.perform(put("/post/update/{id}", postId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedPostDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(postId))
                .andExpect(jsonPath("$.title").value("Updated Title"))
                .andExpect(jsonPath("$.content").value("Updated Content"));

        verify(postService).updatePost(eq(postId), any(PostDto.class));
    }

    private PostDto createSamplePostDto() {
        UserDto userDto = new UserDto(1L, "John Doe", "john.doe@example.com", Collections.emptyList());
        return new PostDto(1L, "Sample Title", "Sample Content", userDto, new Timestamp(System.currentTimeMillis()), null);
    }
}
