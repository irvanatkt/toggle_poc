package com.tiket.poc.sdk;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import java.time.Duration;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import reactor.core.publisher.Mono;
import reactor.kafka.receiver.KafkaReceiver;

@Configuration
@ConditionalOnBean(ToggleKafkaConfiguration.class)
public class ToggleKafkaConfiguration implements ToggleClientService {

  private Map<String, Boolean> toggleMap;
  private final long fetchInterval;
  private KafkaReceiver<String, String> kafkaReceiver;

  /*
  Self inject to provide schedule rate
   */
  @Autowired
  private ToggleKafkaConfiguration toggleKafkaConfiguration;

  public ToggleKafkaConfiguration(long fetchInterval) {
    this.fetchInterval = fetchInterval;
  }

  @Override
  public Mono<Boolean> isActive(String key) {
    return Mono.just(toggleMap.getOrDefault(key, false));
  }

  @PostConstruct
  public void runKafkaListener() {
    kafkaReceiver.receive().retry().subscribe();
  }

  @PreDestroy
  private void disconnect(){

  }

  @Scheduled(fixedDelayString = "#{@toggleKafkaConfiguration.fetchInterval}")
  public void runScheduler() {
    // Scheduler logic
  }
}
