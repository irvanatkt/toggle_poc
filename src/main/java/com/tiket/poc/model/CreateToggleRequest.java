package com.tiket.poc.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateToggleRequest {

  private String key;
  private boolean isActive;

  @JsonCreator
  public CreateToggleRequest(@JsonProperty String key, @JsonProperty boolean isActive) {
    this.key = key;
    this.isActive = isActive;
  }
}
