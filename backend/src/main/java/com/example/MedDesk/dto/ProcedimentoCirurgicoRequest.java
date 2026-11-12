package com.example.MedDesk.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public record ProcedimentoCirurgicoRequest(
        @NotNull UUID pacienteId,
        @NotNull UUID cirurgiaoId,
        @NotNull LocalDate data,
        @NotNull LocalTime horario,
        @NotBlank String descricao,
        @NotBlank String tipo
) {
}