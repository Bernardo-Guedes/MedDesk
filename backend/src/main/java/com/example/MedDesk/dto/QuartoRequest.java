package com.example.MedDesk.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record QuartoRequest(
        @NotNull @Min(1) Integer numeroIdentificacao,
        @NotNull @Min(0) Integer andar,
        @NotNull @Min(1) Integer capacidadeMaximaPacientes
) {
}