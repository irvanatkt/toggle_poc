package com.tiket.poc.sdk;

import reactor.core.publisher.Mono;

public interface ToggleClientService {
  Mono<Boolean> isActive(String key);
}
