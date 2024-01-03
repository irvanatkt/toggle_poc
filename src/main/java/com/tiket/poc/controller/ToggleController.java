package com.tiket.poc.controller;

import com.tiket.poc.model.CreateToggleRequest;
import com.tiket.poc.service.ToggleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/toggles")
public class ToggleController {

  @Autowired
  private ToggleService toggleService;

  @GetMapping
  public Mono<String> getCurrentToggle() {
    return toggleService.getAll();
  }

  @PostMapping
  public Mono<Void> setToggle(@RequestBody CreateToggleRequest request) {
    return toggleService.addToggle(request);
  }
}
