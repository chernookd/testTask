package com.testProject.userPostService.controllers.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
public class UserDto {

    private Long id;
    private String name;
    private String email;

}
