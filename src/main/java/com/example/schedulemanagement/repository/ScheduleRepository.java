package com.example.schedulemanagement.repository;

import com.example.schedulemanagement.dto.ScheduleFindRequestDto;
import com.example.schedulemanagement.dto.ScheduleResponseDto;
import com.example.schedulemanagement.entity.Schedule;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface ScheduleRepository {

    ScheduleResponseDto createSchedule(Schedule schedule);

    List<ScheduleResponseDto> findAllSchedules(LocalDate updatedAt, String username);
    List<ScheduleResponseDto> findAllSchedules(LocalDate updatedAt);
    List<ScheduleResponseDto> findAllSchedules(String username);
    List<ScheduleResponseDto> findAllSchedules();

    Schedule findScheduleByIdOrElseThrow(Long id);

    int updateSchedule(Long id, String title, String content, LocalDateTime now);

    int deleteSchedule(Long id);

}
