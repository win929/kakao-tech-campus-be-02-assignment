package com.example.schedulemanagement.service;

import com.example.schedulemanagement.dto.*;

import java.util.List;

public interface ScheduleService {

    ScheduleResponseDto createSchedule(ScheduleRequestDto scheduleRequestDto);

    List<ScheduleResponseDto> findAllSchedules(ScheduleFindRequestDto scheduleFindRequestDto);

    ScheduleResponseDto findScheduleById(Long id);

    ScheduleResponseDto updateSchedule(Long id, ScheduleUpdateRequestDto scheduleUpdateRequestDto);

    void deleteSchedule(Long id, ScheduleDeleteRequestDto scheduleDeleteRequestDto);
}
