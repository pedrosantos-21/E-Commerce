package com.example.ecommerce.repository;

import com.example.ecommerce.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Interface de repositório para a entidade {@link Customer}.
 * Estende {@link JpaRepository} para fornecer operações CRUD básicas
 * e funcionalidades de paginação e ordenação para a entidade Customer.
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer, UUID> {
    /**
     * Busca um cliente pelo seu endereço de email.
     * O email é um campo único para o cliente.
     * @param email O endereço de email do cliente.
     * @return Um {@link Optional} contendo o cliente, se encontrado, ou vazio caso contrário.
     */
    Optional<Customer> findByEmail(String email);
}
