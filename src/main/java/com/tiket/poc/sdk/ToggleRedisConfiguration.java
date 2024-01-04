package com.tiket.poc.sdk;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import reactor.core.publisher.Mono;

@Configuration
@ConditionalOnBean(ToggleRedisConfiguration.class)
public class ToggleRedisConfiguration implements ToggleClientService {

  private ReactiveRedisTemplate<String, Boolean> reactiveRedisTemplate;

  public ToggleRedisConfiguration(ReactiveRedisConnectionFactory factory) {
    Jackson2JsonRedisSerializer<Boolean> serializer = new Jackson2JsonRedisSerializer<>(
        Boolean.class);

    RedisSerializationContext.RedisSerializationContextBuilder<String, Boolean> builder =
        RedisSerializationContext.newSerializationContext(new StringRedisSerializer());

    RedisSerializationContext<String, Boolean> context = builder.value(serializer).build();

    this.reactiveRedisTemplate = new ReactiveRedisTemplate<>(factory, context);
  }

  @Override
  public Mono<Boolean> isActive(String key) {
    return reactiveRedisTemplate.opsForValue().get(key)
        .map(Object::toString)
        .map(Boolean::parseBoolean)
        .defaultIfEmpty(Boolean.FALSE);
  }
}
