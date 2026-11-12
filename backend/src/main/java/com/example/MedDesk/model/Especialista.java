package com.example.MedDesk.model;

import com.example.MedDesk.exception.RegraNegocioException;
import com.example.MedDesk.model.enums.Especialidade;

public class Especialista extends ProfissionalSaude {

    private Especialidade especialidade;

    public Especialista(String nome, String registroProfissional, String telefone, String email,
                        Especialidade especialidade) {
        super(nome, registroProfissional, telefone, email);
        setEspecialidade(especialidade);
    }

    @Override
    public String exibirInformacoes() {
        return super.exibirInformacoes() + " | Especialidade: " + especialidade;
    }

    public Especialidade getEspecialidade() { return especialidade; }

    public void setEspecialidade(Especialidade especialidade) {
        if (especialidade == null) {
            throw new RegraNegocioException("A especialidade é obrigatória para o especialista");
        }
        this.especialidade = especialidade;
    }
}
