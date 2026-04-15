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
 * Gerencia as operações de CRUD, criptografia de senhas e integração com o RabbitMQ.
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
     * Realiza a criptografia da senha e dispara a mensagem de boas-vindas via RabbitMQ.
     * 
     * @param customerRequestDTO Objeto contendo os dados do cliente para cadastro.
     * @return O cliente salvo convertido para DTO de resposta.
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
     * Retorna uma lista de todos os clientes cadastrados no banco de dados.
     * 
     * @return Lista de DTOs representando todos os clientes.
     */
    public List<CustomerResponseDTO> getAllCustomers() {
        return customerRepository.findAll().stream()
                .map(customerMapper::toCustomerResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Busca um cliente específico pelo seu ID (UUID).
     * 
     * @param id O identificador único do cliente.
     * @return Um Optional contendo o DTO do cliente, se encontrado.
     */
    public Optional<CustomerResponseDTO> getCustomerById(UUID id) {
        return customerRepository.findById(id)
                .map(customerMapper::toCustomerResponseDTO);
    }

    /**
     * Atualiza os dados de um cliente existente.
     * Se uma nova senha for fornecida, ela será criptografada antes de salvar.
     * 
     * @param id O ID do cliente a ser atualizado.
     * @param customerRequestDTO DTO com os novos dados.
     * @return Um Optional com o cliente atualizado, se encontrado.
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
     * Exclui permanentemente um cliente do sistema pelo seu ID.
     * 
     * @param id O ID do cliente para exclusão.
     * @return true se o cliente foi excluído, false se não foi encontrado.
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
     * Busca um cliente pelo seu endereço de e-mail.
     * Útil para validações de login e duplicidade.
     * 
     * @param email O e-mail do cliente.
     * @return Um Optional com o DTO do cliente, se encontrado.
     */
    public Optional<CustomerResponseDTO> getCustomerByEmail(String email) {
        return customerRepository.findByEmail(email)
                .map(customerMapper::toCustomerResponseDTO);
    }

    /**
     * Associa um produto a um cliente específico (ex: carrinho ou favoritos).
     * 
     * @param customerId O ID do cliente.
     * @param productId O ID do produto a ser associado.
     * @return Um Optional com o cliente atualizado.
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
