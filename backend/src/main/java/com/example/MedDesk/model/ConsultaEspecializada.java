package com.example.MedDesk.model;

import jakarta.persistence.Entity;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
public class ConsultaEspecializada extends Atendimento implements Agendavel {
    private String especialidade;

    protected ConsultaEspecializada() {}

    public ConsultaEspecializada(Paciente paciente, ProfissionalSaude profissional, LocalDate data,
                                 LocalTime horario, String especialidade) {
        super(paciente, profissional, data, horario, null);
        this.especialidade = especialidade;
        agendar();
    }

    @Override
    public void agendar() {
        agendarAtendimento();
    }

    @Override
    protected void validarCondicoes() {
        if (especialidade == null || especialidade.isBlank()) {
            throw new IllegalArgumentException("A especialidade da consulta deve ser informada.");
        }
        ProfissionalSaude profissional = getProfissionalResponsavel();
        if (!(profissional instanceof Especialista) || !((Especialista) profissional).atuaEm(especialidade)) {
            throw new IllegalArgumentException("O profissional deve atuar na especialidade solicitada.");
        }
    }

    public String getEspecialidade() { return especialidade; }
}
