package com.testProject.userPostService.Utils;

import com.testProject.userPostService.controllers.dto.PostDto;
import com.testProject.userPostService.controllers.dto.UserDto;
import com.testProject.userPostService.entity.Post;
import com.testProject.userPostService.entity.User;
import lombok.experimental.UtilityClass;


@UtilityClass
public class EntityUtils {

    public static UserDto entityUserToUserDto(User user) {

        return new UserDto(user.getId(), user.getName(), user.getEmail(), user.getPosts());
    }


    public static PostDto entityPostToPostDto(Post post) {

        return new PostDto(post.getId(), post.getTitle(), post.getContent(), entityUserToUserDto(post.getUser()), post.getCreatedAt(), post.getUpdatedAt());
    }

    public static Post PostDtoToPost(PostDto post) {
        return new Post(post.getId(), post.getTitle(), post.getContent(), UserDtoToUser(post.getUser()), post.getCreatedAt(), post.getUpdatedAt());
    }


    public static User UserDtoToUser(UserDto user) {

        return new User(user.getId(), user.getName(), user.getEmail(), user.getPosts());
    }




}
