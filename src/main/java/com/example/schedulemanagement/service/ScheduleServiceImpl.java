package com.example.schedulemanagement.service;

import com.example.schedulemanagement.dto.ScheduleDeleteRequestDto;
import com.example.schedulemanagement.dto.ScheduleRequestDto;
import com.example.schedulemanagement.dto.ScheduleResponseDto;
import com.example.schedulemanagement.dto.ScheduleUpdateRequestDto;
import com.example.schedulemanagement.entity.Schedule;
import com.example.schedulemanagement.entity.User;
import com.example.schedulemanagement.repository.ScheduleRepository;
import com.example.schedulemanagement.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
    public List<ScheduleResponseDto> findAllSchedules() {

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

        int updatedRow = scheduleRepository.updateSchedule(id, title, content);
        if (updatedRow == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Schedule not found");
        }
        if (!user.getPassword().equals(scheduleUpdateRequestDto.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Password does not match");
        }

        user = userRepository.updateUser(schedule.getUserId(), username);

        schedule.setTitle(title);
        schedule.setContent(content);
        schedule.setUpdatedAt(LocalDateTime.now()); // 이거 어떡하지..

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
