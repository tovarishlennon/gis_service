package com.example.userservice.controller;

import com.example.userservice.dto.ResponseDto;
import com.example.userservice.model.Model;
import com.example.userservice.repository.ModelRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class UserControllerTest {

    @Autowired
    private  ModelRepository modelRepository;

    @Test
    void getUserEntities() {
        Flux<Model> byUserId = modelRepository.findAllByUserIdAndId(Long.valueOf(1));
        List<ResponseDto> mainResponse = new ArrayList<>();

        byUserId.subscribe(t -> {
            ResponseDto responseDto = new ResponseDto();
            responseDto.setName(t.getName());
            responseDto.setUserId(t.getUserId().getId());
            mainResponse.add(responseDto);
        });

        Assertions.assertNotNull(mainResponse);
    }
}