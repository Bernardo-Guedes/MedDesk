package com.example.MedDesk.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public record EncaminhamentoRequest(
        @NotNull UUID clinicoGeralId,
        @NotNull UUID pacienteId,
        @NotNull UUID especialistaId,
        @NotNull LocalDate data,
        @NotNull LocalTime horario
) {
}