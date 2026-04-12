package com.example.ecommerce.controller;

import com.example.ecommerce.dto.ProductRequestDTO;
import com.example.ecommerce.dto.ProductResponseDTO;
import com.example.ecommerce.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Produtos", description = "Endpoints para gerenciamento de produtos no e-commerce")
public class ProductController {

    @Autowired
    private ProductService productService;

    /**
     * Cria um novo produto no catálogo.
     * 
     * @param productRequestDTO Dados do produto para cadastro.
     * @return ResponseEntity com o produto criado e status 201 (Created).
     */
    @Operation(summary = "Criar um novo produto", description = "Cria um produto com as informações fornecidas no corpo da requisição")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Produto criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos")
    })
    @PostMapping
    public ResponseEntity<ProductResponseDTO> createProduct(@RequestBody @Valid ProductRequestDTO productRequestDTO) {
        ProductResponseDTO newProduct = productService.createProduct(productRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(newProduct);
    }

    /**
     * Lista todos os produtos cadastrados no sistema.
     * 
     * @return Lista de produtos.
     */
    @Operation(summary = "Listar todos os produtos", description = "Retorna uma lista de todos os produtos cadastrados no sistema")
    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> getAllProducts() {
        List<ProductResponseDTO> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    /**
     * Busca um produto pelo seu ID único.
     * 
     * @param id UUID do produto.
     * @return O produto encontrado ou 404 (Not Found).
     */
    @Operation(summary = "Buscar produto por ID", description = "Retorna os detalhes de um cliente específico baseado no seu UUID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto encontrado"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> getProductById(@PathVariable UUID id) {
        return productService.getProductById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Atualiza os dados de um produto existente.
     * 
     * @param id UUID do produto.
     * @param productRequestDTO Novos dados para atualização.
     * @return O produto atualizado ou 404.
     */
    @Operation(summary = "Atualizar um produto", description = "Atualiza os dados de um cliente existente baseado no ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado para atualização")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> updateProduct(@PathVariable UUID id, @RequestBody @Valid ProductRequestDTO productRequestDTO) {
        return productService.updateProduct(id, productRequestDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Remove um produto do sistema.
     * 
     * @param id UUID do produto.
     * @return Status 204 (No Content) em caso de sucesso.
     */
    @Operation(summary = "Deletar um produto", description = "Remove permanentemente um cliente do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Produto removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado para exclusão")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable UUID id) {
        if (productService.deleteProduct(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
