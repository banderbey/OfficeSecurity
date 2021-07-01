package com.example.officesecurity.springAspects;

import com.example.officesecurity.enums.Status;
import com.example.officesecurity.exeptions.BadRequestException;
import com.example.officesecurity.model.EventEntity;
import com.example.officesecurity.model.UserDAO;
import com.example.officesecurity.repository.TimeRepository;
import com.example.officesecurity.repository.UserRepository;
import com.example.officesecurity.service.impl.UserDTO;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Component
@EnableAspectJAutoProxy
@Aspect
public class EventLogAspect {
    private final UserRepository userRepository;
    private final TimeRepository timeRepository;

    @Pointcut("execution(public * com.example.officesecurity.service.impl.UserServiceImpl.loginUser(..)) && args(userDTO,..)) ")
    public void eventLogForLogin(UserDTO userDTO) {
    }

    @Pointcut("execution(public * com.example.officesecurity.service.impl.UserServiceImpl.logoutUser(..)) && args(userDTO,..)) ")
    public void eventLogForLogout(UserDTO userDTO) {
    }


    @After("eventLogForLogin(userDTO)")
    public void afterEventLogForLogin(JoinPoint joinPoint, UserDTO userDTO) {
        UserDAO userDAOFromDB = userRepository.findByUserName(userDTO.getUserName());
        if (userDAOFromDB == null || userDAOFromDB.isLoggedIn() == true) {

            throw new BadRequestException("you are logged in already");
        } else {
            userDAOFromDB.setLoggedIn(true);
            EventEntity eventEntity = new EventEntity();
            eventEntity.setUserName(userDAOFromDB.getUserName());
            eventEntity.setEventTime(LocalDateTime.now());
            eventEntity.setEvent(Status.SUCCESSFULLY_LOGGED_IN);
            timeRepository.save(eventEntity);
        }
    }

    @After("eventLogForLogout(userDTO)")
    public void afterEventLogForLogout(JoinPoint joinPoint, UserDTO userDTO) {
        UserDAO userDAOFromDB = userRepository.findByUserName(userDTO.getUserName());
        if (userDAOFromDB == null || userDAOFromDB.isLoggedIn() == false) {

            throw new BadRequestException(" you are logged out already");
        } else {
            userDAOFromDB.setLoggedIn(false);
            EventEntity eventEntity = new EventEntity();
            eventEntity.setUserName(userDAOFromDB.getUserName());
            eventEntity.setEventTime(LocalDateTime.now());
            eventEntity.setEvent(Status.SUCCESSFULLY_LOGGED_OUT);
            timeRepository.save(eventEntity);
        }

    }
}


