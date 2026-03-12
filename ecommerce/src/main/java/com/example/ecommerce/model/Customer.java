package com.example.ecommerce.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(unique = true, nullable = false)
    private UUID id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, length = 8)
    private String cep;

    @Column(unique = true, nullable = false)
    private BigInteger cpf;

    @Column(nullable = false)
    private Date birth;

    @Enumerated(EnumType.STRING)
    @Column
    private Gender gender;

    @Enumerated(EnumType.STRING)
    @Column
    private Sex sex;

    @Column(unique = true, nullable = false)
    private BigInteger contactNumber;

    @ManyToMany
    @JoinTable(
            name = "customer_products",
            joinColumns = @JoinColumn(name = "customer_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    @JsonIgnore
    private List<Product> products;

    public Customer() {}

    public Customer(UUID id, String email, String password, String name, String cep, BigInteger cpf, Date birth, BigInteger contactNumber) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.cep = cep;
        this.cpf = cpf;
        this.birth = birth;
        this.contactNumber = contactNumber;
    }

    // Getters and Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getCep() { return cep; }
    public void setCep(String cep) { this.cep = cep; }
    public BigInteger getCpf() { return cpf; }
    public void setCpf(BigInteger cpf) { this.cpf = cpf; }
    public Date getBirth() { return birth; }
    public void setBirth(Date birth) { this.birth = birth; }
    public Gender getGender() { return gender; }
    public void setGender(Gender gender) { this.gender = gender; }
    public Sex getSex() { return sex; }
    public void setSex(Sex sex) { this.sex = sex; }
    public BigInteger getContactNumber() { return contactNumber; }
    public void setContactNumber(BigInteger contactNumber) { this.contactNumber = contactNumber; }
    public List<Product> getProducts() { return products; }
    public void setProducts(List<Product> products) { this.products = products; }
}
