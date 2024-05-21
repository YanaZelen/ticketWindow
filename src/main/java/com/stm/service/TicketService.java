package com.stm.service;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.stm.dao.TicketDAO;
import com.stm.model.Ticket;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TicketService {

    private final TicketDAO ticketDAO = new TicketDAO();

    public List<Ticket> getAllTickets() {
        return ticketDAO.getAllTickets();
    }

    public Ticket getTicketById(Long id) {
        return ticketDAO.getTicketById(id);
    }

    public Ticket createTicket(Ticket ticket) {
        return ticketDAO.createTicket(ticket);
    }

    public void updateTicket(Ticket ticket) {
        ticketDAO.updateTicket(ticket);
    }

    public void deleteTicket(Long id) {
        ticketDAO.deleteTicket(id);
    }

    public Page<Ticket> searchTickets(String departure, String destination,
            String carrier, LocalDateTime dateTime, int page, int size) {
        return ticketDAO.searchTickets(departure, destination, carrier, dateTime, page, size);
    }

    public boolean purchaseTicket(Long id) {
        return ticketDAO.purchaseTicket(id);
    }

    public List<Ticket> getAllTicketsForUser(Long userId) {
        return ticketDAO.getAllTicketsForUser(userId);
    }
}