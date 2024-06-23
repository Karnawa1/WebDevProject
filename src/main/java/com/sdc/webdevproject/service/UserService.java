package com.sdc.webdevproject.service;

import com.sdc.webdevproject.entity.User;
import java.sql.SQLException;
import java.util.List;

public interface UserService {
    List<User> getAllUsers() throws SQLException;
    List<User> getUsers(int offset, int noOfRecords) throws SQLException;
    int getUserCount() throws SQLException;
    User getUserById(Long id) throws SQLException;
    void createUser(User user) throws SQLException;
    void updateUser(User user) throws SQLException;
    void deleteUser(Long id) throws SQLException;
}