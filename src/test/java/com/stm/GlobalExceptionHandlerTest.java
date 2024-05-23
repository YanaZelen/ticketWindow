package com.stm;

import static org.mockito.Mockito.doThrow;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc; 

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.stm.controller.TicketController;
import com.stm.service.TicketService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = TicketController.class, excludeAutoConfiguration = {GlobalExceptionHandler.class})
public class GlobalExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TicketService ticketService;

    @InjectMocks
    private GlobalExceptionHandler globalExceptionHandler;

    @Test
    public void testHandleNullPointerException() throws Exception {
        doThrow(new NullPointerException("Null value")).when(ticketService).getTicketById(1L);

        mockMvc.perform(get("/tickets/1"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Некорректный запрос"))
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.errors.error").value("Null value"));
    }

    @Test
    public void testHandleIllegalArgumentException() throws Exception {
        doThrow(new IllegalArgumentException("Illegal argument")).when(ticketService).getTicketById(1L);

        mockMvc.perform(get("/tickets/1"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Некорректные аргументы"))
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.errors.error").value("Illegal argument"));
    }

    @Test
    public void testHandleHttpMessageNotReadable() throws Exception {
        String unreadableJson = "{ userId: 0, routeId: 0, dateTime: 2024-06-01T12:00:00, seatNumber: 0, price: 0 }"; 

        mockMvc.perform(post("/tickets")
                .contentType(MediaType.APPLICATION_JSON)
                .content(unreadableJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Ошибка чтения HTTP-сообщения"))
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.errors.error").exists());
    }

    @Test
    public void testHandleGenericException() throws Exception {
        doThrow(new RuntimeException("Generic exception")).when(ticketService).getTicketById(1L);

        mockMvc.perform(get("/tickets/1"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").value("Внутренняя ошибка сервера"))
                .andExpect(jsonPath("$.status").value(500))
                .andExpect(jsonPath("$.errors.error").value("Generic exception"));
    }
}