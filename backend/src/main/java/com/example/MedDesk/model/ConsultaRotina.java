package com.example.MedDesk.model;

import com.example.MedDesk.exception.RegraNegocioException;

public class ConsultaRotina extends Consulta {

    private String motivo;

    public ConsultaRotina(String motivo) {
        super();
        this.motivo = motivo;
    }

    @Override
    public void validar() {
        validarDadosComuns();
        if (motivo == null || motivo.isBlank()) {
            throw new RegraNegocioException("O motivo da consulta de rotina é obrigatório");
        }
        if (!(getProfissionalSaude() instanceof ClinicoGeral)) {
            throw new RegraNegocioException("Consultas de rotina devem ser realizadas por um clínico geral");
        }
    }

    @Override
    public String exibirInformacoes() {
        return super.exibirInformacoes() + " | Motivo: " + motivo;
    }

    public String getMotivo() { return motivo; }
}
