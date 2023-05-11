package com.example.userservice.controller;

import com.example.userservice.dto.ResponseDto;
import com.example.userservice.model.Model;
import com.example.userservice.repository.ModelRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private ModelRepository modelRepository;
    private final RedisTemplate<String, String> redisTemplate;

    public UserController(ModelRepository modelRepository, RedisTemplate<String, String> redisTemplate) {
        this.modelRepository = modelRepository;
        this.redisTemplate = redisTemplate;
    }

    @GetMapping("/get")
    public ResponseEntity<String> get() {
        String newString = "some value to show";
        return ResponseEntity.ok(newString);
    }

    @GetMapping("/info")
    public ResponseEntity<List<ResponseDto>> getUserEntities(@CookieValue(value = "token", required = false) String token) {
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
        Flux<Model> byUserId = modelRepository.findAllByUserIdAndId(Long.valueOf(userId));

        List<ResponseDto> mainResponse = new ArrayList<>();

        byUserId.subscribe(t -> {
            ResponseDto responseDto = new ResponseDto();
            responseDto.setName(t.getName());
            responseDto.setUserId(t.getUserId().getId());
            mainResponse.add(responseDto);
        });

        return ResponseEntity.ok(mainResponse);
    }
}
