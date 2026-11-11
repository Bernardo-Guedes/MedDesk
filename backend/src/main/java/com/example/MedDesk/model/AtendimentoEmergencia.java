package com.example.MedDesk.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
public class AtendimentoEmergencia extends Atendimento {
    @Enumerated(EnumType.STRING)
    private Prioridade prioridade;

    protected AtendimentoEmergencia() {}

    public AtendimentoEmergencia(Paciente paciente, ProfissionalSaude profissional, LocalDate data,
                                 LocalTime horario, Prioridade prioridade) {
        super(paciente, profissional, data, horario, Situacao.AGUARDANDO_AVALIACAO);
        alterarPrioridade(prioridade);
    }

    @Override
    protected void validarCondicoes() {
        if (prioridade == null) {
            throw new IllegalArgumentException("A prioridade da emergência deve ser informada.");
        }
    }

    public void alterarPrioridade(Prioridade prioridade) {
        exigirSituacao(Situacao.AGUARDANDO_AVALIACAO, Situacao.EM_ATENDIMENTO);
        if (prioridade == null) {
            throw new IllegalArgumentException("A prioridade da emergência deve ser informada.");
        }
        this.prioridade = prioridade;
    }

    public Prioridade getPrioridade() { return prioridade; }
}
