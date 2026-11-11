package com.example.MedDesk.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Cirurgiao extends ProfissionalSaude {
    private String atuacaoCirurgica;
    @OneToMany(mappedBy = "profissionalResponsavel")
    private List<Cirurgia> cirurgias = new ArrayList<>();

    protected Cirurgiao() {}

    public Cirurgiao(String nome, String registro, String telefone, String email, String atuacaoCirurgica) {
        super(nome, registro, telefone, email);
        this.atuacaoCirurgica = atuacaoCirurgica;
    }

    public void registrarCirurgia(Cirurgia cirurgia) {
        validarResponsavel(cirurgia);
        if (!cirurgias.contains(cirurgia)) {
            cirurgias.add(cirurgia);
        }
    }

    public String getAtuacaoCirurgica() { return atuacaoCirurgica; }
    public ArrayList<Cirurgia> getCirurgias() { return new ArrayList<>(cirurgias); }
}
