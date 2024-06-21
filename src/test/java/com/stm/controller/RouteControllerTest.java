package com.stm.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stm.model.Route;
import com.stm.security.JwtTokenUtil;
import com.stm.security.SecurityConfig;
import com.stm.service.KafkaProducerService;
import com.stm.service.RouteService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

@WebMvcTest(RouteController.class)
@Import(SecurityConfig.class)
public class RouteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RouteService routeService;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private JwtTokenUtil jwtTokenUtil;

    @MockBean
    private KafkaProducerService kafkaProducerService;

    private Route route1;
    private Route route2;

    @BeforeEach
    public void setup() {
        route1 = new Route(1L, "Point A", "Point B", 100L, 120);
        route2 = new Route(2L, "Point C", "Point D", 101L, 180);
    }

    @Test
    @WithMockUser(roles = "ADMINISTRATOR")
    public void testGetAllRoutes() throws Exception {
        List<Route> routes = Arrays.asList(route1, route2);
        when(routeService.getAllRoutes()).thenReturn(routes);

        mockMvc.perform(get("/routes")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(route1.getId()))
                .andExpect(jsonPath("$[0].departurePoint").value(route1.getDeparturePoint()))
                .andExpect(jsonPath("$[0].destinationPoint").value(route1.getDestinationPoint()))
                .andExpect(jsonPath("$[1].id").value(route2.getId()))
                .andExpect(jsonPath("$[1].departurePoint").value(route2.getDeparturePoint()))
                .andExpect(jsonPath("$[1].destinationPoint").value(route2.getDestinationPoint()));
    }

    @Test
    @WithMockUser(roles = "ADMINISTRATOR")
    public void testGetRouteById() throws Exception {
        when(routeService.getRouteById(1L)).thenReturn(route1);

        mockMvc.perform(get("/routes/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(route1.toString()));
    }

    @Test
    @WithMockUser(roles = "ADMINISTRATOR")
    public void testCreateRoute() throws Exception {
        Route newRoute = new Route(null, "Point E", "Point F", 102L, 200);

        mockMvc.perform(post("/routes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newRoute)))
                .andExpect(status().isOk());

        verify(routeService, times(1)).createRoute(any(Route.class));
    }

    @Test
    @WithMockUser(roles = "ADMINISTRATOR")
    public void testUpdateRoute() throws Exception {
        Route updatedRoute = new Route(1L, "Updated Point A", "Updated Point B", 100L, 120);

        mockMvc.perform(put("/routes/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedRoute)))
                .andExpect(status().isOk());

        verify(routeService, times(1)).updateRoute(any(Route.class));
    }

    @Test
    @WithMockUser(roles = "ADMINISTRATOR")
    public void testDeleteRoute() throws Exception {
        mockMvc.perform(delete("/routes/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(routeService, times(1)).deleteRoute(1L);
    }
}