package com.example.ecommerce.dto;

import com.example.ecommerce.model.Customer;
import com.example.ecommerce.model.Gender;
import com.example.ecommerce.model.Sex;
import java.math.BigInteger;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public record CustomerResponseDTO(
        UUID id,
        String email,
        String name,
        String cep,
        BigInteger cpf,
        Date birth,
        Gender gender,
        Sex sex,
        BigInteger contactNumber,
        List<ProductResponseDTO> products
) {
    public CustomerResponseDTO(Customer customer) {
        this(
                customer.getId(),
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
                        Collections.emptyList()
        );
    }
}
