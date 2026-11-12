package com.example.MedDesk.controller;

import com.example.MedDesk.dto.ProfissionalRequest;
import com.example.MedDesk.model.ProcedimentoCirurgico;
import com.example.MedDesk.model.ProfissionalSaude;
import com.example.MedDesk.model.enums.Especialidade;
import com.example.MedDesk.service.ConsultaService;
import com.example.MedDesk.service.ProfissionalSaudeService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/profissionais")
public class ProfissionalSaudeController {

    private final ProfissionalSaudeService profissionalService;
    private final ConsultaService consultaService;

    public ProfissionalSaudeController(ProfissionalSaudeService profissionalService,
                                       ConsultaService consultaService) {
        this.profissionalService = profissionalService;
        this.consultaService = consultaService;
    }

    @PostMapping
    public ResponseEntity<ProfissionalSaude> cadastrar(@RequestBody @Valid ProfissionalRequest dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(profissionalService.cadastrar(dto));
    }

    @GetMapping
    public ResponseEntity<List<ProfissionalSaude>> listar(@RequestParam(required = false) Especialidade especialidade) {
        return ResponseEntity.ok(profissionalService.listar(especialidade));
    }

    @GetMapping("/disponiveis")
    public ResponseEntity<List<ProfissionalSaude>> listarDisponiveis(
            @RequestParam Especialidade especialidade,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data,
            @RequestParam @DateTimeFormat(pattern = "HH:mm") LocalTime horario) {
        return ResponseEntity.ok(consultaService.consultarProfissionaisDisponiveis(especialidade, data, horario));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfissionalSaude> buscarPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(profissionalService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProfissionalSaude> atualizar(@PathVariable UUID id, @RequestBody @Valid ProfissionalRequest dto) {
        return ResponseEntity.ok(profissionalService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable UUID id) {
        profissionalService.remover(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/procedimentos")
    public ResponseEntity<List<ProcedimentoCirurgico>> listarProcedimentos(@PathVariable UUID id) {
        return ResponseEntity.ok(profissionalService.listarProcedimentos(id));
    }
}