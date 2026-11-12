package com.example.MedDesk.model;

import java.time.LocalDate;
import java.time.LocalTime;

public interface Agendavel {

    Atendimento agendar(Paciente paciente, ProfissionalSaude profissional, LocalDate data, LocalTime horario);
}