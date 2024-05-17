package com.test_stm.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class Route {
    private Long id;
    private String departurePoint;
    private String destinationPoint;
    private Long carrierId;
    private int durationInMinutes;
}