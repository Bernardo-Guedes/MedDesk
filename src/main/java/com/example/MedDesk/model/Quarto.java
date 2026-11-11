package com.example.MedDesk.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Quarto {
    @Id
    private String numeroIdentificacao;
    private int andar;
    private int capacidadeMaximaPacientes;
    private boolean disponivel;
    @ManyToMany
    @JoinTable(inverseJoinColumns = @JoinColumn(name = "paciente_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = "paciente_id"))
    private List<Paciente> pacientes = new ArrayList<>();
    @Column(nullable = false)
    private String tipo;

    protected Quarto() {}

    public Quarto(String numeroIdentificacao, int andar, int capacidadeMaximaPacientes, String tipo) {
        if (capacidadeMaximaPacientes < 1 || tipo == null || tipo.isBlank()) {
            throw new IllegalArgumentException("O quarto exige capacidade positiva e tipo.");
        }
        this.numeroIdentificacao = numeroIdentificacao;
        this.andar = andar;
        this.capacidadeMaximaPacientes = capacidadeMaximaPacientes;
        this.disponivel = true;
        this.tipo = tipo;
    }

    public boolean verificarDisponibilidade() {
        return disponivel;
    }

    public void adicionarPaciente(Paciente paciente) {
        for (Paciente ocupante : pacientes) {
            if (ocupante == paciente || (paciente.getId() != null && paciente.getId().equals(ocupante.getId()))) {
                throw new IllegalStateException("O paciente já ocupa este quarto.");
            }
        }
        if (!verificarDisponibilidade()) {
            throw new IllegalStateException("O quarto está lotado.");
        }
        pacientes.add(paciente);
        atualizarDisponibilidade();
    }

    public void removerPaciente(Long id) {
        for (int indice = 0; indice < pacientes.size(); indice++) {
            Paciente paciente = pacientes.get(indice);
            if (id != null && id.equals(paciente.getId())) {
                pacientes.remove(indice);
                atualizarDisponibilidade();
                return;
            }
        }
        throw new IllegalStateException("O paciente não ocupa este quarto.");
    }

    private void atualizarDisponibilidade() {
        disponivel = pacientes.size() < capacidadeMaximaPacientes;
    }

    public String getNumeroIdentificacao() { return numeroIdentificacao; }
    public int getAndar() { return andar; }
    public int getCapacidadeMaximaPacientes() { return capacidadeMaximaPacientes; }
    public ArrayList<Paciente> getPacientes() { return new ArrayList<>(pacientes); }
    public String getTipo() { return tipo; }
}
