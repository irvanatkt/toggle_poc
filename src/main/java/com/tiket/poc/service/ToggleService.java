package com.tiket.poc.service;

import com.tiket.poc.model.CreateToggleRequest;
import reactor.core.publisher.Mono;

public interface ToggleService {

  Mono<String> getAll();

  Mono<Void> addToggle(CreateToggleRequest request);
}
