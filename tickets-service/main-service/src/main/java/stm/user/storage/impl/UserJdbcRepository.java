package stm.user.storage.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import stm.user.model.User;
import stm.user.storage.UserRepository;

import java.sql.PreparedStatement;
import java.util.Objects;

@Repository
@RequiredArgsConstructor
public class UserJdbcRepository implements UserRepository {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public User save(User user) {
        final String sqlQuery = "INSERT INTO users (login, password, full_name) VALUES ( ?, ?, ?)";
        KeyHolder generatedId = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            final PreparedStatement stmt = connection.prepareStatement(sqlQuery, new String[]{"id"});
            stmt.setString(1, user.getLogin());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getFullName());
            return stmt;
        }, generatedId);
        user.setId(Objects.requireNonNull(generatedId.getKey()).intValue());
        return user;
    }
}
