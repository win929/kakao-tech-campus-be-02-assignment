package com.example.schedulemanagement.service;

import com.example.schedulemanagement.dto.*;
import com.example.schedulemanagement.entity.Schedule;
import com.example.schedulemanagement.entity.User;
import com.example.schedulemanagement.repository.ScheduleRepository;
import com.example.schedulemanagement.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;

    public ScheduleServiceImpl(ScheduleRepository scheduleRepository, UserRepository userRepository) {
        this.scheduleRepository = scheduleRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ScheduleResponseDto createSchedule(ScheduleRequestDto scheduleRequestDto) {
        LocalDateTime now = LocalDateTime.now();

        Schedule schedule = new Schedule(
                scheduleRequestDto.getUserId(),
                scheduleRequestDto.getTitle(),
                scheduleRequestDto.getContent(),
                now,
                now
        );

        return scheduleRepository.createSchedule(schedule);
    }

    @Override
    public List<ScheduleResponseDto> findAllSchedules(ScheduleFindRequestDto scheduleFindRequestDto) {

        LocalDate localDate = scheduleFindRequestDto.getUpdatedAt();
        String username = scheduleFindRequestDto.getUsername();

        // localDate와 username이 모두 맞는 schedule을 찾는 로직
        if (localDate != null && username != null) {
            return scheduleRepository.findAllSchedules(localDate, username);
        }

        // localDate가 null인 경우 username으로만 필터링
        if (localDate == null && username != null) {
            return scheduleRepository.findAllSchedules(username);
        }

        // localDate가 null이 아닌 경우 해당 날짜의 모든 스케줄을 찾는 로직
        if (localDate != null) {
            return scheduleRepository.findAllSchedules(localDate);
        }

        // localDate와 username이 모두 null인 경우 모든 스케줄을 반환
        return scheduleRepository.findAllSchedules();
    }

    @Override
    public ScheduleResponseDto findScheduleById(Long id) {

        Schedule schedule = scheduleRepository.findScheduleByIdOrElseThrow(id);

        return new ScheduleResponseDto(schedule);
    }

    @Override
    public ScheduleResponseDto updateSchedule(Long id, ScheduleUpdateRequestDto scheduleUpdateRequestDto) {

        String username = scheduleUpdateRequestDto.getUsername();
        String title = scheduleUpdateRequestDto.getTitle();
        String content = scheduleUpdateRequestDto.getContent();

        if (username == null || title == null || content == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username and title and content must not be null");
        }

        // 비밀번호 확인
        Schedule schedule = scheduleRepository.findScheduleByIdOrElseThrow(id);
        User user = userRepository.findByUserIdOrElseThrow(schedule.getUserId());

        LocalDateTime now = LocalDateTime.now();

        int updatedRow = scheduleRepository.updateSchedule(id, title, content, now);
        if (updatedRow == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Schedule not found");
        }
        if (!user.getPassword().equals(scheduleUpdateRequestDto.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Password does not match");
        }

        user = userRepository.updateUser(schedule.getUserId(), username);

        schedule.setTitle(title);
        schedule.setContent(content);
        schedule.setUpdatedAt(now);

        return new ScheduleResponseDto(schedule);
    }

    @Override
    public void deleteSchedule(Long id, ScheduleDeleteRequestDto scheduleDeleteRequestDto) {

        // 비밀번호 확인
        Schedule schedule = scheduleRepository.findScheduleByIdOrElseThrow(id);
        User user = userRepository.findByUserIdOrElseThrow(schedule.getUserId());

        if (!user.getPassword().equals(scheduleDeleteRequestDto.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Password does not match");
        }

        int deletedRow = scheduleRepository.deleteSchedule(id);

        if (deletedRow == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Schedule not found");
        }
    }
}
