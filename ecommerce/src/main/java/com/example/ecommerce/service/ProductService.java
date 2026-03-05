package com.example.ecommerce.service;

import com.example.ecommerce.dto.ProductRequestDTO;
import com.example.ecommerce.dto.ProductResponseDTO;
import com.example.ecommerce.model.Product;
import com.example.ecommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Serviço responsável pela lógica de negócio relacionada aos produtos.
 * Gerencia as operações de CRUD (Create, Read, Update, Delete) para a entidade {@link Product},
 * utilizando o {@link ProductRepository} para interagir com o banco de dados.
 */
@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    /**
     * Cria um novo produto a partir dos dados fornecidos no DTO de requisição.
     * @param productRequestDTO DTO contendo os dados para criação do produto.
     * @return Um {@link ProductResponseDTO} com os dados do produto recém-criado.
     */
    @Transactional
    public ProductResponseDTO createProduct(ProductRequestDTO productRequestDTO) {
        Product product = new Product(
                productRequestDTO.name(),
                productRequestDTO.description(),
                productRequestDTO.price(),
                productRequestDTO.brand(),
                productRequestDTO.stock()
        );
        Product savedProduct = productRepository.save(product);
        return new ProductResponseDTO(savedProduct);
    }

    /**
     * Retorna uma lista de todos os produtos cadastrados.
     * @return Uma {@link List} de {@link ProductResponseDTO} contendo todos os produtos.
     */
    public List<ProductResponseDTO> getAllProducts() {
        return productRepository.findAll().stream()
                .map(ProductResponseDTO::new)
                .collect(Collectors.toList());
    }

    /**
     * Busca um produto pelo seu ID único.
     * @param id O ID (UUID) do produto a ser buscado.
     * @return Um {@link Optional} contendo o {@link ProductResponseDTO} do produto, se encontrado, ou vazio caso contrário.
     */
    public Optional<ProductResponseDTO> getProductById(UUID id) {
        return productRepository.findById(id)
                .map(ProductResponseDTO::new);
    }

    /**
     * Atualiza os dados de um produto existente.
     * @param id O ID (UUID) do produto a ser atualizado.
     * @param productRequestDTO DTO contendo os novos dados do produto.
     * @return Um {@link Optional} contendo o {@link ProductResponseDTO} do produto atualizado, se encontrado, ou vazio caso contrário.
     */
    @Transactional
    public Optional<ProductResponseDTO> updateProduct(UUID id, ProductRequestDTO productRequestDTO) {
        return productRepository.findById(id)
                .map(existingProduct -> {
                    existingProduct.setName(productRequestDTO.name());
                    existingProduct.setDescription(productRequestDTO.description());
                    existingProduct.setPrice(productRequestDTO.price());
                    existingProduct.setBrand(productRequestDTO.brand());
                    existingProduct.setStock(productRequestDTO.stock());
                    return new ProductResponseDTO(productRepository.save(existingProduct));
                });
    }

    /**
     * Exclui um produto pelo seu ID único.
     * @param id O ID (UUID) do produto a ser excluído.
     * @return {@code true} se o produto foi excluído com sucesso, {@code false} caso contrário.
     */
    @Transactional
    public boolean deleteProduct(UUID id) {
        return productRepository.findById(id)
                .map(product -> {
                    productRepository.delete(product);
                    return true;
                })
                .orElse(false);
    }
}
