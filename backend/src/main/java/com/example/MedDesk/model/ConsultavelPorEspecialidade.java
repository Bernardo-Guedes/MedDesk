package com.example.MedDesk.model;

import com.example.MedDesk.model.enums.Especialidade;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface ConsultavelPorEspecialidade {

    List<ProfissionalSaude> consultarProfissionaisDisponiveis(Especialidade especialidade,
                                                              LocalDate data, LocalTime horario);
}
