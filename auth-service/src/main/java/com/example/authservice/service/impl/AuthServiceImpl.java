package com.example.authservice.service.impl;

import com.example.authservice.dto.token.GenerateTokenResponseDto;
import com.example.authservice.dto.token.GetLoginTokenRequestDto;
import com.example.authservice.dto.token.GetLoginTokenResponseDto;
import com.example.authservice.service.AuthService;
import com.example.authservice.utils.JwtGenerator;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;

    private final JwtGenerator jwtGenerator;

    @Override
    public GetLoginTokenResponseDto login(GetLoginTokenRequestDto dto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        dto.getUsername(),
                        dto.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        GenerateTokenResponseDto responseFromJwtGenerator = jwtGenerator.generateToken(authentication);

        return new GetLoginTokenResponseDto(0, responseFromJwtGenerator.getToken(), responseFromJwtGenerator.getExpiresAt());
    }

}
