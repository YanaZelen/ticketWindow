package com.test_stm.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class User {
    private Long id;
    private String username;
    private String password;
}
