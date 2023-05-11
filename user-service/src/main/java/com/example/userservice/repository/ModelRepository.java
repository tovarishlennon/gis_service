package com.example.userservice.repository;

import com.example.userservice.model.Model;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;


@Repository
public interface ModelRepository extends ReactiveCrudRepository<Model, Long> {
    Flux<Model> findAllByUserIdAndId(Long userId);
}
