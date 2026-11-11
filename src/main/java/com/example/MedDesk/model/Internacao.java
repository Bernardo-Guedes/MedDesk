package com.example.MedDesk.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

@Entity
public class Internacao extends Atendimento implements Agendavel {
    @ManyToOne(fetch = FetchType.LAZY)
    private Quarto quarto;
    private LocalDate dataPrevistaAlta;
    private LocalDate dataEfetivaAlta;

    protected Internacao() {}

    public Internacao(Paciente paciente, ProfissionalSaude profissional, LocalDate data, LocalTime horario,
                      Quarto quarto, LocalDate dataPrevistaAlta) {
        super(paciente, profissional, data, horario, null);
        this.quarto = Objects.requireNonNull(quarto);
        this.dataPrevistaAlta = dataPrevistaAlta;
        agendar();
    }

    @Override
    public void agendar() {
        agendarAtendimento();
    }

    @Override
    protected void validarCondicoes() {
        if (dataPrevistaAlta == null || dataPrevistaAlta.isBefore(getData())) {
            throw new IllegalArgumentException("A previsão de alta deve ser igual ou posterior à entrada.");
        }
        if (!quarto.verificarDisponibilidade()) {
            throw new IllegalStateException("O quarto está lotado.");
        }
    }

    @Override
    protected void antesDeIniciar() {
        quarto.adicionarPaciente(getPaciente());
    }

    @Override
    public void concluir() {
        registrarAlta();
    }

    public void registrarAlta() {
        registrarAlta(LocalDate.now());
    }

    public void registrarAlta(LocalDate dataAlta) {
        exigirSituacao(Situacao.EM_ATENDIMENTO);
        if (dataAlta.isBefore(getData())) {
            throw new IllegalArgumentException("A alta não pode anteceder a entrada.");
        }
        quarto.removerPaciente(getPaciente().getId());
        super.concluir();
        dataEfetivaAlta = dataAlta;
    }

    public Quarto getQuarto() { return quarto; }
    public LocalDate getDataPrevistaAlta() { return dataPrevistaAlta; }
    public LocalDate getDataEfetivaAlta() { return dataEfetivaAlta; }
}
