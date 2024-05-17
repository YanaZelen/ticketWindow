package com.test_stm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.test_stm.model.Ticket;
import com.test_stm.service.TicketService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tickets")
public class TicketController {
    @Autowired
    private TicketService ticketService;

    @GetMapping
    public List<Ticket> getAllTickets() {
        return ticketService.getAllTickets();
    }

    // @GetMapping("/{id}")
    // public ResponseEntity<Ticket> getTicketById(@PathVariable Long id) {
    //     Optional<Ticket> ticket = ticketService.getTicketById(id);
    //     return ticket.map(ResponseEntity::ok)
    //             .orElseGet(() -> ResponseEntity.notFound().build());
    // }

    @PostMapping
    public void createTicket(@RequestBody Ticket ticket) {
        ticketService.createTicket(ticket);
    }

    @PutMapping("/{id}")
    public void updateTicket(@PathVariable Long id, @RequestBody Ticket ticket) {
        ticket.setId(id);
        ticketService.updateTicket(ticket);
    }

    @DeleteMapping("/{id}")
    public void deleteTicket(@PathVariable Long id) {
        ticketService.deleteTicket(id);
    }
}