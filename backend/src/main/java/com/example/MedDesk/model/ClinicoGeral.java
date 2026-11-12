package com.example.MedDesk.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class ClinicoGeral extends ProfissionalSaude {

    public ClinicoGeral(String nome, String registroProfissional, String telefone, String email) {
        super(nome, registroProfissional, telefone, email);
    }

    public ConsultaEspecializada encaminhar(Paciente paciente, Especialista especialista,
                                           LocalDate data, LocalTime horario) {
        ConsultaEspecializada consulta = new ConsultaEspecializada(especialista.getEspecialidade());
        consulta.agendar(paciente, especialista, data, horario);
        return consulta;
    }
}
