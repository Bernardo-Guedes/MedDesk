package com.example.MedDesk.dto;

import com.example.MedDesk.model.Atendimento;
import com.example.MedDesk.model.AtendimentoEmergencia;
import com.example.MedDesk.model.Cirurgia;
import com.example.MedDesk.model.ConsultaEspecializada;
import com.example.MedDesk.model.ConsultaRotina;
import com.example.MedDesk.model.Internacao;
import com.example.MedDesk.model.Prioridade;
import com.example.MedDesk.model.Situacao;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;

public final class AtendimentoDto {
    private AtendimentoDto() {}

    public enum Modalidade {
        ROTINA, ESPECIALIZADA, EMERGENCIA, CIRURGIA, INTERNACAO
    }

    public enum Operacao {
        CONFIRMAR, CANCELAR, INICIAR, CONCLUIR, INFORMAR_AUSENCIA, REGISTRAR_ALTA, ALTERAR_PRIORIDADE,
        REGISTRAR_OBSERVACAO, REGISTRAR_OBSERVACAO_PRE, REGISTRAR_OBSERVACAO_POS
    }

    public static class NovaConsulta {
        @NotNull
        private Modalidade modalidade;
        @NotNull
        @Positive
        private Long pacienteId;
        @NotNull
        @Positive
        private Long profissionalId;
        private LocalDate data;
        private LocalTime horario;
        private String motivo;
        private String especialidade;
        private Prioridade prioridade;
        private String descricaoProcedimento;
        private String tipo;

        public Modalidade getModalidade() {
            return modalidade;
        }

        public void setModalidade(Modalidade modalidade) {
            this.modalidade = modalidade;
        }

        public Long getPacienteId() {
            return pacienteId;
        }

        public void setPacienteId(Long pacienteId) {
            this.pacienteId = pacienteId;
        }

        public Long getProfissionalId() {
            return profissionalId;
        }

        public void setProfissionalId(Long profissionalId) {
            this.profissionalId = profissionalId;
        }

        public LocalDate getData() {
            return data;
        }

        public void setData(LocalDate data) {
            this.data = data;
        }

        public LocalTime getHorario() {
            return horario;
        }

        public void setHorario(LocalTime horario) {
            this.horario = horario;
        }

        public String getMotivo() {
            return motivo;
        }

        public void setMotivo(String motivo) {
            this.motivo = motivo;
        }

        public String getEspecialidade() {
            return especialidade;
        }

        public void setEspecialidade(String especialidade) {
            this.especialidade = especialidade;
        }

        public Prioridade getPrioridade() {
            return prioridade;
        }

        public void setPrioridade(Prioridade prioridade) {
            this.prioridade = prioridade;
        }

        public String getDescricaoProcedimento() {
            return descricaoProcedimento;
        }

        public void setDescricaoProcedimento(String descricaoProcedimento) {
            this.descricaoProcedimento = descricaoProcedimento;
        }

        public String getTipo() {
            return tipo;
        }

        public void setTipo(String tipo) {
            this.tipo = tipo;
        }
    }

    public static class NovaInternacao {
        @NotNull
        @Positive
        private Long pacienteId;
        @NotNull
        @Positive
        private Long profissionalId;
        @NotNull
        private LocalDate data;
        @NotNull
        private LocalTime horario;
        @NotBlank
        private String quarto;
        @NotNull
        private LocalDate dataPrevistaAlta;

        public Long getPacienteId() {
            return pacienteId;
        }

        public void setPacienteId(Long pacienteId) {
            this.pacienteId = pacienteId;
        }

        public Long getProfissionalId() {
            return profissionalId;
        }

        public void setProfissionalId(Long profissionalId) {
            this.profissionalId = profissionalId;
        }

        public LocalDate getData() {
            return data;
        }

        public void setData(LocalDate data) {
            this.data = data;
        }

        public LocalTime getHorario() {
            return horario;
        }

        public void setHorario(LocalTime horario) {
            this.horario = horario;
        }

        public String getQuarto() {
            return quarto;
        }

        public void setQuarto(String quarto) {
            this.quarto = quarto;
        }

        public LocalDate getDataPrevistaAlta() {
            return dataPrevistaAlta;
        }

        public void setDataPrevistaAlta(LocalDate dataPrevistaAlta) {
            this.dataPrevistaAlta = dataPrevistaAlta;
        }
    }

    public static class NovoEncaminhamento {
        @NotNull
        @Positive
        private Long profissionalId;
        @NotNull
        private LocalDate data;
        @NotNull
        private LocalTime horario;
        @NotBlank
        private String especialidade;

        public Long getProfissionalId() {
            return profissionalId;
        }

        public void setProfissionalId(Long profissionalId) {
            this.profissionalId = profissionalId;
        }

        public LocalDate getData() {
            return data;
        }

