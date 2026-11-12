package com.example.MedDesk.service;

import com.example.MedDesk.dto.ProcedimentoCirurgicoRequest;
import com.example.MedDesk.exception.RecursoNaoEncontradoException;
import com.example.MedDesk.exception.RegraNegocioException;
import com.example.MedDesk.model.Cirurgiao;
import com.example.MedDesk.model.Paciente;
import com.example.MedDesk.model.ProcedimentoCirurgico;
import com.example.MedDesk.model.enums.StatusProcedimentoCirurgico;
import com.example.MedDesk.repository.AtendimentoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

@Service
public class ProcedimentoCirurgicoService {

    private final AtendimentoRepository atendimentoRepository;
    private final PacienteService pacienteService;
    private final ProfissionalSaudeService profissionalService;

    public ProcedimentoCirurgicoService(AtendimentoRepository atendimentoRepository,
                                        PacienteService pacienteService,
                                        ProfissionalSaudeService profissionalService) {
        this.atendimentoRepository = atendimentoRepository;
        this.pacienteService = pacienteService;
        this.profissionalService = profissionalService;
    }

    public ProcedimentoCirurgico agendar(ProcedimentoCirurgicoRequest dto) {
        Paciente paciente = pacienteService.buscarPorId(dto.pacienteId());
        if (!(profissionalService.buscarPorId(dto.cirurgiaoId()) instanceof Cirurgiao cirurgiao)) {
            throw new RegraNegocioException("Procedimentos cirúrgicos devem ser realizados por um cirurgião");
        }

        ProcedimentoCirurgico procedimento = new ProcedimentoCirurgico(dto.descricao(), dto.tipo());
        procedimento.agendar(paciente, cirurgiao, dto.data(), dto.horario());
        profissionalService.verificarDisponibilidade(cirurgiao, dto.data(), dto.horario());

        atendimentoRepository.save(procedimento);
        cirurgiao.adicionarProcedimentoCirurgico(procedimento);
        paciente.getHistoricoMedico().adicionarAtendimento(procedimento);
        return procedimento;
    }

    public List<ProcedimentoCirurgico> listar(StatusProcedimentoCirurgico status) {
        return atendimentoRepository.findAllByTipo(ProcedimentoCirurgico.class).stream()
                .filter(p -> status == null || p.getStatusProcedimentoCirurgico() == status)
                .toList();
    }

    public ProcedimentoCirurgico buscarPorId(UUID id) {
        return atendimentoRepository.findById(id)
                .filter(ProcedimentoCirurgico.class::isInstance)
                .map(ProcedimentoCirurgico.class::cast)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Procedimento cirúrgico não encontrado: " + id));
    }

    public ProcedimentoCirurgico iniciar(UUID id) { return alterar(id, ProcedimentoCirurgico::iniciar); }
    public ProcedimentoCirurgico concluir(UUID id) { return alterar(id, ProcedimentoCirurgico::concluir); }
    public ProcedimentoCirurgico cancelar(UUID id) { return alterar(id, ProcedimentoCirurgico::cancelar); }

    public ProcedimentoCirurgico registrarObservacaoPre(UUID id, String observacao) {
        ProcedimentoCirurgico procedimento = alterar(id, p -> p.registrarObservacaoPre(observacao));
        registrarNoHistorico(procedimento, "Pré-operatório: " + observacao.trim());
        return procedimento;
    }

    public ProcedimentoCirurgico registrarObservacaoPos(UUID id, String observacao) {
        ProcedimentoCirurgico procedimento = alterar(id, p -> p.registrarObservacaoPos(observacao));
        registrarNoHistorico(procedimento, "Pós-operatório: " + observacao.trim());
        return procedimento;
    }

    private ProcedimentoCirurgico alterar(UUID id, Consumer<ProcedimentoCirurgico> acao) {
        ProcedimentoCirurgico procedimento = buscarPorId(id);
        acao.accept(procedimento);
        return (ProcedimentoCirurgico) atendimentoRepository.save(procedimento);
    }

    private void registrarNoHistorico(ProcedimentoCirurgico procedimento, String texto) {
        procedimento.getPaciente().getHistoricoMedico().adicionarInformacaoRelevante(
                procedimento.getData() + " - Cirurgia (" + procedimento.getDescricao() + ") " + texto);
    }
}