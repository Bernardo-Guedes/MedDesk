package com.example.MedDesk.dto;

import com.example.MedDesk.model.enums.Prioridade;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record EmergenciaRequest(
        @NotNull UUID pacienteId,
        @NotNull UUID profissionalId,
        @NotNull Prioridade prioridade
) {
}