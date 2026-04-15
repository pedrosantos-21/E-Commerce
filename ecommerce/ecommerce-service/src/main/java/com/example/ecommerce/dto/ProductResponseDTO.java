package com.example.ecommerce.dto;

import com.example.ecommerce.model.Product;
import java.math.BigDecimal;
import java.util.UUID;

/**
 * DTO (Data Transfer Object) para respostas de produtos.
 * Utilizado para encapsular os dados do produto a serem retornados nas requisições HTTP.
 */
public record ProductResponseDTO(
    /**
     * O ID único do produto.
     */
    UUID id,
    /**
     * O nome do produto.
     */
    String name,
    /**
     * A descrição do produto.
     */
    String description,
    /**
     * O preço do produto.
     */
    BigDecimal price,
    /**
     * A marca do produto.
     */
    String brand,
    /**
     * O estoque do produto.
     */
    Integer stock
) {
    /**
     * Construtor que mapeia um objeto Product para um ProductResponseDTO.
     * @param product O objeto Product a ser mapeado.
     */
    public ProductResponseDTO(Product product) {
        this(product.getId(),
             product.getName(),
             product.getDescription(),
             product.getPrice(),
             product.getBrand(),
             product.getStock());
    }
}
