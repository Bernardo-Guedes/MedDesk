package com.example.MedDesk.model;

import com.example.MedDesk.exception.RegraNegocioException;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.UUID;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "categoria")
@JsonSubTypes({
        @JsonSubTypes.Type(value = ClinicoGeral.class, name = "CLINICO_GERAL"),
        @JsonSubTypes.Type(value = Especialista.class, name = "ESPECIALISTA"),
        @JsonSubTypes.Type(value = Cirurgiao.class, name = "CIRURGIAO")
})
public abstract class ProfissionalSaude {

    private final UUID id;
    private String nome;
    private String registroProfissional;
    private String telefone;
    private String email;

    protected ProfissionalSaude(String nome, String registroProfissional, String telefone, String email) {
        this.id = UUID.randomUUID();
        setNome(nome);
        setRegistroProfissional(registroProfissional);
        setTelefone(telefone);
        setEmail(email);
    }

    public String exibirInformacoes() {
        return getClass().getSimpleName() + ": " + nome + " | Registro: " + registroProfissional
                + " | Telefone: " + telefone + " | E-mail: " + email;
    }


    public UUID getId() { return id; }
    public String getNome() { return nome; }
    public String getRegistroProfissional() { return registroProfissional; }
    public String getTelefone() { return telefone; }
    public String getEmail() { return email; }


    public void setNome(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new RegraNegocioException("O nome do profissional é obrigatório");
        }
        this.nome = nome.trim();
    }

    public void setRegistroProfissional(String registroProfissional) {
        if (registroProfissional == null || registroProfissional.isBlank()) {
            throw new RegraNegocioException("O registro profissional é obrigatório");
        }
        this.registroProfissional = registroProfissional.trim();
    }

    public void setTelefone(String telefone) { this.telefone = telefone; }
    public void setEmail(String email) { this.email = email; }
}
