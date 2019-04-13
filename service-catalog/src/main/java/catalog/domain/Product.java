package catalog.domain;

import catalog.events.ProductCreated;
import lombok.*;
import org.springframework.data.domain.AbstractAggregateRoot;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

import static lombok.AccessLevel.PACKAGE;

@Entity
@Getter
@ToString
@Setter(PACKAGE)
@Table(name = "products")
@NoArgsConstructor(access = PACKAGE)
@RequiredArgsConstructor(staticName = "of")
@EqualsAndHashCode(exclude = "aggregateId", callSuper = false)
public class Product extends AbstractAggregateRoot<Product> {

  @Id
  @GeneratedValue
  UUID aggregateId;

  @NonNull
  String name;

  @NonNull
  Long qty;

  public Product toCreateProduct() {
    registerEvent(new ProductCreated(this));
    return this;
  }
}
