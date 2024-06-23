package com.sdc.webdevproject.repository.impl;

import com.sdc.webdevproject.entity.User;
import com.sdc.webdevproject.pool.DatabasePool;
import com.sdc.webdevproject.repository.UserRepository;
import org.tinylog.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepositoryImpl implements UserRepository {

    @Override
    public List<User> findAll() throws SQLException {
        Logger.debug("Fetching all users");
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";
        try (Connection connection = DatabasePool.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                users.add(mapRowToUser(rs));
            }
        }
        return users;
    }

    @Override
    public List<User> findWithPagination(int offset, int noOfRecords) throws SQLException {
        Logger.debug("Fetching users with pagination - offset: {}, records: {}", offset, noOfRecords);
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users LIMIT ? OFFSET ?";
        try (Connection connection = DatabasePool.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, noOfRecords);
            pstmt.setInt(2, offset);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    users.add(mapRowToUser(rs));
                }
            }
        }
        return users;
    }

    @Override
    public int getUserCount() throws SQLException {
        Logger.debug("Fetching user count");
        String sql = "SELECT COUNT(*) FROM users";
        try (Connection connection = DatabasePool.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }

    @Override
    public User findById(Long id) throws SQLException {
        Logger.debug("Fetching user with ID: {}", id);
        User user = null;
        String sql = "SELECT * FROM users WHERE id=?";
        try (Connection connection = DatabasePool.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    user = mapRowToUser(rs);
                }
            }
        }
        return user;
    }

    @Override
    public User findByEmail(String email) throws SQLException {
        Logger.debug("Fetching user with email: {}", email);
        User user = null;
        String sql = "SELECT * FROM users WHERE email=?";
        try (Connection connection = DatabasePool.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, email);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    user = mapRowToUser(rs);
                }
            }
        }
        return user;
    }

    public User findByNickname(String nickname) throws SQLException {
        Logger.debug("Fetching user with nickname: {}", nickname);
        User user = null;
        String sql = "SELECT * FROM users WHERE nickname=?";
        try (Connection connection = DatabasePool.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, nickname);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    user = mapRowToUser(rs);
                }
            }
        }
        return user;
    }

    public User findByConfirmationToken(String token) throws SQLException {
        Logger.debug("Fetching user with confirmation token: {}", token);
        User user = null;
        String sql = "SELECT * FROM users WHERE confirmation_token=?";
        try (Connection connection = DatabasePool.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, token);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    user = mapRowToUser(rs);
                }
            }
        }
        return user;
    }

    @Override
    public void save(User user) throws SQLException {
        Logger.debug("Saving user: {}", user);
        String sql = "INSERT INTO users (name, surname, nickname, password, email, confirmation_token, is_enabled, avatar) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = DatabasePool.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, user.getName());
            pstmt.setString(2, user.getSurname());
            pstmt.setString(3, user.getNickname());
            pstmt.setString(4, user.getPassword());
            pstmt.setString(5, user.getEmail());
            pstmt.setString(6, user.getConfirmationToken());
            pstmt.setBoolean(7, user.isEnabled());
            pstmt.setBytes(8, user.getAvatar()); // Set avatar data
            pstmt.executeUpdate();
        }
    }

    @Override
    public void update(User user) throws SQLException {
        Logger.debug("Updating user: {}", user);
        String sql = "UPDATE users SET name=?, surname=?, nickname=?, password=?, email=?, confirmation_token=?, is_enabled=?, avatar=? WHERE id=?";
        try (Connection connection = DatabasePool.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, user.getName());
            pstmt.setString(2, user.getSurname());
            pstmt.setString(3, user.getNickname());
            pstmt.setString(4, user.getPassword());
            pstmt.setString(5, user.getEmail());
            pstmt.setString(6, user.getConfirmationToken());
            pstmt.setBoolean(7, user.isEnabled());
            pstmt.setBytes(8, user.getAvatar()); // Set avatar data
            pstmt.setLong(9, user.getId());
            pstmt.executeUpdate();
        }
    }

    @Override
    public void delete(Long id) throws SQLException {
        Logger.debug("Deleting user with ID: {}", id);
        String sql = "DELETE FROM users WHERE id=?";
        try (Connection connection = DatabasePool.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            pstmt.executeUpdate();
        }
    }

    private User mapRowToUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getLong("id"));
        user.setName(rs.getString("name"));
        user.setSurname(rs.getString("surname"));
        user.setNickname(rs.getString("nickname"));
        user.setPassword(rs.getString("password"));
        user.setEmail(rs.getString("email"));
        user.setConfirmationToken(rs.getString("confirmation_token"));
        user.setEnabled(rs.getBoolean("is_enabled"));
        user.setAvatar(rs.getBytes("avatar")); // Map avatar data
        return user;
    }
}

