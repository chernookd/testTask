package com.testProject.userPostService.repository;

import com.testProject.userPostService.entity.Post;
import com.testProject.userPostService.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepos extends JpaRepository<User, Long> {
}
