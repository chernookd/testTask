package com.testProject.userPostService.service;

import com.testProject.userPostService.Utils.EntityUtils;
import com.testProject.userPostService.controllers.dto.PostDto;
import com.testProject.userPostService.controllers.dto.UserDto;
import com.testProject.userPostService.controllers.exception.InvalidUserException;
import com.testProject.userPostService.entity.Post;
import com.testProject.userPostService.entity.User;
import com.testProject.userPostService.repository.PostRepos;
import com.testProject.userPostService.repository.UserRepos;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepos userRepository;

    private final PostRepos postRepository;


    public UserDto createUser(UserDto user) {
        User user1 = userRepository.save(EntityUtils.UserDtoToUser(user));

        if (user1 == null) {
            throw new InvalidUserException("invalid User");
        }

        return EntityUtils.entityUserToUserDto(user1);
    }

    public List<UserDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(EntityUtils::entityUserToUserDto)
                .collect(Collectors.toList());

    }

    public UserDto getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow();

        return EntityUtils.entityUserToUserDto(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public List<PostDto> getAllPostsByUserId(Long userId) {
        List<Post> list = postRepository.findByUserId(userId).orElseThrow();

        return list.stream()
                .map(EntityUtils::entityPostToPostDto)
                .collect(Collectors.toList());    }

    public List<PostDto> getAllPostsByUserIdAndTimeRange(Long userId, Timestamp startTime, Timestamp endTime) {
        List<Post> list = postRepository.findByUserIdAndCreatedAtBetween(userId, startTime, endTime).orElseThrow();

        return list.stream()
                .map(EntityUtils::entityPostToPostDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public UserDto updateUser(Long id, UserDto userDetails) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new InvalidUserException("User not found"));

        user.setName(userDetails.getName());
        user.setEmail(userDetails.getEmail());

        return EntityUtils.entityUserToUserDto(userRepository.save(user));
    }

}
