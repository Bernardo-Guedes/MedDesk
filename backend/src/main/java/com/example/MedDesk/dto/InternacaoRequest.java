package com.example.MedDesk.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

public record InternacaoRequest(
        @NotNull UUID pacienteId,
        @NotNull UUID profissionalId,
        @NotNull Integer numeroQuarto,
        @NotNull LocalDate dataPrevistaAlta,
        String observacoes
) {
}