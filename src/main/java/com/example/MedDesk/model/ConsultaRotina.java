package com.example.MedDesk.model;

import jakarta.persistence.Entity;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
public class ConsultaRotina extends Atendimento implements Agendavel {
    private String motivo;

    protected ConsultaRotina() {}

    public ConsultaRotina(Paciente paciente, ProfissionalSaude profissional, LocalDate data, LocalTime horario,
                          String motivo) {
        super(paciente, profissional, data, horario, null);
        this.motivo = motivo;
        agendar();
    }

    @Override
    public void agendar() {
        agendarAtendimento();
    }

    @Override
    protected void validarCondicoes() {
        if (!(getProfissionalResponsavel() instanceof ClinicoGeral)) {
            throw new IllegalArgumentException("Uma consulta de rotina exige um clínico geral.");
        }
        if (motivo == null || motivo.isBlank()) {
            throw new IllegalArgumentException("O motivo da consulta de rotina deve ser informado.");
        }
    }

    public String getMotivo() { return motivo; }
}
