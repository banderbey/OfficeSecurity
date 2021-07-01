package com.example.officesecurity.model;

import com.example.officesecurity.enums.Status;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;
@Data
@Entity
public class EventEntity {
    private String userName;
    private Status event;
    private LocalDateTime eventTime;

    @Id
    @GeneratedValue
    private Integer id;
}
