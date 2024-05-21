package com.test_stm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.test_stm.model.Ticket;
import com.test_stm.service.TicketService;

import java.time.LocalDateTime;
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

    @GetMapping("/{id}")
    public ResponseEntity<Ticket> getTicketById(@PathVariable Long id) {
        return ResponseEntity.ok(ticketService.getTicketById(id));
    }

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

    @GetMapping("/search")
    public ResponseEntity<Page<Ticket>> searchTickets(
            @RequestParam String departure,
            @RequestParam String destination,
            @RequestParam String carrier,
            @RequestParam LocalDateTime dateTime,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<Ticket> result = ticketService.searchTickets(departure, destination, carrier, dateTime, page, size);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/{id}/purchase")
    public ResponseEntity<String> purchaseTicket(@PathVariable Long id) {
        boolean success = ticketService.purchaseTicket(id);
        if (success) {
            return ResponseEntity.ok("Ticket purchased successfully");
        } else {
            return ResponseEntity.badRequest().body("Ticket is already purchased");
        }
    }

    @GetMapping("/purchased")
    public ResponseEntity<List<Ticket>> getAllTicketsForUser(@RequestParam Long userId) {
        List<Ticket> tickets = ticketService.getAllTicketsForUser(userId);
        return ResponseEntity.ok(tickets);
    }
}