package com.stm.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table("ticket")
public class Ticket {

    @Id
    private Long id;

    @NotNull(message = "User ID cannot be null")
    private Long userId;

    @NotNull(message = "Route ID cannot be null")
    private Long routeId;

    @NotNull(message = "Date and time cannot be null")
    private LocalDateTime dateTime;

    @NotNull(message = "Seat number cannot be null")
    private String seatNumber;

    @Positive(message = "Price must be positive")
    private double price;

    private boolean isAvailable;

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", user id='" + userId + '\'' +
                ", route id='" + routeId + '\'' +
                ", date and time='" + dateTime + '\'' +
                ", seat number='" + seatNumber + '\'' +
                ", price='" + price + '\'' +
                ", availability='" + isAvailable + '\'' +
                '}';
    }
}
