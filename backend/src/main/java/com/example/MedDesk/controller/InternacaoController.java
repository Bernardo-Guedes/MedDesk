package com.example.MedDesk.controller;

import com.example.MedDesk.dto.InternacaoRequest;
import com.example.MedDesk.dto.ObservacaoRequest;
import com.example.MedDesk.model.Internacao;
import com.example.MedDesk.service.InternacaoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/internacoes")
public class InternacaoController {

    private final InternacaoService internacaoService;

    public InternacaoController(InternacaoService internacaoService) {
        this.internacaoService = internacaoService;
    }

    @PostMapping
    public ResponseEntity<Internacao> internar(@RequestBody @Valid InternacaoRequest dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(internacaoService.internar(dto));
    }

    @GetMapping
    public ResponseEntity<List<Internacao>> listar(@RequestParam(required = false) Boolean ativas) {
        return ResponseEntity.ok(internacaoService.listar(ativas));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Internacao> buscarPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(internacaoService.buscarPorId(id));
    }

    @PatchMapping("/{id}/alta")
    public ResponseEntity<Internacao> registrarAlta(@PathVariable UUID id) {
        return ResponseEntity.ok(internacaoService.registrarAlta(id));
    }

    @PatchMapping("/{id}/observacoes")
    public ResponseEntity<Internacao> registrarObservacoes(@PathVariable UUID id, @RequestBody @Valid ObservacaoRequest dto) {
        return ResponseEntity.ok(internacaoService.registrarObservacoes(id, dto.observacao()));
    }
}