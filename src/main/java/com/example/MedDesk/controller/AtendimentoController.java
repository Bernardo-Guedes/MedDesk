package com.example.MedDesk.controller;

import com.example.MedDesk.dto.AtendimentoDto.*;
import com.example.MedDesk.model.Situacao;
import com.example.MedDesk.service.AtendimentoService;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AtendimentoController {
    private final AtendimentoService atendimentos;

    public AtendimentoController(AtendimentoService atendimentos) {
        this.atendimentos = atendimentos;
    }

    @GetMapping("/consultas")
    public ArrayList<DadosAtendimento> listarConsultas(@RequestParam(required = false) Situacao situacao) {
        return atendimentos.listarConsultas(situacao);
    }

    @PostMapping(path = "/consultas", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DadosAtendimento> criarConsulta(@Valid @RequestBody NovaConsulta dados) {
        return criado("/consultas", atendimentos.criarConsulta(dados));
    }

    @GetMapping("/consultas/{id}")
    public DadosAtendimento detalharConsulta(@PathVariable String id) {
        return atendimentos.detalhar(id, false);
    }

    @PatchMapping(path = "/consultas/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public DadosAtendimento alterarConsulta(@PathVariable String id, @Valid @RequestBody Alteracao dados) {
        return atendimentos.alterar(id, false, dados);
    }

    @PostMapping(path = "/consultas/{id}/encaminhamento", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DadosAtendimento> encaminhar(@PathVariable String id,
                                                        @Valid @RequestBody NovoEncaminhamento dados) {
        return criado("/consultas", atendimentos.encaminhar(id, dados));
    }

    @GetMapping("/internacoes")
    public ArrayList<DadosAtendimento> listarInternacoes(@RequestParam(required = false) Situacao situacao) {
        return atendimentos.listarInternacoes(situacao);
    }

    @PostMapping(path = "/internacoes", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DadosAtendimento> criarInternacao(@Valid @RequestBody NovaInternacao dados) {
        return criado("/internacoes", atendimentos.criarInternacao(dados));
    }

    @GetMapping("/internacoes/{id}")
    public DadosAtendimento detalharInternacao(@PathVariable String id) {
        return atendimentos.detalhar(id, true);
    }

    @PatchMapping(path = "/internacoes/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public DadosAtendimento alterarInternacao(@PathVariable String id, @Valid @RequestBody Alteracao dados) {
        return atendimentos.alterar(id, true, dados);
    }

    private ResponseEntity<DadosAtendimento> criado(String caminho, DadosAtendimento dados) {
        return ResponseEntity.created(URI.create(caminho + "/" + dados.getId())).body(dados);
    }
}
