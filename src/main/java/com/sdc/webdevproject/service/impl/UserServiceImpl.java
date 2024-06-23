package com.sdc.webdevproject.service.impl;

import com.sdc.webdevproject.entity.User;
import com.sdc.webdevproject.exception.UserNotFoundException;
import com.sdc.webdevproject.repository.UserRepository;
import com.sdc.webdevproject.repository.impl.UserRepositoryImpl;
import com.sdc.webdevproject.service.UserService;
import com.sdc.webdevproject.util.PasswordUtil;
import org.tinylog.Logger;

import java.sql.SQLException;
import java.util.List;

public class UserServiceImpl implements UserService {

    private final UserRepository userRepository = new UserRepositoryImpl();

    @Override
    public List<User> getAllUsers() throws SQLException {
        Logger.debug("Fetching all users");
        return userRepository.findAll();
    }

    @Override
    public List<User> getUsers(int offset, int noOfRecords) throws SQLException {
        Logger.debug("Fetching users with pagination - offset: {}, records: {}", offset, noOfRecords);
        return userRepository.findWithPagination(offset, noOfRecords);
    }

    @Override
    public int getUserCount() throws SQLException {
        Logger.debug("Fetching user count");
        return userRepository.getUserCount();
    }

    @Override
    public User getUserById(Long id) throws SQLException {
        Logger.debug("Fetching user with ID: {}", id);
        return userRepository.findById(id);
    }

    @Override
    public void createUser(User user) throws SQLException {
        Logger.debug("Creating user: {}", user.getEmail());
        user.setPassword(PasswordUtil.encryptPassword(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    public void updateUser(User user) throws SQLException {
        Logger.debug("Updating user: {}", user.getId());
        try {
            User existingUser = userRepository.findById(user.getId());
            if (existingUser == null) {
                throw new UserNotFoundException("User not found for ID: " + user.getId());
            }
            if (user.getPassword() != null && !user.getPassword().isEmpty()) {
                if (!PasswordUtil.verifyPassword(user.getPassword(), existingUser.getPassword())) {
                    user.setPassword(PasswordUtil.encryptPassword(user.getPassword()));
                }
            } else {
                user.setPassword(existingUser.getPassword());
            }
            if (!isUniqueNickname(user.getNickname(), user.getId())) {
                throw new IllegalArgumentException("Nickname already in use");
            }
            if (!isUniqueEmail(user.getEmail(), user.getId())) {
                throw new IllegalArgumentException("Email already in use");
            }
            userRepository.update(user);
        } catch (UserNotFoundException e) {
            Logger.error("User not found: {}", e.getMessage());
            throw new RuntimeException(e); // Or handle it in another appropriate way
        }
    }

    private boolean isUniqueNickname(String nickname, Long userId) throws SQLException {
        User user = userRepository.findByNickname(nickname);
        return user == null || user.getId().equals(userId);
    }

    private boolean isUniqueEmail(String email, Long userId) throws SQLException {
        User user = userRepository.findByEmail(email);
        return user == null || user.getId().equals(userId);
    }

    @Override
    public void deleteUser(Long id) throws SQLException {
        Logger.debug("Deleting user with ID: {}", id);
        userRepository.delete(id);
    }
}

