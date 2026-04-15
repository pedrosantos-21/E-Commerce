package com.example.ecommerce.repository;

import com.example.ecommerce.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Interface de repositório para a entidade {@link Product}.
 * Estende {@link JpaRepository} para fornecer operações CRUD básicas
 * e funcionalidades de paginação e ordenação para a entidade Product.
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {
}
