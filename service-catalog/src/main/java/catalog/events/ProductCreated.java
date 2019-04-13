package catalog.events;

import catalog.domain.Product;
import lombok.ToString;
import lombok.Value;

@Value
@ToString
public class ProductCreated {
  private final Product product;
}
