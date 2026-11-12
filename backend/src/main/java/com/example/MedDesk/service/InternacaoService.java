package com.example.MedDesk.service;

import com.example.MedDesk.dto.InternacaoRequest;
import com.example.MedDesk.exception.RecursoNaoEncontradoException;
import com.example.MedDesk.exception.RegraNegocioException;
import com.example.MedDesk.model.Internacao;
import com.example.MedDesk.model.Paciente;
import com.example.MedDesk.model.ProfissionalSaude;
import com.example.MedDesk.model.Quarto;
import com.example.MedDesk.repository.AtendimentoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class InternacaoService {

    private final AtendimentoRepository atendimentoRepository;
    private final PacienteService pacienteService;
    private final ProfissionalSaudeService profissionalService;
    private final QuartoService quartoService;

    public InternacaoService(AtendimentoRepository atendimentoRepository,
                             PacienteService pacienteService,
                             ProfissionalSaudeService profissionalService,
                             QuartoService quartoService) {
        this.atendimentoRepository = atendimentoRepository;
        this.pacienteService = pacienteService;
        this.profissionalService = profissionalService;
        this.quartoService = quartoService;
    }

    public Internacao internar(InternacaoRequest dto) {
        Paciente paciente = pacienteService.buscarPorId(dto.pacienteId());
        ProfissionalSaude profissional = profissionalService.buscarPorId(dto.profissionalId());
        Quarto quarto = quartoService.buscarPorNumero(dto.numeroQuarto());

        boolean jaInternado = atendimentoRepository.findAllByTipo(Internacao.class).stream()
                .anyMatch(i -> i.getPaciente().equals(paciente) && i.getDataEfetivaAlta() == null);
        if (jaInternado) {
            throw new RegraNegocioException("O paciente já possui uma internação ativa");
        }

        Internacao internacao = new Internacao(paciente, profissional, quarto, dto.dataPrevistaAlta());
        internacao.validar();
        quarto.adicionarPaciente(paciente);

        if (dto.observacoes() != null && !dto.observacoes().isBlank()) {
            internacao.registrarObservacoes(dto.observacoes());
            paciente.getHistoricoMedico().adicionarInformacaoRelevante(
                    internacao.getData() + " - Internação: " + dto.observacoes().trim());
        }

        atendimentoRepository.save(internacao);
        paciente.getHistoricoMedico().adicionarAtendimento(internacao);
        return internacao;
    }

    public List<Internacao> listar(Boolean ativas) {
        return atendimentoRepository.findAllByTipo(Internacao.class).stream()
                .filter(i -> ativas == null || ativas == (i.getDataEfetivaAlta() == null))
                .toList();
    }

    public Internacao buscarPorId(UUID id) {
        return atendimentoRepository.findById(id)
                .filter(Internacao.class::isInstance)
                .map(Internacao.class::cast)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Internação não encontrada: " + id));
    }

    public Internacao registrarAlta(UUID id) {
        Internacao internacao = buscarPorId(id);
        internacao.registrarAlta();
        return (Internacao) atendimentoRepository.save(internacao);
    }

    public Internacao registrarObservacoes(UUID id, String observacao) {
        Internacao internacao = buscarPorId(id);
        internacao.registrarObservacoes(observacao);
        internacao.getPaciente().getHistoricoMedico()
                .adicionarInformacaoRelevante(internacao.getData() + " - Internação: " + observacao.trim());
        return (Internacao) atendimentoRepository.save(internacao);
    }
}