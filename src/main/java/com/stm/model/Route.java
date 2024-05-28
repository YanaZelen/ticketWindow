package com.stm.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table("route")
public class Route {

    @Id
    private Long id;

    @NotNull(message = "Departure cannot be null")
    private String departurePoint;

    @NotNull(message = "Destination cannot be null")
    private String destinationPoint;

    @NotNull(message = "Carrier Id cannot be null")
    private Long carrierId;

    @NotNull(message = "Duration cannot be null")
    private int durationInMinutes;

    @Override
    public String toString() {
        return "Route{" +
                "id=" + id +
                ", departurePoint='" + departurePoint + '\'' +
                ", destinationPoint='" + destinationPoint + '\'' +
                ", carrierId='" + carrierId + '\'' +
                ", durationInMinutes='" + durationInMinutes + '\'' +
                '}';
    }
}