package com.example.officesecurity.service.impl;

import com.example.officesecurity.model.UserDAO;
import com.example.officesecurity.repository.UserRepository;
import com.example.officesecurity.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public void addUser(UserDAO userDAO) {

        userRepository.save(userDAO);

    }

    @Override
    public void saveUser(UserDAO userDAO) {

        userRepository.save(userDAO);
    }


}
