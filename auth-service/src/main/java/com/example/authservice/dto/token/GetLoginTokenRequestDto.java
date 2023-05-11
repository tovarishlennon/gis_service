package com.example.authservice.dto.token;

import lombok.Data;

@Data
public class GetLoginTokenRequestDto {
    private String username;
    private String password;
}
