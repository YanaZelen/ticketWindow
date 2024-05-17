package com.test_stm.service;

import com.test_stm.model.Ticket;
import com.test_stm.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    public List<Ticket> getAllTickets() {
        return (List<Ticket>) ticketRepository.findAll();
    }

    public Optional<Ticket> getTicketById(Long id) {
        return ticketRepository.findById(id);
    }

    public Ticket createTicket(Ticket ticket) {
        return ticketRepository.save(ticket);
    }

    public void updateTicket(Ticket ticket) {
        ticketRepository.update(ticket);
    }

    public void deleteTicket(Long id) {
        ticketRepository.deleteById(id);
    }
}