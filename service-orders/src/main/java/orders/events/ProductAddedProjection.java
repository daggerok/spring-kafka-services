package orders.events;

import org.springframework.data.web.JsonPath;
import org.springframework.data.web.ProjectedPayload;

import java.util.Formattable;
import java.util.Formatter;
import java.util.UUID;

@ProjectedPayload // requires ProjectingMessageConverter, see KafkaIntegration
public interface ProductAddedProjection extends Formattable {

  @JsonPath("$.product.aggregateId")
  UUID getId();

  @JsonPath("$.product.name")
  String getName();

  @JsonPath("$.product.qty")
  Long getAmount();

  @Override
  default void formatTo(Formatter formatter, int flags, int width, int precision) {
    formatter.format("Product(id=%s, name=%s, amount=%s)", getId(), getName(), getAmount());
  }
}
