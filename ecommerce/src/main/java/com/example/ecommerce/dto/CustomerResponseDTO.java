package com.example.ecommerce.dto;

import com.example.ecommerce.model.Customer;
import java.math.BigInteger;
import java.text.Normalizer;
import java.util.Date;
import java.util.UUID;

/**
 * DTO (Data Transfer Object) para respostas de clientes.
 * Utilizado para encapsular os dados do cliente a serem retornados nas requisições HTTP,
 * garantindo que informações sensíveis (como a senha) não sejam expostas.
 */
public record CustomerResponseDTO(
    /**
     * O ID único do cliente.
     */
    UUID id,
    /**
     * O endereço de email do cliente.
     */
    String email,
    /**
     * O nome completo do cliente.
     */
    String name,
    /**
     * O CEP (Código de Endereçamento Postal) do cliente.
     */
    String cep,
    /**
     * O CPF (Cadastro de Pessoas Físicas) do cliente.
     */
    BigInteger cpf,
    /**
     * A data de nascimento do cliente.
     */
    Date birth,
    /**
     * O gênero do cliente.
     */
    Normalizer.Form gender,
    /**
     * O sexo do cliente.
     */
    Normalizer.Form sex,
    /**
     * O número de contato do cliente.
     */
    BigInteger contactNumber,
    java.util.List<ProductResponseDTO> products
) {
    /**
     * Construtor que mapeia um objeto Customer para um CustomerResponseDTO.
     * @param customer O objeto Customer a ser mapeado.
     */
    public CustomerResponseDTO(Customer customer) {
        this(customer.getId(),
             customer.getEmail(),
             customer.getName(),
             customer.getCep(),
             customer.getCpf(),
             customer.getBirth(),
             customer.getGender(),
             customer.getSex(),
             customer.getContactNumber(),
             customer.getProducts() != null ? 
                customer.getProducts().stream().map(ProductResponseDTO::new).toList() : 
                java.util.Collections.emptyList());
    }
}
