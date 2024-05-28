package com.stm.service;

import org.springframework.stereotype.Service;

import com.stm.dao.UserDAO;
import com.stm.model.User;


import java.util.List;

@Service
public class UserService {

    private UserDAO userDAO;

    public UserService() {
        this.userDAO = new UserDAO();
    }

    public User registerUser(User user) {
        if (userDAO.isUsernameTaken(user.getUsername())) {
            throw new RuntimeException("Пользователь с таким именем уже существует");
        }

        userDAO.createUser(user);
        return user;
    }

    public User getByUsername(String username) {
        return userDAO.getUserByName(username);

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