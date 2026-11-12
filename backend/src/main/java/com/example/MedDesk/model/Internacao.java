package com.example.MedDesk.model;

import com.example.MedDesk.exception.RegraNegocioException;
import com.example.MedDesk.model.enums.SituacaoQuarto;

import java.time.LocalDate;
import java.time.LocalTime;

public class Internacao extends Atendimento {

    private Quarto quarto;
    private LocalDate dataPrevistaAlta;
    private LocalDate dataEfetivaAlta;

    public Internacao(Paciente paciente, ProfissionalSaude profissionalSaude,
                      Quarto quarto, LocalDate dataPrevistaAlta) {
        super(paciente, profissionalSaude, LocalDate.now(), LocalTime.now().withSecond(0).withNano(0));
        this.quarto = quarto;
        this.dataPrevistaAlta = dataPrevistaAlta;
    }

    @Override
    public void validar() {
        validarDadosComuns();
        if (quarto == null) {
            throw new RegraNegocioException("A internação deve estar associada a um quarto");
        }
        if (quarto.getSituacaoAtual() != SituacaoQuarto.DISPONIVEL || quarto.quantidadeDisponivel() <= 0) {
            throw new RegraNegocioException("O quarto " + quarto.getNumeroIdentificacao() + " não está disponível");
        }
        if (dataPrevistaAlta == null || dataPrevistaAlta.isBefore(getData())) {
            throw new RegraNegocioException("A data prevista de alta é obrigatória e não pode ser anterior à data de entrada");
        }
    }

    public void registrarAlta() {
        if (dataEfetivaAlta != null) {
            throw new RegraNegocioException("A alta desta internação já foi registrada em " + dataEfetivaAlta);
        }
        this.dataEfetivaAlta = LocalDate.now();
        quarto.removerPaciente(getPaciente());
    }

    @Override
    public String exibirInformacoes() {
        return super.exibirInformacoes() + " | Quarto: " + quarto.getNumeroIdentificacao()
                + " | Alta prevista: " + dataPrevistaAlta
                + " | Alta efetiva: " + (dataEfetivaAlta != null ? dataEfetivaAlta : "-");
    }

    public Quarto getQuarto() { return quarto; }
    public LocalDate getDataPrevistaAlta() { return dataPrevistaAlta; }
    public LocalDate getDataEfetivaAlta() { return dataEfetivaAlta; }
}