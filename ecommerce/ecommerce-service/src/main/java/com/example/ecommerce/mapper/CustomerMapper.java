package com.example.ecommerce.mapper;

import com.example.ecommerce.dto.CustomerRequestDTO;
import com.example.ecommerce.dto.CustomerResponseDTO;
import com.example.ecommerce.model.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    @Mapping(target = "id", ignore = true) // O ID é gerado automaticamente, não vem do DTO de requisição
    @Mapping(target = "gender", ignore = true) // Ignorar gender e sex, pois não estão no DTO de requisição
    @Mapping(target = "sex", ignore = true)
    @Mapping(target = "products", ignore = true)
    @Mapping(target = "authorities", ignore = true)
    Customer toCustomer(CustomerRequestDTO customerRequestDTO);

    CustomerResponseDTO toCustomerResponseDTO(Customer customer);

// Método para atualizar uma entidade Customer existente a partir de um DTO
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "gender", ignore = true)
    @Mapping(target = "sex", ignore = true)
    @Mapping(target = "products", ignore = true)
    @Mapping(target = "authorities", ignore = true)
    void updateCustomerFromDto(CustomerRequestDTO customerRequestDTO, @MappingTarget Customer customer);
}
