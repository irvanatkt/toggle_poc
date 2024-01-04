package com.tiket.poc.service.impl;


import com.tiket.poc.model.CreateToggleRequest;
import com.tiket.poc.service.ToggleService;
import java.time.Duration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class ToggleServiceImpl implements ToggleService {

  @Autowired
  ReactiveRedisOperations<String, Object> redisOperations;

  @Override
  public Mono<String> getAll() {
    return redisOperations.opsForValue().get("fee")
        .map(Object::toString)
        .defaultIfEmpty("toggle");
  }

  @Override
  public Mono<Void> addToggle(CreateToggleRequest request) {
    return redisOperations.opsForValue()
        .set(request.getKey(), request.isActive(), Duration.ofSeconds(0)).then();
  }
}
