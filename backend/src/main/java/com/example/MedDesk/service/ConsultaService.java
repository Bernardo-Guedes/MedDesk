package com.example.MedDesk.service;

import com.example.MedDesk.dto.ConsultaEspecializadaRequest;
import com.example.MedDesk.dto.ConsultaRotinaRequest;
import com.example.MedDesk.dto.EncaminhamentoRequest;
import com.example.MedDesk.exception.RecursoNaoEncontradoException;
import com.example.MedDesk.exception.RegraNegocioException;
import com.example.MedDesk.model.ClinicoGeral;
import com.example.MedDesk.model.Consulta;
import com.example.MedDesk.model.ConsultaEspecializada;
import com.example.MedDesk.model.ConsultaRotina;
import com.example.MedDesk.model.ConsultavelPorEspecialidade;
import com.example.MedDesk.model.Especialista;
import com.example.MedDesk.model.Paciente;
import com.example.MedDesk.model.ProfissionalSaude;
import com.example.MedDesk.model.enums.Especialidade;
import com.example.MedDesk.model.enums.StatusConsulta;
import com.example.MedDesk.repository.AtendimentoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

@Service
public class ConsultaService implements ConsultavelPorEspecialidade {

    private final AtendimentoRepository atendimentoRepository;
    private final PacienteService pacienteService;
    private final ProfissionalSaudeService profissionalService;

    public ConsultaService(AtendimentoRepository atendimentoRepository,
                           PacienteService pacienteService,
                           ProfissionalSaudeService profissionalService) {
        this.atendimentoRepository = atendimentoRepository;
        this.pacienteService = pacienteService;
        this.profissionalService = profissionalService;
    }


    public Consulta agendarRotina(ConsultaRotinaRequest dto) {
        Paciente paciente = pacienteService.buscarPorId(dto.pacienteId());
        ProfissionalSaude profissional = profissionalService.buscarPorId(dto.profissionalId());

        ConsultaRotina consulta = new ConsultaRotina(dto.motivo());
        consulta.agendar(paciente, profissional, dto.data(), dto.horario()); // valida as regras da modalidade
        profissionalService.verificarDisponibilidade(profissional, dto.data(), dto.horario());
        return salvarNovaConsulta(consulta);
    }

    public Consulta agendarEspecializada(ConsultaEspecializadaRequest dto) {
        Paciente paciente = pacienteService.buscarPorId(dto.pacienteId());
        ProfissionalSaude profissional = profissionalService.buscarPorId(dto.profissionalId());

        ConsultaEspecializada consulta = new ConsultaEspecializada(dto.especialidade());
        consulta.agendar(paciente, profissional, dto.data(), dto.horario());
        profissionalService.verificarDisponibilidade(profissional, dto.data(), dto.horario());
        return salvarNovaConsulta(consulta);
    }

    public Consulta encaminhar(EncaminhamentoRequest dto) {
        if (!(profissionalService.buscarPorId(dto.clinicoGeralId()) instanceof ClinicoGeral clinico)) {
            throw new RegraNegocioException("Apenas um clínico geral pode realizar encaminhamentos");
        }
        if (!(profissionalService.buscarPorId(dto.especialistaId()) instanceof Especialista especialista)) {
            throw new RegraNegocioException("O encaminhamento deve ser feito para um especialista");
        }
        Paciente paciente = pacienteService.buscarPorId(dto.pacienteId());

        ConsultaEspecializada consulta = clinico.encaminhar(paciente, especialista, dto.data(), dto.horario());
        profissionalService.verificarDisponibilidade(especialista, dto.data(), dto.horario());
        return salvarNovaConsulta(consulta);
    }


    public List<Consulta> listar(StatusConsulta status) {
        return atendimentoRepository.findAllByTipo(Consulta.class).stream()
                .filter(c -> status == null || c.getStatusConsulta() == status)
                .toList();
    }

    public Consulta buscarPorId(UUID id) {
        return atendimentoRepository.findById(id)
                .filter(Consulta.class::isInstance)
                .map(Consulta.class::cast)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Consulta não encontrada: " + id));
    }

    @Override
    public List<ProfissionalSaude> consultarProfissionaisDisponiveis(Especialidade especialidade,
                                                                     LocalDate data, LocalTime horario) {
        return profissionalService.listar(especialidade).stream()
                .filter(p -> profissionalService.estaDisponivel(p, data, horario))
                .toList();
    }


    public Consulta confirmar(UUID id) { return alterarSituacao(id, Consulta::confirmar); }
    public Consulta iniciar(UUID id) { return alterarSituacao(id, Consulta::iniciar); }
    public Consulta concluir(UUID id) { return alterarSituacao(id, Consulta::concluir); }
    public Consulta cancelar(UUID id) { return alterarSituacao(id, Consulta::cancelar); }
    public Consulta informarAusencia(UUID id) { return alterarSituacao(id, Consulta::informarAusencia); }

    public Consulta registrarObservacoes(UUID id, String observacao) {
        Consulta consulta = buscarPorId(id);
        consulta.registrarObservacoes(observacao);
        consulta.getPaciente().getHistoricoMedico()
                .adicionarInformacaoRelevante(consulta.getData() + " - Consulta: " + observacao.trim());
        return (Consulta) atendimentoRepository.save(consulta);
    }


    private Consulta alterarSituacao(UUID id, Consumer<Consulta> acao) {
        Consulta consulta = buscarPorId(id);
        acao.accept(consulta);
        return (Consulta) atendimentoRepository.save(consulta);
    }

    private Consulta salvarNovaConsulta(Consulta consulta) {
        atendimentoRepository.save(consulta);
        consulta.getPaciente().getHistoricoMedico().adicionarAtendimento(consulta);
        return consulta;
    }
}