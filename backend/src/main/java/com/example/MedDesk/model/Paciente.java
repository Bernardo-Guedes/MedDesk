package com.example.MedDesk.model;

import com.example.MedDesk.exception.RegraNegocioException;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDate;
import java.util.UUID;

public class Paciente {

    private final UUID id;
    private String nome;
    private String cpf;
    private LocalDate dataNascimento;
    private String telefone;
    private String endereco;
    private String email;
    private final HistoricoMedico historicoMedico;

    public Paciente(String nome, String cpf, LocalDate dataNascimento,
                    String telefone, String endereco, String email) {
        this.id = UUID.randomUUID();
        this.historicoMedico = new HistoricoMedico();
        setNome(nome);
        setCpf(cpf);
        setDataNascimento(dataNascimento);
        setTelefone(telefone);
        setEndereco(endereco);
        setEmail(email);
    }

    public String exibirInformacoes() {
        return "Paciente: " + nome + " | CPF: " + cpf + " | Nascimento: " + dataNascimento
                + " | Telefone: " + telefone + " | E-mail: " + email + " | Endereço: " + endereco;
    }


    public UUID getId() { return id; }
    public String getNome() { return nome; }
    public String getCpf() { return cpf; }
    public LocalDate getDataNascimento() { return dataNascimento; }
    public String getTelefone() { return telefone; }
    public String getEndereco() { return endereco; }
    public String getEmail() { return email; }

    @JsonIgnore
    public HistoricoMedico getHistoricoMedico() { return historicoMedico; }


    public void setNome(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new RegraNegocioException("O nome do paciente é obrigatório");
        }
        this.nome = nome.trim();
    }

    public void setCpf(String cpf) {
        String somenteDigitos = cpf == null ? "" : cpf.replaceAll("\\D", "");
        if (somenteDigitos.length() != 11) {
            throw new RegraNegocioException("O CPF deve conter 11 dígitos");
        }
        this.cpf = somenteDigitos;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        if (dataNascimento == null || dataNascimento.isAfter(LocalDate.now())) {
            throw new RegraNegocioException("A data de nascimento é obrigatória e não pode ser futura");
        }
        this.dataNascimento = dataNascimento;
    }

    public void setTelefone(String telefone) { this.telefone = telefone; }
    public void setEndereco(String endereco) { this.endereco = endereco; }
    public void setEmail(String email) { this.email = email; }
}
