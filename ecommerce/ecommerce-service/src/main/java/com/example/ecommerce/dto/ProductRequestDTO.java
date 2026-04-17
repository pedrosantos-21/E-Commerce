package com.example.ecommerce.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * DTO (Data Transfer Object) para requisições de criação ou atualização de produtos.
 * Utilizado para encapsular os dados recebidos nas requisições HTTP, garantindo validação
 * e separação entre a camada de apresentação e o modelo de domínio.
 */
public record ProductRequestDTO(
    /**
     * O nome do produto. Não pode ser vazio.
     */
    @NotBlank(message = "O nome do produto é obrigatório")
    String name,

    /**
     * A descrição do produto. Pode ser nula.
     */
    String description,

    /**
     * O preço do produto. Não pode ser nulo e deve ser maior ou igual a zero.
     */
    @NotNull(message = "O preço é obrigatório")
    @DecimalMin(value = "0.0", inclusive = true, message = "O preço deve ser maior ou igual a zero")
    BigDecimal price,

    /**
     * A marca do produto. Não pode ser vazia.
     */
    @NotBlank(message = "A marca é obrigatória")
    String brand,

    /**
     * O estoque do produto. Não pode ser nulo e deve ser maior ou igual a zero.
     */
    @NotNull(message = "O estoque é obrigatório")
    @Min(value = 0, message = "O estoque não pode ser negativo")
    Integer stock
) {}
