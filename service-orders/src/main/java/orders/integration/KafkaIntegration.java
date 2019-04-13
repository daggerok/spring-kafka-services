package orders.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import orders.domain.ProductInfo;
import orders.domain.ProductsInfos;
import orders.events.ProductAddedProjection;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.converter.ProjectingMessageConverter;

import static java.lang.String.format;

@Log4j2
@Configuration
@RequiredArgsConstructor
public class KafkaIntegration {

  private final ObjectMapper objectMapper;
  private final ProductsInfos productsInfos;

/*
  @KafkaListener(topics = "products")
  public void on(String jsonData) throws IOException {
    ProductAdded event = objectMapper.readValue(jsonData, ProductAdded.class);
    log.info("event: {}", event);
  }
*/

  @KafkaListener(topics = "products")
  void on(ProductAddedProjection event) {
    log.info(format("event: %s", event));
    productsInfos.save(ProductInfo.ofAll(event.getId(), event.getName(), event.getAmount()));
  }

  @Bean
  ProjectingMessageConverter projectingConverter() {
    return new ProjectingMessageConverter(objectMapper);
  }
}
