package com.example.ecommerce.service;

import com.example.ecommerce.dto.CustomerRequestDTO;
import com.example.ecommerce.dto.CustomerResponseDTO;
import com.example.ecommerce.mapper.CustomerMapper;
import com.example.ecommerce.model.Customer;
import com.example.ecommerce.repository.CustomerRepository;
import com.example.ecommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Serviço responsável pela lógica de negócio relacionada aos clientes.
 * Gerencia as operações de CRUD (Create, Read, Update, Delete) para a entidade {@link Customer},
 * utilizando o {@link CustomerRepository} para interagir com o banco de dados.
 */
@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;
    
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private CustomerProducer customerProducer;

    /**
     * Cria um novo cliente a partir dos dados fornecidos no DTO de requisição.
     * @param customerRequestDTO DTO contendo os dados para criação do cliente.
     * @return Um {@link CustomerResponseDTO} com os dados do cliente recém-criado.
     */
    @Transactional
    public CustomerResponseDTO createCustomer(CustomerRequestDTO customerRequestDTO) {
        Customer customer = customerMapper.toCustomer(customerRequestDTO);
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        Customer savedCustomer = customerRepository.save(customer);
        
        // Envia mensagem assíncrona para o RabbitMQ (Centralizado aqui)
        customerProducer.sendWelcomeMessage(savedCustomer);
        
        return customerMapper.toCustomerResponseDTO(savedCustomer);
    }

    /**
     * Retorna uma lista de todos os clientes cadastrados.
     * @return Uma {@link List} de {@link CustomerResponseDTO} contendo todos os clientes.
     */
    public List<CustomerResponseDTO> getAllCustomers() {
        return customerRepository.findAll().stream()
                .map(customerMapper::toCustomerResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Busca um cliente pelo seu ID único.
     * @param id O ID (UUID) do cliente a ser buscado.
     * @return Um {@link Optional} contendo o {@link CustomerResponseDTO} do cliente, se encontrado, ou vazio caso contrário.
     */
    public Optional<CustomerResponseDTO> getCustomerById(UUID id) {
        return customerRepository.findById(id)
                .map(customerMapper::toCustomerResponseDTO);
    }

    /**
     * Atualiza os dados de um cliente existente.
     * @param id O ID (UUID) do cliente a ser atualizado.
     * @param customerRequestDTO DTO contendo os novos dados do cliente.
     * @return Um {@link Optional} contendo o {@link CustomerResponseDTO} do cliente atualizado, se encontrado, ou vazio caso contrário.
     */
    @Transactional
    public Optional<CustomerResponseDTO> updateCustomer(UUID id, CustomerRequestDTO customerRequestDTO) {
        return customerRepository.findById(id)
                .map(existingCustomer -> {
                    // O MapStruct atualiza os campos do existingCustomer com os dados do DTO
                    customerMapper.updateCustomerFromDto(customerRequestDTO, existingCustomer);

                    // Criptografa a nova senha antes de salvar
                    if (customerRequestDTO.password() != null && !customerRequestDTO.password().isEmpty()) {
                        existingCustomer.setPassword(passwordEncoder.encode(customerRequestDTO.password()));
                    }

                    // Salva a entidade atualizada e converte para o DTO de resposta
                    Customer savedCustomer = customerRepository.save(existingCustomer);
                    return customerMapper.toCustomerResponseDTO(savedCustomer);
                });
    }


    /**
     * Exclui um cliente pelo seu ID único.
     * @param id O ID (UUID) do cliente a ser excluído.
     * @return {@code true} se o cliente foi excluído com sucesso, {@code false} caso contrário.
     */
    @Transactional
    public boolean deleteCustomer(UUID id) {
        return customerRepository.findById(id)
                .map(customer -> {
                    customerRepository.delete(customer);
                    return true;
                })
                .orElse(false);
    }

    /**
     * Busca um cliente pelo seu endereço de email.
     * @param email O endereço de email do cliente.
     * @return Um {@link Optional} contendo o {@link CustomerResponseDTO} do cliente, se encontrado, ou vazio caso contrário.
     */
    public Optional<CustomerResponseDTO> getCustomerByEmail(String email) {
        return customerRepository.findByEmail(email)
                .map(customerMapper::toCustomerResponseDTO);
    }

    /**
     * Associa um produto a um cliente.
     * @param customerId O ID do cliente.
     * @param productId O ID do produto.
     * @return Um Optional com o CustomerResponseDTO atualizado.
     */
    @Transactional
    public Optional<CustomerResponseDTO> addProductToCustomer(UUID customerId, UUID productId) {
        return customerRepository.findById(customerId).flatMap(customer -> 
            productRepository.findById(productId).map(product -> {
                customer.getProducts().add(product);
                Customer updatedCustomer = customerRepository.save(customer);
                return customerMapper.toCustomerResponseDTO(updatedCustomer);
            })
        );
    }
}
