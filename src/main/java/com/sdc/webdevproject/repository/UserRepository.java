package com.sdc.webdevproject.repository;

import com.sdc.webdevproject.entity.User;
import java.sql.SQLException;
import java.util.List;

public interface UserRepository {
    List<User> findAll() throws SQLException;
    List<User> findWithPagination(int offset, int noOfRecords) throws SQLException;
    int getUserCount() throws SQLException;
    User findById(Long id) throws SQLException;
    User findByConfirmationToken(String token) throws SQLException;
    User findByEmail(String email) throws SQLException;
    User findByNickname(String nickname) throws SQLException;
    void save(User user) throws SQLException;
    void update(User user) throws SQLException;
    void delete(Long id) throws SQLException;

}