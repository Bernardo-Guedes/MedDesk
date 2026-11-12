package com.example.MedDesk.model;

import com.example.MedDesk.exception.RegraNegocioException;
import com.example.MedDesk.model.enums.StatusProcedimentoCirurgico;

import java.time.LocalDate;
import java.time.LocalTime;

public class ProcedimentoCirurgico extends Atendimento implements Agendavel {

    private String descricao;
    private String tipo;
    private String observacoesPreOperatorias;
    private String observacoesPosOperatorias;
    private LocalDate dataEfetiva;
    private LocalTime horarioEfetivo;
    private StatusProcedimentoCirurgico statusProcedimentoCirurgico;

    public ProcedimentoCirurgico(String descricao, String tipo) {
        super();
        this.descricao = descricao;
        this.tipo = tipo;
    }

    @Override
    public Atendimento agendar(Paciente paciente, ProfissionalSaude profissional,
                               LocalDate data, LocalTime horario) {
        if (statusProcedimentoCirurgico != null) {
            throw new RegraNegocioException("Este procedimento já foi agendado");
        }
        validarDataFutura(data, horario);
        setPaciente(paciente);
        setProfissionalSaude(profissional);
        setData(data);
        setHorario(horario);
        validar();
        this.statusProcedimentoCirurgico = StatusProcedimentoCirurgico.AGENDADO;
        return this;
    }

    @Override
    public void validar() {
        validarDadosComuns();
        if (!(getProfissionalSaude() instanceof Cirurgiao)) {
            throw new RegraNegocioException("Procedimentos cirúrgicos devem ser realizados por um cirurgião");
        }
        if (descricao == null || descricao.isBlank()) {
            throw new RegraNegocioException("A descrição do procedimento é obrigatória");
        }
        if (tipo == null || tipo.isBlank()) {
            throw new RegraNegocioException("O tipo de cirurgia é obrigatório");
        }
    }

    public void registrarObservacaoPre(String observacao) {
        if (statusProcedimentoCirurgico != StatusProcedimentoCirurgico.AGENDADO) {
            throw new RegraNegocioException("Observações pré-operatórias só podem ser registradas antes do início do procedimento");
        }
        exigirTexto(observacao);
        this.observacoesPreOperatorias = observacao.trim();
    }

    public void registrarObservacaoPos(String observacao) {
        if (statusProcedimentoCirurgico != StatusProcedimentoCirurgico.CONCLUIDO) {
            throw new RegraNegocioException("Observações pós-operatórias só podem ser registradas após a conclusão do procedimento");
        }
        exigirTexto(observacao);
        this.observacoesPosOperatorias = observacao.trim();
    }

    public void iniciar() {
        if (statusProcedimentoCirurgico != StatusProcedimentoCirurgico.AGENDADO) {
            throw new RegraNegocioException("Só é possível iniciar um procedimento agendado (status atual: "
                    + statusProcedimentoCirurgico + ")");
        }
        this.dataEfetiva = LocalDate.now();
        this.horarioEfetivo = LocalTime.now().withSecond(0).withNano(0);
        this.statusProcedimentoCirurgico = StatusProcedimentoCirurgico.EM_ANDAMENTO;
    }

    public void concluir() {
        if (statusProcedimentoCirurgico != StatusProcedimentoCirurgico.EM_ANDAMENTO) {
            throw new RegraNegocioException("Não é possível concluir um procedimento que não foi iniciado (status atual: "
                    + statusProcedimentoCirurgico + ")");
        }
        this.statusProcedimentoCirurgico = StatusProcedimentoCirurgico.CONCLUIDO;
    }

    public void cancelar() {
        if (statusProcedimentoCirurgico != StatusProcedimentoCirurgico.AGENDADO) {
            throw new RegraNegocioException("Só é possível cancelar um procedimento agendado (status atual: "
                    + statusProcedimentoCirurgico + ")");
        }
        this.statusProcedimentoCirurgico = StatusProcedimentoCirurgico.CANCELADO;
    }

    private void exigirTexto(String texto) {
        if (texto == null || texto.isBlank()) {
            throw new RegraNegocioException("A observação não pode ser vazia");
        }
    }

    @Override
    public String exibirInformacoes() {
        return super.exibirInformacoes() + " | Procedimento: " + descricao + " (" + tipo + ")"
                + " | Status: " + statusProcedimentoCirurgico;
    }


    public String getDescricao() { return descricao; }
    public String getTipo() { return tipo; }
    public String getObservacoesPreOperatorias() { return observacoesPreOperatorias; }
    public String getObservacoesPosOperatorias() { return observacoesPosOperatorias; }
    public LocalDate getDataEfetiva() { return dataEfetiva; }
    public LocalTime getHorarioEfetivo() { return horarioEfetivo; }
    public StatusProcedimentoCirurgico getStatusProcedimentoCirurgico() { return statusProcedimentoCirurgico; }
}
