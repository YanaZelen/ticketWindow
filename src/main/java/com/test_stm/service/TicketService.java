package com.test_stm.service;

import com.test_stm.dao.TicketDAO;
import com.test_stm.model.Ticket;
import com.test_stm.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    private final TicketDAO ticketDAO = new TicketDAO();

    public List<Ticket> getAllTickets() {
        return (List<Ticket>) ticketRepository.findAll();
    }

    public Ticket getTicketById(Long id) {
        return ticketRepository.findById(id).orElseThrow(() -> new RuntimeException("Ticket not found"));
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

    public Page<Ticket> searchTickets(Optional<String> departure, Optional<String> destination, Optional<String> carrier,
                                      Optional<LocalDateTime> dateTime, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return ticketRepository.searchTickets(departure.orElse(""), destination.orElse(""), carrier.orElse(""),
                dateTime.orElse(null), pageRequest);
    }

    public boolean purchaseTicket(Long id) throws SQLException {
        return ticketDAO.purchaseTicket(id);
    }

    public List<Ticket> getAllTicketsForUser(Long userId) throws SQLException {
        return ticketDAO.getAllTicketsForUser(userId);
    }
}

/*
 * расскажи мне подробнее как мне реализовать работу с базой данных для своего приложения. сейчас у меня есть папка java, с основным кодом приложения, папка resourse с application.properties) и файл DatabaseUtil.java, так же есть dockerfile и файл docker-compose.yml
 * Я хочу использовать postgreSQL разворачивая ее с помощью докера, и так же поднимать само приложение в докере. Что мне нужно добавить в мой проект и как должны выглядеть мои dockerfile и файл docker-compose.yml?
 */