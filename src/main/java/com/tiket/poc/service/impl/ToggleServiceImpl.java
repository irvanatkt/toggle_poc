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
  public Mono<Boolean> getKey(String key) {
    return redisOperations.opsForValue().get(key)
        .map(Object::toString)
        .map(Boolean::parseBoolean)
        .defaultIfEmpty(Boolean.FALSE);
  }

  @Override
  public Mono<Void> addToggle(CreateToggleRequest request) {
    return redisOperations.opsForValue()
        .setIfAbsent(request.getKey(), request.isActive()).then();
  }

  @Override
  public Mono<Boolean> updateToggle(CreateToggleRequest request) {
    return redisOperations.opsForValue()
        .getAndSet(request.getKey(), request.isActive())
        .map(Object::toString)
        .map(Boolean::parseBoolean)
        .onErrorResume(err -> Mono.just(Boolean.FALSE));
  }

  @Override
  public Mono<Void> deleteToggle(String key) {
    return redisOperations.opsForValue()
        .getAndDelete(key)
        .then();
  }
}
