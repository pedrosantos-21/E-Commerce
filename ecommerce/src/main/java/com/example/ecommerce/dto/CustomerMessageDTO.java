package com.example.ecommerce.dto;

import java.io.Serializable;

public record CustomerMessageDTO(String name, String email) implements Serializable {
}
