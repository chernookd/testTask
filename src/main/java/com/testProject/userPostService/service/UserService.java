package com.testProject.userPostService.service;

import com.testProject.userPostService.Utils.EntityUtils;
import com.testProject.userPostService.controllers.dto.PostDto;
import com.testProject.userPostService.controllers.dto.UserDto;
import com.testProject.userPostService.entity.Post;
import com.testProject.userPostService.entity.User;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

public interface UserService {

    public UserDto createUser(UserDto user);

    public List<UserDto> getAllUsers();

    public UserDto getUserById(Long id);

    public void deleteUser(Long id);

    UserDto updateUser(Long id, UserDto userDetails);

    public List<PostDto> getAllPostsByUserId(Long userId);

    public List<PostDto> getAllPostsByUserIdAndTimeRange(Long userId, Timestamp startTime, Timestamp endTime);


}
