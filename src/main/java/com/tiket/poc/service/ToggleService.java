package com.tiket.poc.service;

import com.tiket.poc.model.CreateToggleRequest;
import reactor.core.publisher.Mono;

public interface ToggleService {

  Mono<Boolean> getKey(String key);

  Mono<Void> addToggle(CreateToggleRequest request);

  Mono<Boolean> updateToggle(CreateToggleRequest request);

  Mono<Void> deleteToggle(String key);
}
