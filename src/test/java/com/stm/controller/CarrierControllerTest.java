package com.stm.controller;

import static org.mockito.Mockito.when;

import com.stm.service.KafkaProducerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stm.model.Carrier;
import com.stm.security.JwtTokenUtil;
import com.stm.security.SecurityConfig;
import com.stm.service.CarrierService;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Import(SecurityConfig.class)
@WebMvcTest(CarrierController.class)
public class CarrierControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CarrierService carrierService;

    @MockBean
    private JwtTokenUtil jwtTokenUtil;

    @MockBean
    private KafkaProducerService kafkaProducerService;

    @Test
    @WithMockUser(roles = "ADMINISTRATOR")
    public void testGetCarrierById() throws Exception {
        Long id = 1L;
        Carrier carrier = new Carrier(id, "Carrier1", "1234567890");
        when(carrierService.getCarrierById(id)).thenReturn(carrier);

        mockMvc.perform(get("/carriers/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Carrier1")))
                .andExpect(jsonPath("$.phone", is("1234567890")));
    }

    @Test
    @WithMockUser(roles = "ADMINISTRATOR")
    public void testCreateCarrier() throws Exception {
        Carrier carrier = new Carrier(null, "New Carrier", "9876543210");
        Carrier savedCarrier = new Carrier(1L, "New Carrier", "9876543210");
        when(carrierService.createCarrier(carrier)).thenReturn(savedCarrier);

        mockMvc.perform(post("/carriers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(carrier)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("New Carrier")))
                .andExpect(jsonPath("$.phone", is("9876543210")));
    }

    @Test
    @WithMockUser(roles = "ADMINISTRATOR")
    public void testUpdateCarrier() throws Exception {
        Long id = 1L;
        Carrier updatedCarrier = new Carrier(id, "Updated Carrier", "1111111111");

        mockMvc.perform(put("/carriers/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(updatedCarrier)))
                .andExpect(status().isOk());

        verify(carrierService).updateCarrier(updatedCarrier);
    }

    @Test
    @WithMockUser(roles = "ADMINISTRATOR")
    public void testDeleteCarrier() throws Exception {
        Long id = 1L;

        mockMvc.perform(delete("/carriers/{id}", id))
                .andExpect(status().isOk());

        verify(carrierService).deleteCarrier(id);
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}