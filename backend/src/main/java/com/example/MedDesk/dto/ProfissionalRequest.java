package com.example.MedDesk.dto;

import com.example.MedDesk.model.enums.Especialidade;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record ProfissionalRequest(
        @NotBlank
        @Pattern(regexp = "CLINICO_GERAL|ESPECIALISTA|CIRURGIAO",
                message = "deve ser CLINICO_GERAL, ESPECIALISTA ou CIRURGIAO")
        String categoria,
        @NotBlank String nome,
        @NotBlank String registroProfissional,
        String telefone,
        @Email String email,
        Especialidade especialidade,
        String atuacaoCirurgica
) {
}