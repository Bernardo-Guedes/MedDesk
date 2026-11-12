package com.example.MedDesk.model;

import com.example.MedDesk.exception.RegraNegocioException;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "modalidade")
@JsonSubTypes({
        @JsonSubTypes.Type(value = ConsultaRotina.class, name = "CONSULTA_ROTINA"),
        @JsonSubTypes.Type(value = ConsultaEspecializada.class, name = "CONSULTA_ESPECIALIZADA"),
        @JsonSubTypes.Type(value = Emergencia.class, name = "EMERGENCIA"),
        @JsonSubTypes.Type(value = ProcedimentoCirurgico.class, name = "PROCEDIMENTO_CIRURGICO"),
        @JsonSubTypes.Type(value = Internacao.class, name = "INTERNACAO")
})

public abstract class Atendimento {

    private final UUID id;
    private Paciente paciente;
    private ProfissionalSaude profissionalSaude;
    private LocalDate data;
    private LocalTime horario;
    private String observacoes;

    protected Atendimento() {
        this.id = UUID.randomUUID();
    }

    protected Atendimento(Paciente paciente, ProfissionalSaude profissionalSaude,
                          LocalDate data, LocalTime horario) {
        this();
        this.paciente = paciente;
        this.profissionalSaude = profissionalSaude;
        this.data = data;
        this.horario = horario;
    }

    public abstract void validar();

    public String exibirInformacoes() {
        return getClass().getSimpleName() + " em " + data + " às " + horario
                + " | Paciente: " + (paciente != null ? paciente.getNome() : "-")
                + " | Profissional: " + (profissionalSaude != null ? profissionalSaude.getNome() : "-")
                + " | Observações: " + (observacoes != null ? observacoes : "-");
    }

    public void registrarObservacoes(String observacoes) {
        if (observacoes == null || observacoes.isBlank()) {
            throw new RegraNegocioException("As observações não podem ser vazias");
        }
        this.observacoes = observacoes.trim();
    }

    protected void validarDadosComuns() {
        if (paciente == null) {
            throw new RegraNegocioException("O atendimento deve estar associado a um paciente");
        }
        if (profissionalSaude == null) {
            throw new RegraNegocioException("O atendimento deve estar associado a um profissional de saúde");
        }
        if (data == null || horario == null) {
            throw new RegraNegocioException("Data e horário do atendimento são obrigatórios");
        }
    }

    protected void validarDataFutura(LocalDate data, LocalTime horario) {
        if (data == null || horario == null) {
            throw new RegraNegocioException("Data e horário do atendimento são obrigatórios");
        }
        if (LocalDateTime.of(data, horario).isBefore(LocalDateTime.now())) {
            throw new RegraNegocioException("Não é possível agendar para uma data/horário que já passou");
        }
    }

    public UUID getId() { return id; }
    public Paciente getPaciente() { return paciente; }
    public ProfissionalSaude getProfissionalSaude() { return profissionalSaude; }
    public LocalDate getData() { return data; }
    public LocalTime getHorario() { return horario; }
    public String getObservacoes() { return observacoes; }

    protected void setPaciente(Paciente paciente) { this.paciente = paciente; }
    protected void setProfissionalSaude(ProfissionalSaude profissionalSaude) { this.profissionalSaude = profissionalSaude; }
    protected void setData(LocalDate data) { this.data = data; }
    protected void setHorario(LocalTime horario) { this.horario = horario; }
}
