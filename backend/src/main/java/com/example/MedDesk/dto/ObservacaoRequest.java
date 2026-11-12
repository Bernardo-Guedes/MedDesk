package com.example.MedDesk.dto;

import jakarta.validation.constraints.NotBlank;

public record ObservacaoRequest(
        @NotBlank String observacao
) {
}