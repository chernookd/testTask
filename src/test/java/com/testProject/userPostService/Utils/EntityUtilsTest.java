package com.testProject.userPostService.Utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.testProject.userPostService.controllers.dto.PostDto;
import com.testProject.userPostService.controllers.dto.UserDto;

import com.testProject.userPostService.entity.Post;
import com.testProject.userPostService.entity.User;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class EntityUtilsTest {

    @Test
    void testEntityUserToUserDto() {
        User user = new User(1L, "1", "1", new ArrayList<>());

        UserDto userDto = EntityUtils.entityUserToUserDto(user);

        assertNotNull(userDto);
        assertEquals(user.getId(), userDto.getId());
        assertEquals(user.getName(), userDto.getName());
        assertEquals(user.getEmail(), userDto.getEmail());
        assertEquals(user.getPosts(), userDto.getPosts());
    }

    @Test
    void testEntityPostToPostDto() {
        User user = new User(1L, "1", "1", new ArrayList<>());
        Post post = new Post(1L, "1", "1", user, new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()));

        PostDto postDto = EntityUtils.entityPostToPostDto(post);

        assertNotNull(postDto);
        assertEquals(post.getId(), postDto.getId());
        assertEquals(post.getTitle(), postDto.getTitle());
        assertEquals(post.getContent(), postDto.getContent());
        assertEquals(post.getCreatedAt(), postDto.getCreatedAt());
        assertEquals(post.getUpdatedAt(), postDto.getUpdatedAt());
        assertNotNull(postDto.getUser());
        assertEquals(post.getUser().getId(), postDto.getUser().getId());
    }

    @Test
    void testPostDtoToPost() {
        UserDto userDto = new UserDto(1L, "1", "1", new ArrayList<>());
        PostDto postDto = new PostDto(1L, "1", "1", userDto, new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()));

        Post post = EntityUtils.PostDtoToPost(postDto);

        assertNotNull(post);
        assertEquals(postDto.getId(), post.getId());
        assertEquals(postDto.getTitle(), post.getTitle());
        assertEquals(postDto.getContent(), post.getContent());
        assertEquals(postDto.getCreatedAt(), post.getCreatedAt());
        assertEquals(postDto.getUpdatedAt(), post.getUpdatedAt());
        assertNotNull(post.getUser());
        assertEquals(postDto.getUser().getId(), post.getUser().getId());
    }

    @Test
    void testUserDtoToUser() {
        List<Post> posts = new ArrayList<>();
        UserDto userDto = new UserDto(1L, "1", "1", posts);

        User user = EntityUtils.UserDtoToUser(userDto);

        assertNotNull(user);
        assertEquals(userDto.getId(), user.getId());
        assertEquals(userDto.getName(), user.getName());
        assertEquals(userDto.getEmail(), user.getEmail());
        assertEquals(userDto.getPosts(), user.getPosts());
    }
}
