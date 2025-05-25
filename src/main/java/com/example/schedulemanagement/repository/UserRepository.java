package com.example.schedulemanagement.repository;

import com.example.schedulemanagement.dto.UserResponseDto;
import com.example.schedulemanagement.entity.User;

public interface UserRepository {

    UserResponseDto createUser(User user);

    User findByUserIdOrElseThrow(Long userId);

    User updateUser(Long id, String username);
}
