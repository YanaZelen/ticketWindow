package com.stm.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.stm.model.Ticket;
import com.stm.service.TicketService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(TicketController.class)
public class TicketControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TicketService ticketService;

    @Test
    public void testGetAllTickets() throws Exception {
        List<Ticket> tickets = Arrays.asList(new Ticket(), new Ticket());
        when(ticketService.getAllTickets()).thenReturn(tickets);

        mockMvc.perform(get("/tickets"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(tickets.size()));

        verify(ticketService, times(1)).getAllTickets();
    }

    @Test
    public void testGetTicketById() throws Exception {
        Ticket ticket = new Ticket();
        when(ticketService.getTicketById(anyLong())).thenReturn(ticket);

        mockMvc.perform(get("/tickets/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists());

        verify(ticketService, times(1)).getTicketById(1L);
    }

    @Test
    public void testCreateTicket() throws Exception {
        String ticketJson = "{ \"departure\": \"NYC\", \"destination\": \"LA\", \"dateTime\": \"2024-06-01T12:00:00\" }";

        mockMvc.perform(post("/tickets")
                .contentType(MediaType.APPLICATION_JSON)
                .content(ticketJson))
                .andExpect(status().isOk());

        verify(ticketService, times(1)).createTicket(any(Ticket.class));
    }

    @Test
    public void testUpdateTicket() throws Exception {
        String ticketJson = "{ \"departure\": \"NYC\", \"destination\": \"LA\", \"dateTime\": \"2024-06-01T12:00:00\" }";

        mockMvc.perform(put("/tickets/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(ticketJson))
                .andExpect(status().isOk());

        verify(ticketService, times(1)).updateTicket(any(Ticket.class));
    }

    @Test
    public void testDeleteTicket() throws Exception {
        doNothing().when(ticketService).deleteTicket(anyLong());

        mockMvc.perform(delete("/tickets/{id}", 1L))
                .andExpect(status().isOk());

        verify(ticketService, times(1)).deleteTicket(1L);
    }

    @Test
    public void testSearchTickets() throws Exception {
        Page<Ticket> ticketPage = new PageImpl<>(Arrays.asList(new Ticket(), new Ticket()), PageRequest.of(0, 10), 2);
        when(ticketService.searchTickets(anyString(), anyString(), anyString(), any(LocalDateTime.class), anyInt(), anyInt()))
                .thenReturn(ticketPage);
    
        mockMvc.perform(get("/tickets/search")
                .param("departure", "NYC")
                .param("destination", "LA")
                .param("carrier", "Carrier")
                .param("dateTime", "2024-06-01T12:00:00")
                .param("page", "0")
                .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").exists())
                .andExpect(jsonPath("$.content.length()").value(ticketPage.getContent().size()))
                .andExpect(jsonPath("$.totalElements").value(ticketPage.getTotalElements()));
    
        verify(ticketService, times(1)).searchTickets(anyString(), anyString(), anyString(), any(LocalDateTime.class), anyInt(), anyInt());
    }

    @Test
    public void testPurchaseTicket() throws Exception {
        when(ticketService.purchaseTicket(anyLong())).thenReturn(true);

        mockMvc.perform(post("/tickets/{id}/purchase", 1L))
                .andExpect(status().isOk())
                .andExpect(content().string("Ticket purchased successfully"));

        verify(ticketService, times(1)).purchaseTicket(1L);
    }

    @Test
    public void testGetAllTicketsForUser() throws Exception {
        List<Ticket> tickets = Arrays.asList(new Ticket(), new Ticket());
        when(ticketService.getAllTicketsForUser(anyLong())).thenReturn(tickets);

        mockMvc.perform(get("/tickets/purchased")
                .param("userId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(tickets.size()));

        verify(ticketService, times(1)).getAllTicketsForUser(1L);
    }
}