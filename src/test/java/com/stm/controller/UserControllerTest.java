package com.stm.controller;

import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stm.model.Role;
import com.stm.model.User;
import com.stm.security.JwtTokenUtil;
import com.stm.security.SecurityConfig;
import com.stm.service.UserService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@Import(SecurityConfig.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private JwtTokenUtil jwtTokenUtil;

    @Test
    @WithMockUser(roles = "ADMINISTRATOR")
    public void testGetAllUsers() throws Exception {
        User user1 = new User(1L, "user1", "password1", Role.ROLE_ADMINISTRATOR);
        User user2 = new User(2L, "user2", "password2", Role.ROLE_USER);
        List<User> users = Arrays.asList(user1, user2);
        when(userService.getAllUsers()).thenReturn(users);

        mockMvc.perform(MockMvcRequestBuilders.get("/users"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(user1.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].username").value(user1.getUsername()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].role").value(user1.getRole().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(user2.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].username").value(user2.getUsername()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].role").value(user2.getRole().toString()));

        verify(userService, times(1)).getAllUsers();
        verifyNoMoreInteractions(userService);
    }

    @Test
    @WithMockUser(roles = "ADMINISTRATOR")
    public void testGetUserById() throws Exception {
        Long userId = 1L;
        User user = new User(userId, "user1", "password1", Role.ROLE_ADMINISTRATOR);
        when(userService.getUserById(userId)).thenReturn(user);

        mockMvc.perform(MockMvcRequestBuilders.get("/users/{id}", userId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(user.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value(user.getUsername()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.role").value(user.getRole().toString()));

        verify(userService, times(1)).getUserById(userId);
        verifyNoMoreInteractions(userService);
    }

    @Test
    @WithMockUser(roles = "ADMINISTRATOR")
    public void testRegisterUser() throws Exception {
        User newUser = new User(1L, "newUser", "newPassword", Role.ROLE_USER);
        when(userService.registerUser(any(User.class))).thenReturn(newUser);

        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(newUser)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(newUser.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value(newUser.getUsername()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.role").value(newUser.getRole().toString()));

        verify(userService, times(1)).registerUser(any(User.class));
        verifyNoMoreInteractions(userService);
    }

    @Test
    @WithMockUser(roles = "ADMINISTRATOR")
    public void testUpdateUser() throws Exception {
        Long userId = 1L;
        User updatedUser = new User(userId, "updatedUser", "updatedPassword", Role.ROLE_USER);

        mockMvc.perform(MockMvcRequestBuilders.put("/users/{id}", userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(updatedUser)))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(userService, times(1)).updateUser(updatedUser);
        verifyNoMoreInteractions(userService);
    }

    @Test
    @WithMockUser(roles = "ADMINISTRATOR")
    public void testDeleteUser() throws Exception {
        Long userId = 1L;

        mockMvc.perform(MockMvcRequestBuilders.delete("/users/{id}", userId))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(userService, times(1)).deleteUser(userId);
        verifyNoMoreInteractions(userService);
    }
}