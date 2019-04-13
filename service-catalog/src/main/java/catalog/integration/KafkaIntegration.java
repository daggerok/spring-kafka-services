package catalog.integration;

import catalog.events.ProductCreated;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.KafkaOperations;

@Log4j2
@Configuration
@RequiredArgsConstructor
public class KafkaIntegration {

  private final ObjectMapper objectMapper;
  private final KafkaOperations<Object, Object> kafkaOperations;

  @EventListener
  public void on(ProductCreated event) throws JsonProcessingException {
    log.info("received from spring: {}", event);
    String data = objectMapper.writeValueAsString(event);
    log.info("publishing to kafka: {}", data);
    kafkaOperations.send("products", data);
  }
}
