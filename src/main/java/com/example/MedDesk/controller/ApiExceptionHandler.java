package com.example.MedDesk.controller;

import java.util.ArrayList;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ApiExceptionHandler {
    @ExceptionHandler(IllegalArgumentException.class)
    public ProblemDetail entradaInvalida(IllegalArgumentException erro) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, erro.getMessage());
    }

    @ExceptionHandler(IllegalStateException.class)
    public ProblemDetail conflito(IllegalStateException erro) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, erro.getMessage());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ProblemDetail conflitoDeCadastro() {
        return ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, "O registro conflita com dados já cadastrados.");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail camposInvalidos(MethodArgumentNotValidException erro) {
        ProblemDetail detalhe = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Há campos inválidos.");
        ArrayList<String> camposInvalidos = new ArrayList<>();
        for (FieldError campo : erro.getBindingResult().getFieldErrors()) {
            camposInvalidos.add(campo.getField() + " " + campo.getDefaultMessage());
        }
        detalhe.setProperty("campos", camposInvalidos);
        return detalhe;
    }
}
