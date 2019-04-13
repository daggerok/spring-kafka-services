package orders.domain;

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
@Setter(PACKAGE)
@Table(name = "product_infos")
@NoArgsConstructor(access = PACKAGE)
@AllArgsConstructor(staticName = "ofAll")
@EqualsAndHashCode(exclude = "aggregateId", callSuper = false)
public class ProductInfo extends AbstractAggregateRoot<ProductInfo> {

  @Id
  @GeneratedValue
  private UUID aggregateId;

  private String name;

  private Long qty;
}
