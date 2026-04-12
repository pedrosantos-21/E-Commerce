package com.example.ecommerce.service;

import com.example.ecommerce.dto.ProductRequestDTO;
import com.example.ecommerce.dto.ProductResponseDTO;
import com.example.ecommerce.mapper.ProductMapper;
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
 * Gerencia o catálogo de produtos, estoque e precificação.
 */
@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMapper productMapper;

    /**
     * Cadastra um novo produto no sistema a partir de um DTO.
     * 
     * @param productRequestDTO Dados do produto para criação.
     * @return O produto salvo convertido para DTO de resposta.
     */
    @Transactional
    public ProductResponseDTO createProduct(ProductRequestDTO productRequestDTO) {
        Product product = productMapper.toProduct(productRequestDTO);
        Product savedProduct = productRepository.save(product);
        return productMapper.toProductResponseDTO(savedProduct);
    }

    /**
     * Lista todos os produtos disponíveis no catálogo.
     * 
     * @return Lista contendo todos os produtos cadastrados.
     */
    public List<ProductResponseDTO> getAllProducts() {
        return productRepository.findAll().stream()
                .map(productMapper::toProductResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Busca um produto específico pelo seu ID (UUID).
     * 
     * @param id O identificador único do produto.
     * @return Um Optional contendo o produto encontrado.
     */
    public Optional<ProductResponseDTO> getProductById(UUID id) {
        return productRepository.findById(id)
                .map(productMapper::toProductResponseDTO);
    }

    /**
     * Atualiza as informações de um produto existente.
     * 
     * @param id O ID do produto a ser editado.
     * @param productRequestDTO Novos dados para o produto.
     * @return Um Optional com o produto atualizado.
     */
    @Transactional
    public Optional<ProductResponseDTO> updateProduct(UUID id, ProductRequestDTO productRequestDTO) {
        return productRepository.findById(id)
                .map(existingProduct -> {
                    productMapper.updateProductFromDto(productRequestDTO, existingProduct);
                    return productMapper.toProductResponseDTO(productRepository.save(existingProduct));
                });
    }

    /**
     * Remove permanentemente um produto do catálogo pelo seu ID.
     * 
     * @param id O ID do produto para exclusão.
     * @return true se o produto foi deletado, false caso não exista.
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
