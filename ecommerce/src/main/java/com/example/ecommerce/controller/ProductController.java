package com.example.ecommerce.controller;

import com.example.ecommerce.dto.ProductRequestDTO;
import com.example.ecommerce.dto.ProductResponseDTO;
import com.example.ecommerce.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * Controlador REST para gerenciar as operações relacionadas a produtos.
 * Expõe endpoints para criar, listar, buscar, atualizar e deletar produtos.
 */
@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    /**
     * Cria um novo produto no sistema.
     * @param productRequestDTO DTO contendo os dados do produto a ser criado.
     * @return ResponseEntity com o {@link ProductResponseDTO} do produto criado e status HTTP 201 (Created).
     */
    @PostMapping
    public ResponseEntity<ProductResponseDTO> createProduct(@RequestBody @Valid ProductRequestDTO productRequestDTO) {
        ProductResponseDTO newProduct = productService.createProduct(productRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(newProduct);
    }

    /**
     * Retorna uma lista de todos os produtos cadastrados.
     * @return ResponseEntity com uma {@link List} de {@link ProductResponseDTO} e status HTTP 200 (OK).
     */
    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> getAllProducts() {
        List<ProductResponseDTO> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    /**
     * Busca um produto pelo seu ID único.
     * @param id O ID (UUID) do produto a ser buscado.
     * @return ResponseEntity com o {@link ProductResponseDTO} do produto e status HTTP 200 (OK), ou 404 (Not Found) se não encontrado.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> getProductById(@PathVariable UUID id) {
        return productService.getProductById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Atualiza os dados de um produto existente.
     * @param id O ID (UUID) do produto a ser atualizado.
     * @param productRequestDTO DTO contendo os novos dados do produto.
     * @return ResponseEntity com o {@link ProductResponseDTO} do produto atualizado e status HTTP 200 (OK), ou 404 (Not Found) se não encontrado.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> updateProduct(@PathVariable UUID id, @RequestBody @Valid ProductRequestDTO productRequestDTO) {
        return productService.updateProduct(id, productRequestDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Exclui um produto pelo seu ID único.
     * @param id O ID (UUID) do produto a ser excluído.
     * @return ResponseEntity com status HTTP 204 (No Content) se excluído com sucesso, ou 404 (Not Found) se não encontrado.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable UUID id) {
        if (productService.deleteProduct(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
