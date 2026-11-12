package com.example.MedDesk.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public record ConsultaRotinaRequest(
        @NotNull UUID pacienteId,
        @NotNull UUID profissionalId,
        @NotNull LocalDate data,
        @NotNull LocalTime horario,
        @NotBlank String motivo
) {
}
