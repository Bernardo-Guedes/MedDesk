package com.example.MedDesk.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

import java.time.LocalDate;

public record PacienteRequest(
        @NotBlank String nome,
        @NotBlank String cpf,
        @NotNull @Past LocalDate dataNascimento,
        String telefone,
        String endereco,
        @Email String email
) {
}