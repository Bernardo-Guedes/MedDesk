package com.example.MedDesk.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Paciente {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String nome;
    @Column(nullable = false, unique = true)
    private String cpf;
    private LocalDate dataNascimento;
    private String telefone;
    private String endereco;
    private String email;
    @OneToOne(optional = false, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private HistoricoMedico historicoMedico = new HistoricoMedico();

    protected Paciente() {}

    public Paciente(String nome, String cpf, LocalDate dataNascimento, String telefone, String endereco, String email) {
        this.nome = nome;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
        this.telefone = telefone;
        this.endereco = endereco;
        this.email = email;
    }

    public String exibirInformacoes() { return nome; }
    public Long getId() { return id; }
    public String getNome() { return nome; }
    public String getCpf() { return cpf; }
    public LocalDate getDataNascimento() { return dataNascimento; }
    public String getTelefone() { return telefone; }
    public String getEndereco() { return endereco; }
    public String getEmail() { return email; }
    public HistoricoMedico getHistoricoMedico() { return historicoMedico; }
}
