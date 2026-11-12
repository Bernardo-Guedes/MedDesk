package com.example.MedDesk.model;

import com.example.MedDesk.exception.RegraNegocioException;
import com.example.MedDesk.model.enums.Prioridade;
import com.example.MedDesk.model.enums.StatusEmergencia;

import java.time.LocalDate;
import java.time.LocalTime;

public class Emergencia extends Atendimento {

    private Prioridade prioridade;
    private StatusEmergencia statusEmergencia;

    public Emergencia(Paciente paciente, ProfissionalSaude profissionalSaude, Prioridade prioridade) {
        super(paciente, profissionalSaude, LocalDate.now(), LocalTime.now().withSecond(0).withNano(0));
        this.prioridade = prioridade;
        this.statusEmergencia = StatusEmergencia.AGUARDANDO_AVALIACAO;
    }

    @Override
    public void validar() {
        validarDadosComuns();
        if (prioridade == null) {
            throw new RegraNegocioException("A classificação de prioridade é obrigatória no atendimento de emergência");
        }
    }

    public void iniciar() {
        if (statusEmergencia != StatusEmergencia.AGUARDANDO_AVALIACAO) {
            throw new RegraNegocioException("Só é possível iniciar uma emergência que aguarda avaliação (status atual: "
                    + statusEmergencia + ")");
        }
        this.statusEmergencia = StatusEmergencia.INICIADO;
    }

    public void concluir() {
        if (statusEmergencia != StatusEmergencia.INICIADO) {
            throw new RegraNegocioException("Só é possível concluir uma emergência já iniciada (status atual: "
                    + statusEmergencia + ")");
        }
        this.statusEmergencia = StatusEmergencia.CONCLUIDO;
    }

    @Override
    public String exibirInformacoes() {
        return super.exibirInformacoes() + " | Prioridade: " + prioridade + " | Status: " + statusEmergencia;
    }

    public Prioridade getPrioridade() { return prioridade; }
    public StatusEmergencia getStatusEmergencia() { return statusEmergencia; }
}
