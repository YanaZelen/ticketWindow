package com.test_stm.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Route {
    private Long id;
    private String departurePoint;
    private String destinationPoint;
    private Long carrierId;
    private int durationInMinutes;

    @Override
    public String toString() {
        return "Carrier{" +
                "id=" + id +
                ", departurePoint='" + departurePoint + '\'' +
                ", destinationPoint='" + destinationPoint + '\'' +
                ", carrierId='" + carrierId + '\'' +
                ", durationInMinutes='" + durationInMinutes + '\'' +
                '}';
    }
}