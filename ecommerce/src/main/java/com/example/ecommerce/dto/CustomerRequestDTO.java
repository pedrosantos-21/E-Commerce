package com.example.ecommerce.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import java.math.BigInteger;
import java.util.Date;

/**
 * DTO (Data Transfer Object) para requisições de criação ou atualização de clientes.
 * Utilizado para encapsular os dados recebidos nas requisições HTTP, garantindo validação
 * e separação entre a camada de apresentação e o modelo de domínio.
 */
public record CustomerRequestDTO(
    /**
     * O endereço de email do cliente. Deve ser único e não pode ser vazio.
     * Validado para ser um formato de email válido.
     */
    @NotBlank(message = "O email é obrigatório")
    @Email(message = "Email inválido")
    String email,

    /**
     * A senha do cliente. Não pode ser vazia.
     */
    @NotBlank(message = "A senha é obrigatória")
    String password,

    /**
     * O nome completo do cliente. Não pode ser vazio.
     */
    @NotBlank(message = "O nome é obrigatório")
    String name,

    /**
     * O CEP (Código de Endereçamento Postal) do cliente. Não pode ser vazio.
     * Validado para conter exatamente 8 dígitos numéricos.
     */
    @NotBlank(message = "O CEP é obrigatório")
    @Pattern(regexp = "\\d{8}", message = "CEP inválido")
    String cep,

    /**
     * O CPF (Cadastro de Pessoas Físicas) do cliente. Não pode ser nulo.
     */
    @NotNull(message = "O CPF é obrigatório")
    BigInteger cpf,

    /**
     * A data de nascimento do cliente. Não pode ser nula.
     * Validada para ser uma data no passado.
     */
    @NotNull(message = "A data de nascimento é obrigatória")
    @Past(message = "A data de nascimento deve ser no passado")
    Date birth,

    /**
     * O número de contato do cliente. Campo opcional.
     */
    BigInteger contactNumber
) {}
