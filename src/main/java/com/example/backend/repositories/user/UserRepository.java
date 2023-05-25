package com.example.backend.repositories.user;

import com.example.backend.entities.User;

import java.util.List;

public interface UserRepository {
    List<User> allUsers();
    User addUser(User user);
    User findUser(String email);
    void userActivity(String email);
    User updateUser(User user, String email);
    void deleteUser(String email);
}
