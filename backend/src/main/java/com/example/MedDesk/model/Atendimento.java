package com.example.MedDesk.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Atendimento {
    @Id
    private String id = UUID.randomUUID().toString();
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Paciente paciente;
    @ManyToOne(optional = false)
    private ProfissionalSaude profissionalResponsavel;
    @ElementCollection
    @OrderColumn
    private List<String> observacoes = new ArrayList<>();
    @Column(nullable = false)
    private LocalDate data;
    @Column(nullable = false)
    private LocalTime horario;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Situacao situacao;

    protected Atendimento() {}

    protected Atendimento(Paciente paciente, ProfissionalSaude profissionalResponsavel, LocalDate data,
                          LocalTime horario, Situacao situacao) {
        this.paciente = Objects.requireNonNull(paciente);
        this.profissionalResponsavel = Objects.requireNonNull(profissionalResponsavel);
        this.data = Objects.requireNonNull(data);
        this.horario = Objects.requireNonNull(horario);
        this.situacao = situacao;
    }

    protected abstract void validarCondicoes();

    protected final void agendarAtendimento() {
        exigirSituacao((Situacao) null);
        validarCondicoes();
        situacao = Situacao.AGENDADA;
    }

    public void registrarObservacao(String observacao) {
        if (observacao == null || observacao.isBlank()) {
            throw new IllegalArgumentException("A observação deve ser informada.");
        }
        observacoes.add(observacao.strip());
    }

    public String exibirInformacoes() {
        return id + " " + data + " " + horario + " " + situacao;
    }

    public void confirmar() {
        if (!(this instanceof ConsultaRotina || this instanceof ConsultaEspecializada)) {
            throw new IllegalStateException("A confirmação está disponível para consultas.");
        }
        exigirSituacao(Situacao.AGENDADA);
        situacao = Situacao.CONFIRMADA;
    }

    public void cancelar() {
        exigirSituacao(Situacao.AGENDADA, Situacao.CONFIRMADA);
        situacao = Situacao.CANCELADA;
    }

    public void iniciar() {
        exigirSituacao(Situacao.AGENDADA, Situacao.CONFIRMADA, Situacao.AGUARDANDO_AVALIACAO);
        validarCondicoes();
        antesDeIniciar();
        situacao = Situacao.EM_ATENDIMENTO;
    }

    protected void antesDeIniciar() {}

    public void concluir() {
        exigirSituacao(Situacao.EM_ATENDIMENTO);
        situacao = Situacao.CONCLUIDA;
    }

    public void informarAusencia() {
        if (!(this instanceof ConsultaRotina || this instanceof ConsultaEspecializada)) {
            throw new IllegalStateException("O registro de ausência está disponível para consultas.");
        }
        exigirSituacao(Situacao.AGENDADA, Situacao.CONFIRMADA);
        situacao = Situacao.NAO_COMPARECEU;
    }

    public void encaminharEspecializacao() {
        if (!(this instanceof ConsultaRotina)) {
            throw new IllegalStateException("O encaminhamento parte de uma consulta de rotina.");
        }
        exigirSituacao(Situacao.CONCLUIDA);
    }

    protected final void exigirSituacao(Situacao... permitidas) {
        for (Situacao permitida : permitidas) {
            if (situacao == permitida) {
                return;
            }
        }
        throw new IllegalStateException("Operação incompatível com a situação atual: " + situacao + ".");
    }

    public String getId() { return id; }
    public Paciente getPaciente() { return paciente; }
    public ProfissionalSaude getProfissionalResponsavel() { return profissionalResponsavel; }
    public ArrayList<String> getObservacoes() { return new ArrayList<>(observacoes); }
    public LocalDate getData() { return data; }
    public LocalTime getHorario() { return horario; }
    public Situacao getSituacao() { return situacao; }
}
