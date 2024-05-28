package com.stm.controller;

import com.stm.service.TicketService;
import com.stm.model.Ticket;
import com.stm.security.JwtTokenUtil;
import com.stm.security.SecurityConfig;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@WebMvcTest(TicketController.class)
@Import(SecurityConfig.class)
public class TicketControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TicketService ticketService;

    @MockBean
    private JwtTokenUtil jwtTokenUtil;

    @Test
    @WithMockUser(roles = "USER")
    public void testGetAllTickets() throws Exception {
        List<Ticket> tickets = Arrays.asList(new Ticket(1L, 1L, 1L, LocalDateTime.now(), "A1", 100.0, true));
        when(ticketService.getAllTickets()).thenReturn(tickets);

        mockMvc.perform(get("/tickets"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L));
    }

    @Test
    @WithMockUser(roles = "USER")
    public void testGetTicketById() throws Exception {
        Ticket ticket = new Ticket(1L, 1L, 1L, LocalDateTime.now(), "A1", 100.0, true);
        when(ticketService.getTicketById(1L)).thenReturn(ticket);

        mockMvc.perform(get("/tickets/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    @WithMockUser(roles = "ADMINISTRATOR")
    public void testCreateTicket() throws Exception {
        String ticketJson = "{ \"userId\": 1, \"routeId\": 1, \"dateTime\": \"2024-05-28T00:00:00\", \"seatNumber\": \"A1\", \"price\": 100.0, \"isAvailable\": true }";

        mockMvc.perform(post("/tickets/admin").contentType(MediaType.APPLICATION_JSON)
                        .content(ticketJson)
                        .with(csrf()))
                .andExpect(status().isOk());

        verify(ticketService, times(1)).createTicket(any(Ticket.class));
    }

    @Test
    @WithMockUser(roles = "ADMINISTRATOR")
    public void testUpdateTicket() throws Exception {
        String ticketJson = "{ \"userId\": 1, \"routeId\": 1, \"dateTime\": \"2024-05-28T00:00:00\", \"seatNumber\": \"A1\", \"price\": 100.0, \"isAvailable\": true }";

        mockMvc.perform(put("/tickets/admin/1").contentType(MediaType.APPLICATION_JSON)
                        .content(ticketJson)
                        .with(csrf()))
                .andExpect(status().isOk());

        verify(ticketService, times(1)).updateTicket(any(Ticket.class));
    }

    @Test
    @WithMockUser(roles = "ADMINISTRATOR")
    public void testDeleteTicket() throws Exception {
        mockMvc.perform(delete("/tickets/admin/1")
                        .with(csrf()))
                .andExpect(status().isOk());

        verify(ticketService, times(1)).deleteTicket(1L);
    }

    @Test
    @WithMockUser(roles = "USER")
    public void testPurchaseTicket() throws Exception {
        when(ticketService.purchaseTicket(1L)).thenReturn(true);

        mockMvc.perform(post("/tickets/1/purchase").with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().string("Ticket purchased successfully"));
    }

    @Test
    @WithMockUser(roles = "USER")
    public void testGetAllTicketsForUser() throws Exception {
        List<Ticket> tickets = Arrays.asList(new Ticket(1L, 1L, 1L, LocalDateTime.now(), "A1", 100.0, true));
        when(ticketService.getAllTicketsForUser(1L)).thenReturn(tickets);

        mockMvc.perform(get("/tickets/purchased?userId=1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L));
    }

    @Test
    @WithMockUser(roles = "USER")
    public void testSearchTickets() throws Exception {
        Page<Ticket> tickets = new PageImpl<>(Arrays.asList(new Ticket(1L, 1L, 1L, LocalDateTime.now(), "A1", 100.0, true)));
        when(ticketService.searchTickets(anyString(), anyString(), anyInt(), any(LocalDateTime.class), anyInt(), anyInt())).thenReturn(tickets);
    
        mockMvc.perform(get("/tickets/search")
                        .param("departure", "A")
                        .param("destination", "B")
                        .param("carrier", "1") 
                        .param("dateTime", "2024-05-28T00:00:00")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(1L));
    }

    @Test
    @WithMockUser(roles = "ADMINISTRATOR")
    public void testSearchTicketsForAdministrator() throws Exception {
        Page<Ticket> tickets = new PageImpl<>(Arrays.asList(new Ticket(1L, 1L, 1L, LocalDateTime.now(), "A1", 100.0, true)));
        when(ticketService.searchTickets(anyString(), anyString(), anyInt(), any(LocalDateTime.class), anyInt(), anyInt())).thenReturn(tickets);

        mockMvc.perform(get("/tickets/search")
                        .param("departure", "A")
                        .param("destination", "B")
                        .param("carrier", "1")
                        .param("dateTime", "2024-05-28T00:00:00")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(1L));
    }

    @Test
    @WithMockUser(roles = "USER")
    public void testCannotPurchaseAlreadyPurchasedTicket() throws Exception {
        when(ticketService.purchaseTicket(1L)).thenReturn(true);
        mockMvc.perform(post("/tickets/1/purchase").with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().string("Ticket purchased successfully"));

        when(ticketService.purchaseTicket(1L)).thenReturn(false);
        mockMvc.perform(post("/tickets/1/purchase").with(csrf()))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Ticket is already purchased"));
    }
}