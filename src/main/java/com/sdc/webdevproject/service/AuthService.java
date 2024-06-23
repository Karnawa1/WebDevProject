package com.sdc.webdevproject.service;

import com.sdc.webdevproject.entity.User;
import java.sql.SQLException;

public interface AuthService {
    void signUp(User user) throws SQLException;
    User signIn(String email, String password) throws SQLException;
    void confirmUser(String token) throws SQLException;
}
