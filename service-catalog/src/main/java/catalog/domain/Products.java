package catalog.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface Products extends JpaRepository<Product, UUID> {}
