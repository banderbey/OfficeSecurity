package com.example.officesecurity.service;

import com.example.officesecurity.model.UserDAO;


public interface UserService {
    void addUser(UserDAO userDAO);
    void saveUser(UserDAO userDAO);

}
