package com.stm;

import static org.mockito.Mockito.doThrow;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc; 

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.stm.controller.TicketController;
import com.stm.exception.GlobalExceptionHandler;
import com.stm.security.JwtTokenUtil;
import com.stm.service.TicketService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = TicketController.class, excludeAutoConfiguration = {GlobalExceptionHandler.class})
public class GlobalExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TicketService ticketService;
    
    @MockBean
    private JwtTokenUtil jwtTokenUtil;
   
    @InjectMocks
    private GlobalExceptionHandler globalExceptionHandler;

    @Test
    @WithMockUser(roles = "ADMINISTRATOR")
    public void testHandleNullPointerException() throws Exception {
        doThrow(new NullPointerException("Null value")).when(ticketService).getTicketById(1L);

        mockMvc.perform(get("/tickets/1"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Некорректный запрос"))
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.errors.error").value("Null value"));
    }

    @Test
    @WithMockUser(roles = "ADMINISTRATOR")
    public void testHandleIllegalArgumentException() throws Exception {
        doThrow(new IllegalArgumentException("Illegal argument")).when(ticketService).getTicketById(1L);

        mockMvc.perform(get("/tickets/1"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Некорректные аргументы"))
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.errors.error").value("Illegal argument"));
    }

    @Test
    @WithMockUser(roles = "ADMINISTRATOR")
    public void testHandleGenericException() throws Exception {
        doThrow(new RuntimeException("Generic exception")).when(ticketService).getTicketById(1L);

        mockMvc.perform(get("/tickets/1"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").value("Внутренняя ошибка сервера"))
                .andExpect(jsonPath("$.status").value(500))
                .andExpect(jsonPath("$.errors.error").value("Generic exception"));
    }
}