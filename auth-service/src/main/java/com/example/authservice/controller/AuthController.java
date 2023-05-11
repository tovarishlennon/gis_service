package com.example.authservice.controller;

import com.example.authservice.model.Users;
import com.example.authservice.dto.token.GetLoginTokenRequestDto;
import com.example.authservice.dto.token.GetLoginTokenResponseDto;
import com.example.authservice.repository.UsersRepository;
import com.example.authservice.service.AuthService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService authService;

    private final UsersRepository usersRepository;

    private final RedisTemplate<String, String> redisTemplate;

    private final PasswordEncoder passwordEncoder;

    @GetMapping("/get")
    public ResponseEntity<String> get() {
        String newString = "some value to show";
        return ResponseEntity.ok(newString);
    }

    // первоначально мне хотелось сделать все через SpringSecurity, но изучив подробно материал в интернете на основе ТЗ, я пришел к выводу, что мне не нужно делать чего-то сверхестественного
    // не удалил данный эндпоинт только для экономии своего времени и чтобы ничего не сломалось по пути
    @PostMapping("/login")
    public ResponseEntity<GetLoginTokenResponseDto> adminEndpoint(@RequestBody GetLoginTokenRequestDto dto) {
        GetLoginTokenResponseDto responseDto = authService.login(dto);
        return ResponseEntity.ok(responseDto);
    }

    // токен логирую в системе и не показываю его пользователю
    @PostMapping("/login/cookie")
    public ResponseEntity<String> loginCookie(@RequestBody GetLoginTokenRequestDto dto, HttpServletResponse response) {
        Users user = usersRepository.findByUsername(dto.getUsername()).orElseThrow(() -> new RuntimeException("User not found!"));

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }

        // Генерация и сохранение токена в Redis
        String token = UUID.randomUUID().toString();
        redisTemplate.opsForValue().set(token, user.getId().toString());

        // Установка куки с токеном
        Cookie cookie = new Cookie("token", token);
        cookie.setPath("/");
        response.addCookie(cookie);

        log.info(token);

        return ResponseEntity.ok("Login successful");
    }

}