        public void setData(LocalDate data) {
            this.data = data;
        }

        public LocalTime getHorario() {
            return horario;
        }

        public void setHorario(LocalTime horario) {
            this.horario = horario;
        }

        public String getEspecialidade() {
            return especialidade;
        }

        public void setEspecialidade(String especialidade) {
            this.especialidade = especialidade;
        }
    }

    public static class Alteracao {
        @NotNull
        private Operacao operacao;
        private String observacao;
        private Prioridade prioridade;
        private LocalDate dataEfetiva;
        private LocalTime horarioEfetivo;

        public Operacao getOperacao() {
            return operacao;
        }

        public void setOperacao(Operacao operacao) {
            this.operacao = operacao;
        }

        public String getObservacao() {
            return observacao;
        }

        public void setObservacao(String observacao) {
            this.observacao = observacao;
        }

        public Prioridade getPrioridade() {
            return prioridade;
        }

        public void setPrioridade(Prioridade prioridade) {
            this.prioridade = prioridade;
        }

        public LocalDate getDataEfetiva() {
            return dataEfetiva;
        }

        public void setDataEfetiva(LocalDate dataEfetiva) {
            this.dataEfetiva = dataEfetiva;
        }

        public LocalTime getHorarioEfetivo() {
            return horarioEfetivo;
        }

        public void setHorarioEfetivo(LocalTime horarioEfetivo) {
            this.horarioEfetivo = horarioEfetivo;
        }
    }

    public static class DadosAtendimento {
        private final String id;
        private final Modalidade modalidade;
        private final Long pacienteId;
        private final Long profissionalId;
        private final LocalDate data;
        private final LocalTime horario;
        private final Situacao situacao;
        private final ArrayList<String> observacoes;
        private final HashMap<String, Object> detalhes;

        public DadosAtendimento(Atendimento atendimento) {
            id = atendimento.getId();
            pacienteId = atendimento.getPaciente().getId();
            profissionalId = atendimento.getProfissionalResponsavel().getId();
            data = atendimento.getData();
            horario = atendimento.getHorario();
            situacao = atendimento.getSituacao();
            observacoes = atendimento.getObservacoes();
            detalhes = new HashMap<>();
            modalidade = preencherDetalhes(atendimento);
        }

        private Modalidade preencherDetalhes(Atendimento atendimento) {
            if (atendimento instanceof ConsultaRotina) {
                ConsultaRotina consulta = (ConsultaRotina) atendimento;
                detalhes.put("motivo", consulta.getMotivo());
                return Modalidade.ROTINA;
            }
            if (atendimento instanceof ConsultaEspecializada) {
                ConsultaEspecializada consulta = (ConsultaEspecializada) atendimento;
                detalhes.put("especialidade", consulta.getEspecialidade());
                return Modalidade.ESPECIALIZADA;
            }
            if (atendimento instanceof AtendimentoEmergencia) {
                AtendimentoEmergencia emergencia = (AtendimentoEmergencia) atendimento;
                detalhes.put("prioridade", emergencia.getPrioridade());
                return Modalidade.EMERGENCIA;
            }
            if (atendimento instanceof Cirurgia) {
                Cirurgia cirurgia = (Cirurgia) atendimento;
                detalhes.put("descricaoProcedimento", cirurgia.getDescricaoProcedimento());
                detalhes.put("tipo", cirurgia.getTipo());
                detalhes.put("observacoesPreOperatorias", cirurgia.getObservacoesPreOperatorias());
                detalhes.put("observacoesPosOperatorias", cirurgia.getObservacoesPosOperatorias());
                detalhes.put("dataEfetiva", cirurgia.getDataEfetiva());
                detalhes.put("horarioEfetivo", cirurgia.getHorarioEfetivo());
                return Modalidade.CIRURGIA;
            }
            if (atendimento instanceof Internacao) {
                Internacao internacao = (Internacao) atendimento;
                detalhes.put("quarto", internacao.getQuarto().getNumeroIdentificacao());
                detalhes.put("dataPrevistaAlta", internacao.getDataPrevistaAlta());
                detalhes.put("dataEfetivaAlta", internacao.getDataEfetivaAlta());
                return Modalidade.INTERNACAO;
            }
            throw new IllegalStateException("Modalidade de atendimento desconhecida.");
        }

        public String getId() {
            return id;
        }

        public Modalidade getModalidade() {
            return modalidade;
        }

        public Long getPacienteId() {
            return pacienteId;
        }

        public Long getProfissionalId() {
            return profissionalId;
        }

        public LocalDate getData() {
            return data;
        }

        public LocalTime getHorario() {
            return horario;
        }

        public Situacao getSituacao() {
            return situacao;
        }

        public ArrayList<String> getObservacoes() {
            return observacoes;
        }

        public HashMap<String, Object> getDetalhes() {
            return detalhes;
        }
    }
}
