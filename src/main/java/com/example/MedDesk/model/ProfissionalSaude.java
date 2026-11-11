package com.example.MedDesk.model;

import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class ProfissionalSaude {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String nome;
    @Column(nullable = false, unique = true)
    private String registro;
    private String telefone;
    private String email;

    protected ProfissionalSaude() {}

    protected ProfissionalSaude(String nome, String registro, String telefone, String email) {
        this.nome = nome;
        this.registro = registro;
        this.telefone = telefone;
        this.email = email;
    }

    protected void validarResponsavel(Atendimento atendimento) {
        if (!this.equals(atendimento.getProfissionalResponsavel())) {
            throw new IllegalArgumentException("O atendimento pertence a outro profissional.");
        }
    }

    public String exibirInformacoes() { return nome + " (" + registro + ")"; }
    public Long getId() { return id; }
    public String getNome() { return nome; }
    public String getRegistro() { return registro; }
    public String getTelefone() { return telefone; }
    public String getEmail() { return email; }
}
