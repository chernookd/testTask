package com.testProject.userPostService.repository;

import com.testProject.userPostService.entity.Post;
import com.testProject.userPostService.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepos extends JpaRepository<Post, Long> {
    @Query("SELECT p FROM Post p WHERE p.user.id = :userId")
    Optional<List<Post>> findByUserId(@Param("userId") Long userId);

    @Query("SELECT p FROM Post p WHERE p.user.id = :userId AND p.createdAt BETWEEN :startTime AND :endTime")
    Optional<List<Post>> findByUserIdAndCreatedAtBetween(@Param("userId") Long userId, @Param("startTime") Timestamp startTime,
                                               @Param("endTime") Timestamp endTime);

}