package com.example.ecommerce.dto;

/**
 * Dados para autenticação de usuários.
 * 
 * @param email E-mail do usuário.
 * @param password Senha de acesso.
 */
public record AuthenticationDTO(String email, String password) {
}
