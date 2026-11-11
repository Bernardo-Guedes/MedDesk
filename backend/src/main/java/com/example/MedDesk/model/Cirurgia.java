package com.example.MedDesk.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Cirurgia extends Atendimento implements Agendavel {
    private String descricaoProcedimento;
    private String tipo;
    @ElementCollection
    @OrderColumn
    private List<String> observacoesPreOperatorias = new ArrayList<>();
    @ElementCollection
    @OrderColumn
    private List<String> observacoesPosOperatorias = new ArrayList<>();
    private LocalDate dataEfetiva;
    private LocalTime horarioEfetivo;

    protected Cirurgia() {}

    public Cirurgia(Paciente paciente, ProfissionalSaude profissional, LocalDate data, LocalTime horario,
                    String descricaoProcedimento, String tipo) {
        super(paciente, profissional, data, horario, null);
        this.descricaoProcedimento = descricaoProcedimento;
        this.tipo = tipo;
        agendar();
    }

    @Override
    public void agendar() {
        agendarAtendimento();
    }

    @Override
    protected void validarCondicoes() {
        if (!(getProfissionalResponsavel() instanceof Cirurgiao)) {
            throw new IllegalArgumentException("Uma cirurgia exige um cirurgião responsável.");
        }
        if (descricaoProcedimento == null || descricaoProcedimento.isBlank() || tipo == null || tipo.isBlank()) {
            throw new IllegalArgumentException("A descrição e o tipo da cirurgia devem ser informados.");
        }
    }

    @Override
    public void iniciar() {
        realizarCirurgia();
    }

    public void realizarCirurgia() {
        realizarCirurgia(LocalDateTime.now());
    }

    public void realizarCirurgia(LocalDateTime realizacao) {
        super.iniciar();
        dataEfetiva = realizacao.toLocalDate();
        horarioEfetivo = realizacao.toLocalTime().withNano(0);
    }

    @Override
    public void cancelar() {
        exigirSituacao(Situacao.AGENDADA);
        super.cancelar();
    }

    public void registrarObservacaoPre(String observacao) {
        exigirSituacao(Situacao.AGENDADA);
        registrar(observacoesPreOperatorias, observacao);
    }

    public void registrarObservacaoPos(String observacao) {
        exigirSituacao(Situacao.CONCLUIDA);
        registrar(observacoesPosOperatorias, observacao);
    }

    private void registrar(List<String> observacoes, String observacao) {
        if (observacao == null || observacao.isBlank()) {
            throw new IllegalArgumentException("A observação deve ser informada.");
        }
        observacoes.add(observacao.strip());
    }

    public String getDescricaoProcedimento() { return descricaoProcedimento; }
    public String getTipo() { return tipo; }
    public ArrayList<String> getObservacoesPreOperatorias() { return new ArrayList<>(observacoesPreOperatorias); }
    public ArrayList<String> getObservacoesPosOperatorias() { return new ArrayList<>(observacoesPosOperatorias); }
    public LocalDate getDataEfetiva() { return dataEfetiva; }
    public LocalTime getHorarioEfetivo() { return horarioEfetivo; }
}
