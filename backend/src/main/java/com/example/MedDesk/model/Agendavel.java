package com.example.MedDesk.model;

import java.util.ArrayList;

public interface Agendavel {
    void agendar();

    default ArrayList<Especialista> consultarProfissionaisDisponiveis(String especialidade, ArrayList<Especialista> disponiveis) {
        return Especialista.consultarProfissionaisDisponiveis(especialidade, disponiveis);
    }
}
