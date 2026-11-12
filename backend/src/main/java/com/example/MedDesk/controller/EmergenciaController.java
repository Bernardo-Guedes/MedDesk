package com.example.MedDesk.controller;

import com.example.MedDesk.dto.EmergenciaRequest;
import com.example.MedDesk.dto.ObservacaoRequest;
import com.example.MedDesk.model.Emergencia;
import com.example.MedDesk.model.enums.StatusEmergencia;
import com.example.MedDesk.service.EmergenciaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/emergencias")
public class EmergenciaController {

    private final EmergenciaService emergenciaService;

    public EmergenciaController(EmergenciaService emergenciaService) {
        this.emergenciaService = emergenciaService;
    }

    @PostMapping
    public ResponseEntity<Emergencia> registrar(@RequestBody @Valid EmergenciaRequest dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(emergenciaService.registrar(dto));
    }

    @GetMapping
    public ResponseEntity<List<Emergencia>> listar(@RequestParam(required = false) StatusEmergencia status) {
        return ResponseEntity.ok(emergenciaService.listar(status));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Emergencia> buscarPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(emergenciaService.buscarPorId(id));
    }

    @PatchMapping("/{id}/iniciar")
    public ResponseEntity<Emergencia> iniciar(@PathVariable UUID id) {
        return ResponseEntity.ok(emergenciaService.iniciar(id));
    }

    @PatchMapping("/{id}/concluir")
    public ResponseEntity<Emergencia> concluir(@PathVariable UUID id) {
        return ResponseEntity.ok(emergenciaService.concluir(id));
    }

    @PatchMapping("/{id}/observacoes")
    public ResponseEntity<Emergencia> registrarObservacoes(@PathVariable UUID id, @RequestBody @Valid ObservacaoRequest dto) {
        return ResponseEntity.ok(emergenciaService.registrarObservacoes(id, dto.observacao()));
    }
}