package com.example.schedulemanagement.repository;

import com.example.schedulemanagement.dto.ScheduleResponseDto;
import com.example.schedulemanagement.entity.Schedule;

import java.util.List;

public interface ScheduleRepository {

    ScheduleResponseDto createSchedule(Schedule schedule);

    List<ScheduleResponseDto> findAllSchedules();

    Schedule findScheduleByIdOrElseThrow(Long id);

    int updateSchedule(Long id,String title, String content);

    int deleteSchedule(Long id);

}
