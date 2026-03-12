package com.example.ecommerce.dto;

import com.example.ecommerce.model.Gender;
import com.example.ecommerce.model.Sex;
import jakarta.validation.constraints.*;
import java.math.BigInteger;
import java.util.Date;

public record CustomerRequestDTO(
        @NotBlank(message = "O email é obrigatório")
        @Email(message = "Email inválido")
        String email,

        @NotBlank(message = "A senha é obrigatória")
        String password,

        @NotBlank(message = "O nome é obrigatório")
        String name,

        @NotBlank(message = "O CEP é obrigatório")
        @Pattern(regexp = "\\d{8}", message = "CEP inválido")
        String cep,

        @NotNull(message = "O CPF é obrigatório")
        BigInteger cpf,

        @NotNull(message = "A data de nascimento é obrigatória")
        @Past(message = "A data de nascimento deve ser no passado")
        Date birth,

        Gender gender,

        Sex sex,

        BigInteger contactNumber
) {}
