package orders.events;

import lombok.Data;

@Data
public class ProductAdded {

  private Product product;

  @Data
  public static class Product {
    private String aggregateId;
    private String name;
    private Long qty;
  }
}
