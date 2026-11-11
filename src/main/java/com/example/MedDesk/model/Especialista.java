package com.example.MedDesk.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Especialista extends ProfissionalSaude {
    private String especialidade;
    @OneToMany(mappedBy = "profissionalResponsavel")
    private List<ConsultaEspecializada> consultasEspecializadas = new ArrayList<>();

    protected Especialista() {}

    public Especialista(String nome, String registro, String telefone, String email, String especialidade) {
        super(nome, registro, telefone, email);
        this.especialidade = especialidade;
    }

    public boolean atuaEm(String especialidade) {
        return this.especialidade.equalsIgnoreCase(especialidade.strip());
    }

    public static ArrayList<Especialista> consultarProfissionaisDisponiveis(String especialidade,
            ArrayList<Especialista> disponiveis) {
        ArrayList<Especialista> encontrados = new ArrayList<>();
        for (Especialista profissional : disponiveis) {
            if (profissional.atuaEm(especialidade)) {
                encontrados.add(profissional);
            }
        }
        return encontrados;
    }

    public void registrarConsulta(ConsultaEspecializada consulta) {
        validarResponsavel(consulta);
        if (!consultasEspecializadas.contains(consulta)) {
            consultasEspecializadas.add(consulta);
        }
    }

    public String getEspecialidade() { return especialidade; }
    public ArrayList<ConsultaEspecializada> getConsultasEspecializadas() { return new ArrayList<>(consultasEspecializadas); }
}
