package com.example.schedulemanagement.repository;

import com.example.schedulemanagement.dto.UserResponseDto;
import com.example.schedulemanagement.entity.User;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class JdbcTemplateUserRepository implements UserRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateUserRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public UserResponseDto createUser(User user) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(this.jdbcTemplate);
        jdbcInsert.withTableName("user").usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("username", user.getUsername());
        parameters.put("password", user.getPassword());
        parameters.put("email", user.getEmail());

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));

        return new UserResponseDto(key.longValue(),
                user.getUsername(),
                user.getPassword(),
                user.getEmail());
    }

    @Override
    public User findByUserIdOrElseThrow(Long userId) {
        List<User> result = jdbcTemplate.query("select * from user where id = ?", userRowMapperV2(), userId);

        return result.stream()
                .findAny()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }

    @Override
    public User updateUser(Long id, String username) {
        String sql = "update user set username = ? where id = ?";

        int updatedRows = jdbcTemplate.update(sql, username, id);

        if (updatedRows == 0) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "User not found or no changes made"
            );
        }

        return findByUserIdOrElseThrow(id);
    }

    private RowMapper<User> userRowMapperV2() {
        return new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new User(
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("email")
                );
            }
        };
    }
}
