package com.tiket.poc.controller;

import com.tiket.poc.model.CreateToggleRequest;
import com.tiket.poc.model.CreateToggleResponse;
import com.tiket.poc.service.ToggleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/toggles")
public class ToggleController {

  @Autowired
  private ToggleService toggleService;

  @GetMapping
  public Mono<Boolean> getToggle(@RequestParam String key) {
    return toggleService.getKey(key);
  }

  @PostMapping
  public Mono<CreateToggleResponse> setToggle(@RequestBody CreateToggleRequest request) {
    return toggleService.addToggle(request)
        .thenReturn(CreateToggleResponse.builder().status("SUCCESS").build())
        .onErrorResume(err -> Mono.just(CreateToggleResponse.builder().status("FAILED").build()));
  }

  @PatchMapping
  public Mono<CreateToggleResponse> updateToggle(@RequestBody CreateToggleRequest request) {
    return toggleService.updateToggle(request)
        .map(val -> {
          if (Boolean.TRUE.equals(val)) {
            return CreateToggleResponse.builder().status("SUCCESS").build();
          }
          return CreateToggleResponse.builder().status("FAILED").build();
        })
        .onErrorResume(err -> Mono.just(CreateToggleResponse.builder().status("FAILED").build()));
  }
}
