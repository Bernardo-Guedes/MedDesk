package com.example.MedDesk.dto;

import com.example.MedDesk.model.enums.Especialidade;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public record ConsultaEspecializadaRequest(
        @NotNull UUID pacienteId,
        @NotNull UUID profissionalId,
        @NotNull LocalDate data,
        @NotNull LocalTime horario,
        @NotNull Especialidade especialidade
) {
}