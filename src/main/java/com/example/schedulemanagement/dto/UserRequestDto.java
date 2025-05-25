package com.example.schedulemanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserRequestDto {

    private Long id;
    private String username;
    private String password;
    private String email;
}
