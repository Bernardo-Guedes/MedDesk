package com.example.MedDesk.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class HistoricoMedico {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany
    @JoinColumn(name = "historico_medico_id")
    @OrderBy("data DESC, horario DESC, id ASC")
    private List<Atendimento> consultas = new ArrayList<>();

    public ArrayList<Atendimento> consultarHistorico() { return new ArrayList<>(consultas); }

    public void adicionarAtendimento(Atendimento atendimento) {
        if (buscarAtendimento(atendimento.getId()) == null) {
            consultas.add(atendimento);
        }
    }

    public void removerAtendimento(Atendimento atendimento) {
        consultas.remove(atendimento);
    }

    public Atendimento buscarAtendimento(String id) {
        for (Atendimento atendimento : consultas) {
            if (atendimento.getId().equals(id)) {
                return atendimento;
            }
        }
        return null;
    }

    public Long getId() { return id; }
}
