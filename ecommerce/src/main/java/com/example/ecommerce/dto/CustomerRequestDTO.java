package com.example.ecommerce.dto;

import com.example.ecommerce.model.Gender;
import com.example.ecommerce.model.Sex;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import java.math.BigInteger;
import java.util.Date;

@Schema(description = "Dados para criação ou atualização de um cliente")
public record CustomerRequestDTO(
        @NotBlank(message = "O email é obrigatório")
        @Email(message = "Email inválido")
        @Schema(description = "Email do cliente", example = "joao.silva@email.com")
        String email,

        @NotBlank(message = "A senha é obrigatória")
        @Schema(description = "Senha de acesso", example = "senha123")
        String password,

        @NotBlank(message = "O nome é obrigatório")
        @Schema(description = "Nome completo do cliente", example = "João da Silva")
        String name,

        @NotBlank(message = "O CEP é obrigatório")
        @Pattern(regexp = "\\d{8}", message = "CEP inválido")
        @Schema(description = "CEP (apenas números)", example = "74000000")
        String cep,

        @NotNull(message = "O CPF é obrigatório")
        @Schema(description = "CPF (apenas números)", example = "12345678901")
        BigInteger cpf,

        @NotNull(message = "A data de nascimento é obrigatória")
        @Past(message = "A data de nascimento deve ser no passado")
        @Schema(description = "Data de nascimento", example = "1990-05-15")
        Date birth,

        @Schema(description = "Identidade de gênero")
        Gender gender,

        @Schema(description = "Sexo biológico")
        Sex sex,

        @Schema(description = "Número de contato", example = "62988887777")
        BigInteger contactNumber
) {}
