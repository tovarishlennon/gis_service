package com.example.authservice.service;


import com.example.authservice.dto.token.GetLoginTokenRequestDto;
import com.example.authservice.dto.token.GetLoginTokenResponseDto;

public interface AuthService {
    GetLoginTokenResponseDto login(GetLoginTokenRequestDto dto);
}
