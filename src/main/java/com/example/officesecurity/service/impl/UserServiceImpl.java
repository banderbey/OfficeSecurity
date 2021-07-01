package com.example.officesecurity.service.impl;

import com.example.officesecurity.exeptions.BadRequestException;
import com.example.officesecurity.model.EventEntity;
import com.example.officesecurity.model.UserDAO;
import com.example.officesecurity.repository.TimeRepository;
import com.example.officesecurity.repository.UserRepository;
import com.example.officesecurity.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {


    private final UserRepository userRepository;
    private final TimeRepository timeRepository;

    @Override
    public void addUser(UserDTO userDTO) {
        UserDAO byUserName = userRepository.findByUserName(userDTO.getUserName());
        if (byUserName == null) {

            UserDAO userDAO = new UserDAO();
            userDAO.setUserName(userDTO.getUserName());
            userDAO.setLoggedIn(userDTO.isLoggedIn());
            userRepository.save(userDAO);
        } else {
            throw new BadRequestException("user exist");
        }
    }

    @Override

    public void loginUser(UserDTO userDTO) {
        UserDAO userDAOFromDB = userRepository.findByUserName(userDTO.getUserName());
        if (userDAOFromDB == null || userDAOFromDB.isLoggedIn() == true) {

            throw new BadRequestException("Not Found or you are logged in already");
        } else {
            userRepository.save(userDAOFromDB);
        }


    }

    @Override
    public void logoutUser(UserDTO userDTO) {
        UserDAO userDAOFromDB = userRepository.findByUserName(userDTO.getUserName());
        if (userDAOFromDB == null || userDAOFromDB.isLoggedIn() == false) {

            throw new BadRequestException("Not Found or you are logged out already");
        }

        userRepository.save(userDAOFromDB);
    }

    @Override
    public List<EventEntity> getTimeLogs() {
        return timeRepository.findAll();
    }

    @Override
    public List<EventEntity> getLogsForSpecificTime(String start, String end) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-ddHH:mm:ss");
        LocalDateTime start1 = LocalDateTime.parse(start, formatter);
        LocalDateTime end1 = LocalDateTime.parse(end, formatter);
        return timeRepository.findAllByEventTimeBetween(start1, end1);
    }


    @Override
    public List<UserDAO> getAllUsersByPages(Integer pageNo, Integer pageSize, String sortBy) {
        if (pageNo < 0) {
            throw new BadRequestException("pageNo is less then zero");

        }
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

        Page<UserDAO> pagedResult = userRepository.findAll(paging);

        if (pagedResult.hasContent()) {
            return pagedResult.getContent();
        } else {
            throw new BadRequestException("No data found!");
        }
    }

    @Override
    public  List<UserDAO> findAll() {
         List<UserDAO> userDAOList = userRepository.findAll();
        return userDAOList;
    }
}







