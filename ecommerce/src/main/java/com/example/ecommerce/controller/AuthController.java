package com.example.ecommerce.controller;

import com.example.ecommerce.dto.AuthenticationDTO;
import com.example.ecommerce.dto.CustomerRequestDTO;
import com.example.ecommerce.dto.LoginResponseDTO;
import com.example.ecommerce.model.Customer;
import com.example.ecommerce.repository.CustomerRepository;
import com.example.ecommerce.security.TokenService;
import com.example.ecommerce.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador responsável pelos processos de autenticação e registro de usuários.
 * Provê endpoints para login e criação de novas contas com segurança JWT.
 */
@RestController
@RequestMapping("auth")
@Tag(name = "Authentication", description = "Endpoints para autenticação e registro de usuários")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomerRepository repository;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private CustomerService customerService;

    /**
     * Realiza a autenticação do usuário.
     * Valida as credenciais e retorna um token JWT se forem válidas.
     * 
     * @param data DTO contendo e-mail e senha.
     * @return ResponseEntity contendo o token de acesso.
     */
    @PostMapping("/login")
    @Operation(summary = "Realiza o login e retorna um token JWT")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((Customer) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    /**
     * Registra um novo cliente no sistema.
     * Valida a duplicidade de e-mail e delega a criação para o serviço de clientes.
     * 
     * @param data DTO com os dados do novo cliente.
     * @return ResponseEntity com status 200 (OK) ou 400 (Bad Request).
     */
    @PostMapping("/register")
    @Operation(summary = "Registra um novo cliente com senha criptografada")
    public ResponseEntity register(@RequestBody @Valid CustomerRequestDTO data) {
        if (this.repository.findByEmail(data.email()).isPresent()) {
            return ResponseEntity.badRequest().build();
        }

        customerService.createCustomer(data);

        return ResponseEntity.ok().build();
    }
}
