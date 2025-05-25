package com.example.schedulemanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class ScheduleFindRequestDto {

    LocalDate updatedAt;
    String username;
}
