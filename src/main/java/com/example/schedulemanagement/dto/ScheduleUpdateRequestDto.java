package com.example.schedulemanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ScheduleUpdateRequestDto {

    private String username;
    private String password;
    private String title;
    private String content;
}

