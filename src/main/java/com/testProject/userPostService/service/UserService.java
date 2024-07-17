package com.testProject.userPostService.service;

import com.testProject.userPostService.entity.User;

import java.util.List;

public interface UserService {

    public User createUser(User user);

    public List<User> getAllUsers();

    public User getUserById(Long id);

    public void deleteUser(Long id);

}
