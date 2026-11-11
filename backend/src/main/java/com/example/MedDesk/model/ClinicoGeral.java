package com.example.MedDesk.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class ClinicoGeral extends ProfissionalSaude {
    @OneToMany(mappedBy = "profissionalResponsavel")
    private List<ConsultaRotina> consultasGerais = new ArrayList<>();

    protected ClinicoGeral() {}

    public ClinicoGeral(String nome, String registro, String telefone, String email) {
        super(nome, registro, telefone, email);
    }

    public void encaminharPaciente(Paciente paciente) {
        for (ConsultaRotina consulta : consultasGerais) {
            Paciente atendido = consulta.getPaciente();
            boolean mesmoPaciente = atendido == paciente
                    || (paciente.getId() != null && paciente.getId().equals(atendido.getId()));
            if (mesmoPaciente && consulta.getSituacao() == Situacao.CONCLUIDA) {
                return;
            }
        }
        throw new IllegalStateException("O paciente precisa ter uma consulta de rotina concluída com o clínico.");
    }

    public void registrarConsulta(ConsultaRotina consulta) {
        validarResponsavel(consulta);
        if (!consultasGerais.contains(consulta)) {
            consultasGerais.add(consulta);
        }
    }

    public ArrayList<ConsultaRotina> getConsultasGerais() { return new ArrayList<>(consultasGerais); }
}
