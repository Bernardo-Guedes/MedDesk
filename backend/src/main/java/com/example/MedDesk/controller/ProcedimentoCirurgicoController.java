package com.example.MedDesk.controller;

import com.example.MedDesk.dto.ObservacaoRequest;
import com.example.MedDesk.dto.ProcedimentoCirurgicoRequest;
import com.example.MedDesk.model.ProcedimentoCirurgico;
import com.example.MedDesk.model.enums.StatusProcedimentoCirurgico;
import com.example.MedDesk.service.ProcedimentoCirurgicoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/procedimentos")
public class ProcedimentoCirurgicoController {

    private final ProcedimentoCirurgicoService procedimentoService;

    public ProcedimentoCirurgicoController(ProcedimentoCirurgicoService procedimentoService) {
        this.procedimentoService = procedimentoService;
    }

    @PostMapping
    public ResponseEntity<ProcedimentoCirurgico> agendar(@RequestBody @Valid ProcedimentoCirurgicoRequest dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(procedimentoService.agendar(dto));
    }

    @GetMapping
    public ResponseEntity<List<ProcedimentoCirurgico>> listar(
            @RequestParam(required = false) StatusProcedimentoCirurgico status) {
        return ResponseEntity.ok(procedimentoService.listar(status));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProcedimentoCirurgico> buscarPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(procedimentoService.buscarPorId(id));
    }

    @PatchMapping("/{id}/iniciar")
    public ResponseEntity<ProcedimentoCirurgico> iniciar(@PathVariable UUID id) {
        return ResponseEntity.ok(procedimentoService.iniciar(id));
    }

    @PatchMapping("/{id}/concluir")
    public ResponseEntity<ProcedimentoCirurgico> concluir(@PathVariable UUID id) {
        return ResponseEntity.ok(procedimentoService.concluir(id));
    }

    @PatchMapping("/{id}/cancelar")
    public ResponseEntity<ProcedimentoCirurgico> cancelar(@PathVariable UUID id) {
        return ResponseEntity.ok(procedimentoService.cancelar(id));
    }

    @PatchMapping("/{id}/observacoes-pre")
    public ResponseEntity<ProcedimentoCirurgico> registrarObservacaoPre(@PathVariable UUID id,
                                                                        @RequestBody @Valid ObservacaoRequest dto) {
        return ResponseEntity.ok(procedimentoService.registrarObservacaoPre(id, dto.observacao()));
    }

    @PatchMapping("/{id}/observacoes-pos")
    public ResponseEntity<ProcedimentoCirurgico> registrarObservacaoPos(@PathVariable UUID id,
                                                                        @RequestBody @Valid ObservacaoRequest dto) {
        return ResponseEntity.ok(procedimentoService.registrarObservacaoPos(id, dto.observacao()));
    }
}