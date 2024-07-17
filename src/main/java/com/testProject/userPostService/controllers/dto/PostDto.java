package com.testProject.userPostService.controllers.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
public class PostDto {

    private Long id;
    private String title;
    private String content;
    private Long userId;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
