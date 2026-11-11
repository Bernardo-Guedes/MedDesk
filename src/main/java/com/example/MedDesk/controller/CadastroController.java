package com.example.MedDesk.controller;

import com.example.MedDesk.dto.AtendimentoDto.DadosAtendimento;
import com.example.MedDesk.dto.CadastroDto.*;
import com.example.MedDesk.service.AtendimentoService;
import com.example.MedDesk.service.CadastroService;
import jakarta.validation.Valid;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class CadastroController {
    private final CadastroService cadastro;
    private final AtendimentoService atendimentos;

    public CadastroController(CadastroService cadastro, AtendimentoService atendimentos) {
        this.cadastro = cadastro;
        this.atendimentos = atendimentos;
    }

    @GetMapping("/pacientes")
    public ArrayList<DadosPaciente> listarPacientes() {
        return cadastro.listarPacientes();
    }

    @PostMapping(path = "/pacientes", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DadosPaciente> cadastrarPaciente(@Valid @RequestBody NovoPaciente dados) {
        DadosPaciente paciente = cadastro.cadastrar(dados);
        return criado(paciente.getId().toString(), paciente);
    }

    @GetMapping("/pacientes/{id}")
    public DadosPaciente buscarPaciente(@PathVariable Long id) {
        return cadastro.buscarPaciente(id);
    }

    @GetMapping("/pacientes/{id}/historico")
    public ArrayList<DadosAtendimento> historico(@PathVariable Long id) {
        return atendimentos.historico(id);
    }

    @GetMapping("/profissionais")
    public ArrayList<DadosProfissional> listarProfissionais(
            @RequestParam(required = false) String especialidade,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime horario) {
        return cadastro.listarProfissionais(especialidade, data, horario);
    }

    @PostMapping(path = "/profissionais", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DadosProfissional> cadastrarProfissional(@Valid @RequestBody NovoProfissional dados) {
        DadosProfissional profissional = cadastro.cadastrar(dados);
        return criado(profissional.getId().toString(), profissional);
    }

    @GetMapping("/profissionais/{id}")
    public DadosProfissional buscarProfissional(@PathVariable Long id) {
        return cadastro.buscarProfissional(id);
    }

    @GetMapping("/quartos")
    public ArrayList<DadosQuarto> listarQuartos(@RequestParam(required = false) String tipo,
                                          @RequestParam(required = false) Boolean disponivel) {
        return cadastro.listarQuartos(tipo, disponivel);
    }

    @PostMapping(path = "/quartos", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DadosQuarto> cadastrarQuarto(@Valid @RequestBody NovoQuarto dados) {
        DadosQuarto quarto = cadastro.cadastrar(dados);
        return criado(quarto.getNumeroIdentificacao(), quarto);
    }

    @GetMapping("/quartos/{numero}")
    public DadosQuarto buscarQuarto(@PathVariable String numero) {
        return cadastro.buscarQuarto(numero);
    }

    private <T> ResponseEntity<T> criado(String id, T corpo) {
        return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequestUri()
                .pathSegment(id).build().encode().toUri()).body(corpo);
    }
}
