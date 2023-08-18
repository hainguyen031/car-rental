package com.hainguyen.carrental.dto;

import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String username;
    private String password;
    private String phone;
    private String email;
}
