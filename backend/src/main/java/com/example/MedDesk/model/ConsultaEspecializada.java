package com.example.MedDesk.model;

import com.example.MedDesk.exception.RegraNegocioException;
import com.example.MedDesk.model.enums.Especialidade;

public class ConsultaEspecializada extends Consulta {

    private Especialidade especialidade;

    public ConsultaEspecializada(Especialidade especialidade) {
        super();
        this.especialidade = especialidade;
    }

    @Override
    public void validar() {
        validarDadosComuns();
        if (especialidade == null) {
            throw new RegraNegocioException("A especialidade da consulta é obrigatória");
        }
        if (!(getProfissionalSaude() instanceof Especialista especialista)) {
            throw new RegraNegocioException("Consultas especializadas devem ser realizadas por um especialista");
        }
        if (especialista.getEspecialidade() != especialidade) {
            throw new RegraNegocioException("O especialista atua em " + especialista.getEspecialidade()
                    + " e não pode atender uma consulta de " + especialidade);
        }
    }

    @Override
    public String exibirInformacoes() {
        return super.exibirInformacoes() + " | Especialidade: " + especialidade;
    }

    public Especialidade getEspecialidade() { return especialidade; }
}
