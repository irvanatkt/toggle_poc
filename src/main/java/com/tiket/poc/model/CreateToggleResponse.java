package com.tiket.poc.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class CreateToggleResponse {

  private String status;

  public CreateToggleResponse(String status) {
    this.status = status;
  }
}
