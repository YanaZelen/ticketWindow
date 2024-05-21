package com.test_stm.service;

import org.springframework.stereotype.Service;

import com.test_stm.dao.UserDAO;
import com.test_stm.model.User;

import lombok.extern.slf4j.Slf4j;

import java.sql.SQLException;
import java.util.List;

@Service
@Slf4j
public class UserService {

    private UserDAO userDAO;

    public UserService() {
        this.userDAO = new UserDAO();
    }

    public User registerUser(User user) {
        try {
            if (userDAO.isUsernameTaken(user.getUsername())) {
                throw new SQLException("Username already taken");
            }
            userDAO.createUser(user);
        } catch (SQLException ex) {
            log.error("Can't register user: " + ex.getMessage(), ex);
        }
        return user;
    }

    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }

    public User getUserById(Long id) {
        return userDAO.getUserById(id);
    }

    public void updateUser(User user) {
        userDAO.updateUser(user);
    }

    public void deleteUser(Long id) {
        userDAO.deleteUser(id);
    }
}