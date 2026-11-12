package com.example.MedDesk.controller;

import com.example.MedDesk.dto.QuartoRequest;
import com.example.MedDesk.model.Quarto;
import com.example.MedDesk.model.enums.SituacaoQuarto;
import com.example.MedDesk.service.QuartoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/quartos")
public class QuartoController {

    private final QuartoService quartoService;

    public QuartoController(QuartoService quartoService) {
        this.quartoService = quartoService;
    }

    @PostMapping
    public ResponseEntity<Quarto> cadastrar(@RequestBody @Valid QuartoRequest dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(quartoService.cadastrar(dto));
    }

    @GetMapping
    public ResponseEntity<List<Quarto>> listar(@RequestParam(required = false) SituacaoQuarto situacao) {
        return ResponseEntity.ok(quartoService.listar(situacao));
    }

    @GetMapping("/{numero}")
    public ResponseEntity<Quarto> buscarPorNumero(@PathVariable int numero) {
        return ResponseEntity.ok(quartoService.buscarPorNumero(numero));
    }

    @PutMapping("/{numero}")
    public ResponseEntity<Quarto> atualizar(@PathVariable int numero, @RequestBody @Valid QuartoRequest dto) {
        return ResponseEntity.ok(quartoService.atualizar(numero, dto));
    }

    @DeleteMapping("/{numero}")
    public ResponseEntity<Void> remover(@PathVariable int numero) {
        quartoService.remover(numero);
        return ResponseEntity.noContent().build();
    }
}