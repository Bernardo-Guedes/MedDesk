package com.example.MedDesk.service;

import com.example.MedDesk.dto.EmergenciaRequest;
import com.example.MedDesk.exception.RecursoNaoEncontradoException;
import com.example.MedDesk.model.Emergencia;
import com.example.MedDesk.model.Paciente;
import com.example.MedDesk.model.ProfissionalSaude;
import com.example.MedDesk.model.enums.StatusEmergencia;
import com.example.MedDesk.repository.AtendimentoRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Service
public class EmergenciaService {

    private final AtendimentoRepository atendimentoRepository;
    private final PacienteService pacienteService;
    private final ProfissionalSaudeService profissionalService;

    public EmergenciaService(AtendimentoRepository atendimentoRepository,
                             PacienteService pacienteService,
                             ProfissionalSaudeService profissionalService) {
        this.atendimentoRepository = atendimentoRepository;
        this.pacienteService = pacienteService;
        this.profissionalService = profissionalService;
    }

    public Emergencia registrar(EmergenciaRequest dto) {
        Paciente paciente = pacienteService.buscarPorId(dto.pacienteId());
        ProfissionalSaude profissional = profissionalService.buscarPorId(dto.profissionalId());

        Emergencia emergencia = new Emergencia(paciente, profissional, dto.prioridade());
        emergencia.validar();

        atendimentoRepository.save(emergencia);
        paciente.getHistoricoMedico().adicionarAtendimento(emergencia);
        return emergencia;
    }

    public List<Emergencia> listar(StatusEmergencia status) {
        return atendimentoRepository.findAllByTipo(Emergencia.class).stream()
                .filter(e -> status == null || e.getStatusEmergencia() == status)
                .sorted(Comparator.comparing(Emergencia::getPrioridade).reversed()
                        .thenComparing(Emergencia::getData)
                        .thenComparing(Emergencia::getHorario))
                .toList();
    }

    public Emergencia buscarPorId(UUID id) {
        return atendimentoRepository.findById(id)
                .filter(Emergencia.class::isInstance)
                .map(Emergencia.class::cast)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Atendimento de emergência não encontrado: " + id));
    }

    public Emergencia iniciar(UUID id) {
        Emergencia emergencia = buscarPorId(id);
        emergencia.iniciar();
        return (Emergencia) atendimentoRepository.save(emergencia);
    }

    public Emergencia concluir(UUID id) {
        Emergencia emergencia = buscarPorId(id);
        emergencia.concluir();
        return (Emergencia) atendimentoRepository.save(emergencia);
    }

    public Emergencia registrarObservacoes(UUID id, String observacao) {
        Emergencia emergencia = buscarPorId(id);
        emergencia.registrarObservacoes(observacao);
        emergencia.getPaciente().getHistoricoMedico()
                .adicionarInformacaoRelevante(emergencia.getData() + " - Emergência: " + observacao.trim());
        return (Emergencia) atendimentoRepository.save(emergencia);
    }
}