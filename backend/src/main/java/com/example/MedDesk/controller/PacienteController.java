package com.example.MedDesk.controller;

import com.example.MedDesk.dto.PacienteRequest;
import com.example.MedDesk.model.HistoricoMedico;
import com.example.MedDesk.model.Paciente;
import com.example.MedDesk.service.PacienteService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {

    private final PacienteService pacienteService;

    public PacienteController(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    @PostMapping
    public ResponseEntity<Paciente> cadastrar(@RequestBody @Valid PacienteRequest dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(pacienteService.cadastrar(dto));
    }

    @GetMapping
    public ResponseEntity<List<Paciente>> listar() {
        return ResponseEntity.ok(pacienteService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Paciente> buscarPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(pacienteService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Paciente> atualizar(@PathVariable UUID id, @RequestBody @Valid PacienteRequest dto) {
        return ResponseEntity.ok(pacienteService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable UUID id) {
        pacienteService.remover(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/historico")
    public ResponseEntity<HistoricoMedico> consultarHistorico(@PathVariable UUID id) {
        return ResponseEntity.ok(pacienteService.consultarHistorico(id));
    }
}