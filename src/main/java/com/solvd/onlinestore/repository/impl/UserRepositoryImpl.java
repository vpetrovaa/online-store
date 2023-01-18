package com.solvd.onlinestore.repository.impl;

import com.solvd.onlinestore.domain.User;
import com.solvd.onlinestore.domain.exception.SqlException;
import com.solvd.onlinestore.repository.DataSourceConfig;
import com.solvd.onlinestore.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private static final String CREATE_QUERY = "insert into users(name, surname, email, phone, password, registration_time) values(?, ?, ?, ?, ?, ?);";
    private static final String FIND_BY_ID = "select id, name, surname, email, phone, password, role, registration_time from users where id = ?";
    private static final String EXIST_BY_EMAIL = "select id from users where email = ?";

    private final DataSourceConfig dataSource;

    @Override
    public void create(User user) {
        try {
            Connection conn = Objects.requireNonNull(dataSource.getConnection());
            try (PreparedStatement ps = conn.prepareStatement(CREATE_QUERY, Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, user.getName());
                ps.setString(2, user.getSurname());
                ps.setString(3, user.getEmail());
                ps.setString(4, user.getPhone());
                ps.setString(5, user.getPassword());
                ps.setTimestamp(6, Timestamp.valueOf(LocalDateTime.now()));
                ps.executeUpdate();
                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        user.setId(generatedKeys.getLong(1));
                    }
                }
            }
        } catch (SQLException e) {
            throw new SqlException("Exception in saving user with email " + user.getEmail());
        }
    }

    @Override
    public Optional<User> findById(Long id) {
        try {
            Connection conn = dataSource.getConnection();
            try (PreparedStatement ps = conn.prepareStatement(FIND_BY_ID)) {
                ps.setLong(1, id);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        User user = new User();
                        user.setId(id);
                        user.setName(rs.getString("name"));
                        user.setSurname(rs.getString("surname"));
                        user.setEmail(rs.getString("email"));
                        user.setPhone(rs.getString("phone"));
                        user.setPassword(rs.getString("password"));
                        user.setRole(User.Role.valueOf(rs.getString("role")));
                        user.setRegistrationTime(rs.getTimestamp("registration_time").toLocalDateTime());
                        return Optional.of(user);
                    }
                }
            }
        } catch (SQLException e) {
            throw new SqlException("Exception in finding user with id " + id);
        }
        return Optional.empty();
    }


    @Override
    public boolean isExistByEmail(String email) {
        Long idFromDb = null;
        try {
            Connection conn = dataSource.getConnection();
            try (PreparedStatement ps = conn.prepareStatement(EXIST_BY_EMAIL)) {
                ps.setString(1, email);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        idFromDb = rs.getLong("id");
                    }
                }
            }
        } catch (SQLException e) {
            throw new SqlException("Exception in checking user by email" + email);
        }
        return idFromDb != null;
    }

}
