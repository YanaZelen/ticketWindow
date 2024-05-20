package com.test_stm.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ticket {
    private Long id;
    private Long userId;
    private Long routeId;
    private LocalDateTime dateTime;
    private String seatNumber;
    private double price;
    private boolean isAvailable;
}
