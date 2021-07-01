package com.example.officesecurity.repository;

import com.example.officesecurity.model.EventEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TimeRepository extends JpaRepository<EventEntity, LocalDateTime> {
    List<EventEntity> findAll();
    EventEntity findByUserName(String userName);
    List<EventEntity> findAllByEventTimeBetween(LocalDateTime start,LocalDateTime end);

}