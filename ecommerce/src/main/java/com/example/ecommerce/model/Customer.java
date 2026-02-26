package com.example.ecommerce.model;

import jakarta.persistence.*;
import org.hibernate.validator.constraints.UUID;

import java.math.BigInteger;
import java.text.Normalizer;
import java.util.Date;

/*
Entity -> Com essa Class nós conseguimos criar as Entidades(Tablas do banco)
@Id -> Id da tabela
@GeneratedValue -> Auto increment do banco
UUID  -> Criptografia do dado.
*/


@Entity
public class Customer {
    //Dados de login
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(unique = true, nullable = false)
    private UUID id;

    @Column (unique = true, nullable = false)
    private String email;

    @Column (nullable = false)
    private String password;

    // Dados pessoais
    @Column (nullable = false)
    private String name;

    @Column (nullable = false, length = 8)
    private String cep;

    @Column (unique = true, nullable = false)
    private BigInteger cpf;

    @Column (nullable = false)
    private Date birth;

    @Column
    private Normalizer.Form gender;
    //Nesta sessão vamos criar o formulario.

    @Column
    private Normalizer.Form sex;
    //Dados de Contato
    @Column (unique = true, nullable = false)
    private BigInteger contactNumber;


    public Customer(){

    }

    public Customer(UUID id,
                    String email,
                    String password,
                    String cep,
                    BigInteger cpf,
                    Date birth){

                    this.id = id;
                    this.email = email;
                    this.password = password;
                    this.cep = cep;
                    this.cpf = cpf;
                    this.birth = birth;

    }
    // Tambem utilizamos a sobrecarga de customer, como uma forma de tratamento, prevenção, das variações de objetos
    // Serve para salvar os dados em uma tabela de diferentes formas, como por exemplo na etapa de desenvolvimento, passar apenas os campos de nome e email.
    // 3️⃣ Cadastro sem dados opcionais (sexo/gênero)
    public Customer(String email,
                    String password,
                    String name,
                    String cep,
                    BigInteger cpf,
                    Date birth,
                    BigInteger contactNumber) {

        this.email = email;
        this.password = password;
        this.name = name;
        this.cep = cep;
        this.cpf = cpf;
        this.birth = birth;
        this.contactNumber = contactNumber;
    }

    // 4️⃣ Uso interno / testes (com ID)
    public Customer(UUID id,
                    String email,
                    String password,
                    String name,
                    String cep,
                    BigInteger cpf,
                    Date birth,
                    BigInteger contactNumber) {

        this(email, password, name, cep, cpf, birth, contactNumber);
        this.id = id;
    }

    public UUID getId(){
        return id;
    }

    public BigInteger getContactNumber() {
        return contactNumber;
    }

    public BigInteger getCpf() {
        return cpf;
    }

    public Date getBirth() {
        return birth;
    }

    public Normalizer.Form getGender() {
        return gender;
    }

    public String getCep() {
        return cep;
    }

    public Normalizer.Form getSex() {
        return sex;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public void setCpf(BigInteger cpf) {
        this.cpf = cpf;
    }

    public void setGender(Normalizer.Form gender) {
        this.gender = gender;
    }

    public void setSex(Normalizer.Form sex) {
        this.sex = sex;
    }

    public void setContactNumber(BigInteger contactNumber) {
        this.contactNumber = contactNumber;
    }

}


