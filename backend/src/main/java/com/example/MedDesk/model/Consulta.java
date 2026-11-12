package com.example.MedDesk.model;

import com.example.MedDesk.exception.RegraNegocioException;
import com.example.MedDesk.model.enums.StatusConsulta;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;

public abstract class Consulta extends Atendimento implements Agendavel {

    private StatusConsulta statusConsulta;

    protected Consulta() {
        super();
    }

    @Override
    public Atendimento agendar(Paciente paciente, ProfissionalSaude profissional,
                               LocalDate data, LocalTime horario) {
        if (statusConsulta != null) {
            throw new RegraNegocioException("Esta consulta já foi agendada. Para remarcar, crie um novo agendamento");
        }
        validarDataFutura(data, horario);
        setPaciente(paciente);
        setProfissionalSaude(profissional);
        setData(data);
        setHorario(horario);
        validar();
        this.statusConsulta = StatusConsulta.AGENDADA;
        return this;
    }

    public void confirmar() {
        exigirStatus("confirmar", StatusConsulta.AGENDADA);
        this.statusConsulta = StatusConsulta.CONFIRMADA;
    }

    public void iniciar() {
        exigirStatus("iniciar", StatusConsulta.AGENDADA, StatusConsulta.CONFIRMADA);
        this.statusConsulta = StatusConsulta.EM_ATENDIMENTO;
    }

    public void concluir() {
        exigirStatus("concluir", StatusConsulta.EM_ATENDIMENTO);
        this.statusConsulta = StatusConsulta.CONCLUIDA;
    }

    public void cancelar() {
        exigirStatus("cancelar", StatusConsulta.AGENDADA, StatusConsulta.CONFIRMADA);
        this.statusConsulta = StatusConsulta.CANCELADA;
    }

    public void informarAusencia() {
        exigirStatus("registrar ausência", StatusConsulta.AGENDADA, StatusConsulta.CONFIRMADA);
        this.statusConsulta = StatusConsulta.NAO_COMPARECEU;
    }

    @Override
    public void registrarObservacoes(String observacoes) {
        exigirStatus("registrar observações", StatusConsulta.EM_ATENDIMENTO, StatusConsulta.CONCLUIDA);
        super.registrarObservacoes(observacoes);
    }

    private void exigirStatus(String acao, StatusConsulta... permitidos) {
        boolean permitido = Arrays.asList(permitidos).contains(statusConsulta);
        if (!permitido) {
            throw new RegraNegocioException("Não é possível " + acao + ": a consulta está com status " + statusConsulta);
        }
    }

    @Override
    public String exibirInformacoes() {
        return super.exibirInformacoes() + " | Status: " + statusConsulta;
    }

    public StatusConsulta getStatusConsulta() { return statusConsulta; }
}
