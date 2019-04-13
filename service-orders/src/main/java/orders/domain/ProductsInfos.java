package orders.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductsInfos extends JpaRepository<ProductInfo, UUID> {}
