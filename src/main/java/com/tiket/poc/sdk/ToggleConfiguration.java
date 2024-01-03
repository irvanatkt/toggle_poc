package com.tiket.poc.sdk;

import java.time.Duration;
import java.util.Map;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.kafka.receiver.KafkaReceiver;

@Configuration
public class ToggleConfiguration {

  private Map<String, Boolean> toggleMap;
  private Duration fetchInterval;
  private ListenerConfiguration listenerConfiguration;
  private VersionChecker versionChecker;

  public ToggleConfiguration(Duration fetchInterval) {
    this.fetchInterval = fetchInterval;
  }

  private static class ListenerConfiguration {

    private KafkaReceiver<String, String> receiver;


  }

  private static class VersionChecker {

    private WebClient webClient;
  }
}
