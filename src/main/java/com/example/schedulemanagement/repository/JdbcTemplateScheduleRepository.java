package com.example.schedulemanagement.repository;

import com.example.schedulemanagement.dto.ScheduleFindRequestDto;
import com.example.schedulemanagement.dto.ScheduleResponseDto;
import com.example.schedulemanagement.entity.Schedule;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class JdbcTemplateScheduleRepository implements ScheduleRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateScheduleRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public ScheduleResponseDto createSchedule(Schedule schedule) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(this.jdbcTemplate);
        jdbcInsert.withTableName("schedule").usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("user_id", schedule.getUserId());
        parameters.put("title", schedule.getTitle());
        parameters.put("content", schedule.getContent());
        parameters.put("created_at", schedule.getCreatedAt());
        parameters.put("updated_at", schedule.getUpdatedAt());

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));

        return new ScheduleResponseDto(key.longValue(),
                schedule.getUserId(),
                schedule.getTitle(),
                schedule.getContent(),
                schedule.getCreatedAt(),
                schedule.getUpdatedAt());
    }

    @Override
    public List<ScheduleResponseDto> findAllSchedules() {
        return jdbcTemplate.query("select * from schedule order by updated_at desc", scheduleRowMapper());
    }

    @Override
    public List<ScheduleResponseDto> findAllSchedules(LocalDate updatedAt, String username) {
        return jdbcTemplate.query(
                "select s.* from schedule s join user u on s.user_id = u.id where DATE(s.updated_at) = ? and u.username = ? order by s.updated_at desc",
                scheduleRowMapper(),
                updatedAt,
                username
        );
    }

    @Override
    public List<ScheduleResponseDto> findAllSchedules(LocalDate updatedAt) {
        return jdbcTemplate.query(
                "select * from schedule where DATE(updated_at) = ? order by updated_at desc",
                scheduleRowMapper(),
                updatedAt
        );
    }

    @Override
    public List<ScheduleResponseDto> findAllSchedules(String username) {
        return jdbcTemplate.query(
                "select s.* from schedule s join user u on s.user_id = u.id where u.username = ? order by s.updated_at desc",
                scheduleRowMapper(),
                username
        );
    }

    @Override
    public Schedule findScheduleByIdOrElseThrow(Long id) {
        List<Schedule> result = jdbcTemplate.query("select * from schedule where id = ?", scheduleRowMapperV2(), id);

        return result.stream().findAny().orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Schedule not found"));
    }

    @Override
    public int updateSchedule(Long id, String title, String content, LocalDateTime now) {
        return jdbcTemplate.update("update schedule set title = ?, content = ?, updated_at = ? where id = ?", title, content, now, id);
    }

    @Override
    public int deleteSchedule(Long id) {
        return jdbcTemplate.update("delete from schedule where id = ?", id);
    }

    private RowMapper<ScheduleResponseDto> scheduleRowMapper() {
        return new RowMapper<ScheduleResponseDto>() {
            @Override
            public ScheduleResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new ScheduleResponseDto(
                        rs.getLong("id"),
                        rs.getLong("user_id"),
                        rs.getString("title"),
                        rs.getString("content"),
                        rs.getTimestamp("created_at").toLocalDateTime(),
                        rs.getTimestamp("updated_at").toLocalDateTime()
                );
            }
        };
    }

    private RowMapper<Schedule> scheduleRowMapperV2() {
        return new RowMapper<Schedule>() {
            @Override
            public Schedule mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new Schedule(
                        rs.getLong("id"),
                        rs.getLong("user_id"),
                        rs.getString("title"),
                        rs.getString("content"),
                        rs.getTimestamp("created_at").toLocalDateTime(),
                        rs.getTimestamp("updated_at").toLocalDateTime()
                );
            }
        };
    }
}
