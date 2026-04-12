package com.example.ecommerce.mapper;

import com.example.ecommerce.dto.ProductRequestDTO;
import com.example.ecommerce.dto.ProductResponseDTO;
import com.example.ecommerce.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    Product toProduct(ProductRequestDTO productRequestDTO);

    ProductResponseDTO toProductResponseDTO(Product product);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "customers", ignore = true)
    void updateProductFromDto(ProductRequestDTO productRequestDTO, @MappingTarget Product product);
}
