package com.example.schedulemanagement.service;

import com.example.schedulemanagement.dto.UserRequestDto;
import com.example.schedulemanagement.dto.UserResponseDto;

public interface UserService {

    UserResponseDto createUser(UserRequestDto userRequestDto);
}
