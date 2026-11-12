package com.example.MedDesk.exception;

import java.time.LocalDateTime;
import java.util.List;

public record ErroResponse(
        LocalDateTime timestamp,
        int status,
        String mensagem,
        List<String> detalhes
) {
    public ErroResponse(int status, String mensagem) {
        this(LocalDateTime.now(), status, mensagem, List.of());
    }

    public ErroResponse(int status, String mensagem, List<String> detalhes) {
        this(LocalDateTime.now(), status, mensagem, detalhes);
    }
}