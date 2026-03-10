package com.example.ecommerce.controller;

import com.example.ecommerce.dto.CustomerRequestDTO;
import com.example.ecommerce.dto.CustomerResponseDTO;
import com.example.ecommerce.service.CustomerService;
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
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    /**
     * Cria um novo cliente no sistema.
     *
     * @param customerRequestDTO DTO contendo os dados do cliente a ser criado.
     * @return ResponseEntity com o {@link CustomerResponseDTO} do cliente criado e status HTTP 201 (Created).
     */
    @PostMapping
    public ResponseEntity<CustomerResponseDTO> createCustomer(@RequestBody @Valid CustomerRequestDTO customerRequestDTO) {
        CustomerResponseDTO newCustomer = customerService.createCustomer(customerRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(newCustomer);
    }

    /**
     * Retorna uma lista de todos os clientes cadastrados.
     *
     * @return ResponseEntity com uma {@link List} de {@link CustomerResponseDTO} e status HTTP 200 (OK).
     */
    @GetMapping
    public ResponseEntity<List<CustomerResponseDTO>> getAllCustomers() {
        List<CustomerResponseDTO> customers = customerService.getAllCustomers();
        return ResponseEntity.ok(customers);
    }

    /**
     * Busca um cliente pelo seu ID único.
     * @param id O ID (UUID) do cliente a ser buscado.
     * @return ResponseEntity com o {@link CustomerResponseDTO} do cliente e status HTTP 200 (OK), ou 404 (Not Found) se não encontrado.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponseDTO> getCustomerById(@PathVariable UUID id) {
        return customerService.getCustomerById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Atualiza os dados de um cliente existente.
     * @param id O ID (UUID) do cliente a ser atualizado.
     * @param customerRequestDTO DTO contendo os novos dados do cliente.
     * @return ResponseEntity com o {@link CustomerResponseDTO} do cliente atualizado e status HTTP 200 (OK), ou 404 (Not Found) se não encontrado.
     */
    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponseDTO> updateCustomer(@PathVariable UUID id, @RequestBody @Valid CustomerRequestDTO customerRequestDTO) {
        return customerService.updateCustomer(id, customerRequestDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Exclui um cliente pelo seu ID único.
     * @param id O ID (UUID) do cliente a ser excluído.
     * @return ResponseEntity com status HTTP 204 (No Content) se excluído com sucesso, ou 404 (Not Found) se não encontrado.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable UUID id) {
        if (customerService.deleteCustomer(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Adiciona um produto à lista de desejos/compras do cliente.
     * @param customerId O ID do cliente.
     * @param productId O ID do produto.
     * @return ResponseEntity com o CustomerResponseDTO atualizado.
     */
    @PostMapping("/{customerId}/products/{productId}")
    public ResponseEntity<CustomerResponseDTO> addProductToCustomer(
            @PathVariable UUID customerId, 
            @PathVariable UUID productId) {
        return customerService.addProductToCustomer(customerId, productId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
