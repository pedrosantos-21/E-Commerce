package com.example.ecommerce.controller;

import com.example.ecommerce.dto.CustomerRequestDTO;
import com.example.ecommerce.dto.CustomerResponseDTO;
import com.example.ecommerce.service.CustomerService;
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
 * Controlador REST para gerenciar as operações relacionadas a clientes.
 * Expõe endpoints para criar, listar, buscar, atualizar e deletar clientes.
 */
@RestController
@RequestMapping("/customers")
@Tag(name = "Clientes", description = "Endpoints para gerenciamento de clientes no e-commerce")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Operation(summary = "Criar um novo cliente", description = "Cadastra um cliente com os dados fornecidos no corpo da requisição")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cliente criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos (ex: CPF ou Email inválido)")
    })
    @PostMapping
    public ResponseEntity<CustomerResponseDTO> createCustomer(@RequestBody @Valid CustomerRequestDTO customerRequestDTO) {
        CustomerResponseDTO newCustomer = customerService.createCustomer(customerRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(newCustomer);
    }

    @Operation(summary = "Listar todos os clientes", description = "Retorna uma lista de todos os clientes cadastrados no sistema")
    @GetMapping
    public ResponseEntity<List<CustomerResponseDTO>> getAllCustomers() {
        List<CustomerResponseDTO> customers = customerService.getAllCustomers();
        return ResponseEntity.ok(customers);
    }

    @Operation(summary = "Buscar cliente por ID", description = "Retorna os detalhes de um cliente específico baseado no seu UUID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente encontrado"),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponseDTO> getCustomerById(@PathVariable UUID id) {
        return customerService.getCustomerById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Atualizar um cliente", description = "Atualiza os dados de um cliente existente baseado no ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado para atualização")
    })
    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponseDTO> updateCustomer(@PathVariable UUID id, @RequestBody @Valid CustomerRequestDTO customerRequestDTO) {
        return customerService.updateCustomer(id, customerRequestDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Deletar um cliente", description = "Remove permanentemente um cliente do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Cliente removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado para exclusão")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable UUID id) {
        if (customerService.deleteCustomer(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Adicionar produto ao cliente", description = "Vincula um produto específico a um cliente (ex: carrinho de compras ou lista de desejos)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto adicionado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Cliente ou Produto não encontrado")
    })
    @PostMapping("/{customerId}/products/{productId}")
    public ResponseEntity<CustomerResponseDTO> addProductToCustomer(
            @PathVariable UUID customerId,
            @PathVariable UUID productId) {
        return customerService.addProductToCustomer(customerId, productId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
