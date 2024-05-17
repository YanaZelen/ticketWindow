package com.test_stm.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class Ticket {
    private Long id;
    private Long routeId;
    private LocalDateTime dateTime;
    private String seatNumber;
    private double price;
}
