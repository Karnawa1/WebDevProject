package com.sdc.webdevproject.service.impl;

import com.sdc.webdevproject.entity.User;
import com.sdc.webdevproject.exception.AuthException;
import com.sdc.webdevproject.repository.UserRepository;
import com.sdc.webdevproject.repository.impl.UserRepositoryImpl;
import com.sdc.webdevproject.service.AuthService;
import com.sdc.webdevproject.util.EmailUtil;
import com.sdc.webdevproject.util.PasswordUtil;
import com.sdc.webdevproject.util.TokenGenerator;
import jakarta.mail.MessagingException;
import jakarta.servlet.ServletException;
import org.tinylog.Logger;

import java.sql.SQLException;

public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository = new UserRepositoryImpl();

    @Override
    public User signIn(String email, String password) throws SQLException {
        Logger.debug("Sing in user: {}", email);
        User user = userRepository.findByEmail(email);
        if (user != null && PasswordUtil.verifyPassword(password, user.getPassword())) {
            if (!user.isEnabled()) {
                Logger.warn("User account not enabled: {}", email);
                return null;
            }
            Logger.debug("Sing in successful for user: {}", email);
            return user;
        }
        Logger.warn("Sing in failed for user: {}", email);
        return null;
    }

    @Override
    public void signUp(User user) throws SQLException {
        Logger.debug("Signing up user: {}", user.getEmail());
        user.setPassword(PasswordUtil.encryptPassword(user.getPassword()));
        user.setConfirmationToken(TokenGenerator.generateToken());
        user.setEnabled(false); // user is not enabled until email is confirmed
        userRepository.save(user);

        String confirmationLink = "http://localhost:8080" + "/WebDevProject_war_exploded" + "/auth?action=confirm&token=" + user.getConfirmationToken();
        String emailContent = "Dear " + user.getName() + ",\n\nPlease click the following link to confirm your registration:\n" + confirmationLink;
        try {
            EmailUtil.sendEmail(user.getEmail(), "Registration Confirmation", emailContent);
        } catch (MessagingException e) {
            Logger.error("Failed to send confirmation email", e);
        }
    }

    @Override
    public void confirmUser(String token) throws SQLException {
        Logger.debug("Confirming user with token: {}", token);
        User user = userRepository.findByConfirmationToken(token);
        if (user != null) {
            user.setEnabled(true);
            user.setConfirmationToken(null);
            userRepository.update(user);
        } else {
            Logger.warn("Invalid confirmation token: {}", token);
            throw new IllegalArgumentException("Invalid confirmation token");
        }
    }
}
