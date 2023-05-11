package com.example.userservice.controller;

import com.example.userservice.model.Model;
import com.example.userservice.repository.ModelRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Autowired
    private ModelRepository modelRepository;
    private final RedisTemplate<String, String> redisTemplate;

    public UserController(RedisTemplate<String, String> redisTemplate) {

        this.redisTemplate = redisTemplate;
    }

    @GetMapping("/info")
    public ResponseEntity<Model> getUserEntities(@CookieValue(value = "token", required = false) String token) {
        // Проверка наличия токена в куках
        if (token == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // Проверка наличия идентификатора пользователя в Redis
        String userId = redisTemplate.opsForValue().get(token);
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // Получение всех записей сущностей пользователя
        Model entities = modelRepository.findByUserId(Long.parseLong(userId)).orElseThrow(() -> new RuntimeException("No user found!"));

        return ResponseEntity.ok(entities);
    }
}
