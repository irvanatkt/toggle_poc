package com.tiket.poc.sdk;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.kafka.receiver.KafkaReceiver;

@Configuration
@ConditionalOnBean(ToggleKafkaConfiguration.class)
public class ToggleKafkaConfiguration implements ToggleClientService {

  private ConcurrentHashMap<String, Boolean> toggleMap;
  private final long fetchInterval;
  private KafkaReceiver<String, String> kafkaReceiver;
  private WebClient webClient;

  /*
  Self inject to provide schedule rate
   */
  @Autowired
  private ToggleKafkaConfiguration toggleKafkaConfiguration;

  public ToggleKafkaConfiguration(long fetchInterval) {
    this.fetchInterval = fetchInterval;

    initConsumerConfig();
    initToggleMap();
  }

  @Override
  public Mono<Boolean> isActive(String key) {
    return Mono.just(toggleMap.getOrDefault(key, false));
  }

  /**
   * consume and update toggleMap
   */
  @PostConstruct
  public void runKafkaListener() {
    kafkaReceiver.receive().retry().subscribe();
  }

  @PreDestroy
  private void disconnect() {

  }

  /**
   * calling toggle service periodically to check current version & update if version if different
   */
  @Scheduled(fixedDelayString = "#{@toggleKafkaConfiguration.fetchInterval}")
  public void runScheduler() {
  }

  /**
   * initiate toggleMap by call toggle-service endpoint
   */
  private void initToggleMap() {
  }

  /**
   * - every service pod has unique consumer group
   * - new joiner consumer should only consume latest committed offset
   */
  private void initConsumerConfig() {
//    props.put(ConsumerConfig.GROUP_ID_CONFIG, "vertical+podId");
  }

}
