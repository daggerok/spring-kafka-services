package kafka;

import lombok.SneakyThrows;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.test.EmbeddedKafkaBroker;

@SpringBootApplication
public class KafkaApp {

  @Bean
  @SneakyThrows
  EmbeddedKafkaBroker embeddedKafkaBroker() {
    //return new EmbeddedKafkaBroker(3, false, "products", "orders", "inventory", "purchase");
    return new EmbeddedKafkaBroker(3, false, "products")
        .kafkaPorts(9092, 9093, 9094);
  }

  public static void main(String[] args) {
    SpringApplication.run(KafkaApp.class, args);
  }
}
