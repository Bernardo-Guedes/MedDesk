package com.example.MedDesk.controller;

import com.example.MedDesk.dto.ConsultaEspecializadaRequest;
import com.example.MedDesk.dto.ConsultaRotinaRequest;
import com.example.MedDesk.dto.EncaminhamentoRequest;
import com.example.MedDesk.dto.ObservacaoRequest;
import com.example.MedDesk.model.Consulta;
import com.example.MedDesk.model.enums.StatusConsulta;
import com.example.MedDesk.service.ConsultaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/consultas")
public class ConsultaController {

    private final ConsultaService consultaService;

    public ConsultaController(ConsultaService consultaService) {
        this.consultaService = consultaService;
    }


    @PostMapping("/rotina")
    public ResponseEntity<Consulta> agendarRotina(@RequestBody @Valid ConsultaRotinaRequest dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(consultaService.agendarRotina(dto));
    }

    @PostMapping("/especializada")
    public ResponseEntity<Consulta> agendarEspecializada(@RequestBody @Valid ConsultaEspecializadaRequest dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(consultaService.agendarEspecializada(dto));
    }

    @PostMapping("/encaminhamentos")
    public ResponseEntity<Consulta> encaminhar(@RequestBody @Valid EncaminhamentoRequest dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(consultaService.encaminhar(dto));
    }


    @GetMapping
    public ResponseEntity<List<Consulta>> listar(@RequestParam(required = false) StatusConsulta status) {
        return ResponseEntity.ok(consultaService.listar(status));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Consulta> buscarPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(consultaService.buscarPorId(id));
    }


    @PatchMapping("/{id}/confirmar")
    public ResponseEntity<Consulta> confirmar(@PathVariable UUID id) {
        return ResponseEntity.ok(consultaService.confirmar(id));
    }

    @PatchMapping("/{id}/iniciar")
    public ResponseEntity<Consulta> iniciar(@PathVariable UUID id) {
        return ResponseEntity.ok(consultaService.iniciar(id));
    }

    @PatchMapping("/{id}/concluir")
    public ResponseEntity<Consulta> concluir(@PathVariable UUID id) {
        return ResponseEntity.ok(consultaService.concluir(id));
    }

    @PatchMapping("/{id}/cancelar")
    public ResponseEntity<Consulta> cancelar(@PathVariable UUID id) {
        return ResponseEntity.ok(consultaService.cancelar(id));
    }

    @PatchMapping("/{id}/ausencia")
    public ResponseEntity<Consulta> informarAusencia(@PathVariable UUID id) {
        return ResponseEntity.ok(consultaService.informarAusencia(id));
    }

    @PatchMapping("/{id}/observacoes")
    public ResponseEntity<Consulta> registrarObservacoes(@PathVariable UUID id, @RequestBody @Valid ObservacaoRequest dto) {
        return ResponseEntity.ok(consultaService.registrarObservacoes(id, dto.observacao()));
    }
}