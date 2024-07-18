package com.testProject.userPostService.controllers.dto;

import com.testProject.userPostService.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SourceType;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {

    @NotNull
    private Long id;
    private String title;
    private String content;
    @NotNull
    private UserDto user;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}

