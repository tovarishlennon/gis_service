package com.example.authservice.dto.token;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class GetLoginTokenResponseDto {
    private Integer code;
    private String accessToken;
    private ZonedDateTime expiresAt;

    public GetLoginTokenResponseDto(Integer code, String accessToken, ZonedDateTime expiresAt) {
        this.code = code;
        this.accessToken = accessToken;
        this.expiresAt = expiresAt;
    }
}
