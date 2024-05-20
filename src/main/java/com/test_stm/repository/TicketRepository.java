package com.test_stm.repository;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.test_stm.model.Ticket;

public interface TicketRepository extends CrudRepository<Ticket, Long> {

    void update(Ticket ticket);

     @Query("SELECT t FROM Ticket t WHERE " +
            "(:departure IS NULL OR t.departure LIKE %:departure%) AND " +
            "(:destination IS NULL OR t.destination LIKE %:destination%) AND " +
            "(:carrier IS NULL OR t.carrier LIKE %:carrier%) AND " +
            "(:dateTime IS NULL OR t.dateTime = :dateTime)")
    Page<Ticket> searchTickets(String departure, String destination, String carrier, LocalDateTime dateTime, PageRequest pageRequest);
}
