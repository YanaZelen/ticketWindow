package com.test_stm.repository;

import org.springframework.data.repository.CrudRepository;

import com.test_stm.model.Ticket;

public interface TicketRepository extends CrudRepository<Ticket, Long> {

    void update(Ticket ticket);
}
