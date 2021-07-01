package com.example.officesecurity.service;

import com.example.officesecurity.model.EventEntity;
import com.example.officesecurity.model.UserDAO;
import com.example.officesecurity.service.impl.UserDTO;

import java.io.ByteArrayInputStream;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.List;


public interface UserService {
    void addUser(UserDTO userDTO);

    void loginUser(UserDTO userDTO);

    void logoutUser(UserDTO userDTO);

    List<EventEntity> getTimeLogs();

    List<EventEntity> getLogsForSpecificTime( String start,String end);

    List<UserDAO> getAllUsersByPages(Integer pageNo, Integer pageSize, String sortBy);
    List<UserDAO> findAll();
}
