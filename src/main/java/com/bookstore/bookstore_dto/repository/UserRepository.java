package com.bookstore.bookstore_dto.repository;

import com.bookstore.bookstore_dto.domain.User;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
@Slf4j
public class UserRepository {

    private final JdbcTemplate jdbcTemplate;
    private DataSource dataSource;

    /**
     * 회원가입
     */
    public void save(User user) {
        jdbcTemplate.update("insert into user(user_id, password, name) values (?, ?, ?)", user.getUser_id(), user.getPassword(), user.getName());
    }

    /**
     * 회원가입시 동일한 회원이 있는지 검사
     * 로그인
     */
    public Optional<User> findByUserId(String user_id) throws Exception {
        List<User> query = jdbcTemplate.query("select * from user where user_id = ?", findRowMapper(), user_id);
//        return query.isEmpty() ? null : query.get(0);
        return query.isEmpty() ? Optional.empty() : Optional.of(query.get(0));
    }

    public RowMapper<User> findRowMapper() {
        return (rs, rowNum) -> {
            User user = new User();
            user.setUser_id(rs.getString("user_id"));
            user.setPassword(rs.getString("password"));
            user.setName(rs.getString("name"));
            return user;
        };
    }
}
