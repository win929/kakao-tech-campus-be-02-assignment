package com.example.schedulemanagement.service;

import com.example.schedulemanagement.dto.ScheduleDeleteRequestDto;
import com.example.schedulemanagement.dto.ScheduleRequestDto;
import com.example.schedulemanagement.dto.ScheduleResponseDto;
import com.example.schedulemanagement.dto.ScheduleUpdateRequestDto;

import java.util.List;

public interface ScheduleService {

    ScheduleResponseDto createSchedule(ScheduleRequestDto scheduleRequestDto);

    List<ScheduleResponseDto> findAllSchedules();

    ScheduleResponseDto findScheduleById(Long id);

    ScheduleResponseDto updateSchedule(Long id, ScheduleUpdateRequestDto scheduleUpdateRequestDto);

    void deleteSchedule(Long id, ScheduleDeleteRequestDto scheduleDeleteRequestDto);
}
