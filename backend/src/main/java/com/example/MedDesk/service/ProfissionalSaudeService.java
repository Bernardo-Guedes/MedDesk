package com.example.MedDesk.service;

import com.example.MedDesk.dto.ProfissionalRequest;
import com.example.MedDesk.exception.RecursoNaoEncontradoException;
import com.example.MedDesk.exception.RegraNegocioException;
import com.example.MedDesk.model.Atendimento;
import com.example.MedDesk.model.Cirurgiao;
import com.example.MedDesk.model.ClinicoGeral;
import com.example.MedDesk.model.Consulta;
import com.example.MedDesk.model.Especialista;
import com.example.MedDesk.model.ProcedimentoCirurgico;
import com.example.MedDesk.model.ProfissionalSaude;
import com.example.MedDesk.model.enums.Especialidade;
import com.example.MedDesk.model.enums.StatusConsulta;
import com.example.MedDesk.model.enums.StatusProcedimentoCirurgico;
import com.example.MedDesk.repository.AtendimentoRepository;
import com.example.MedDesk.repository.ProfissionalSaudeRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Service
public class ProfissionalSaudeService {

    private final ProfissionalSaudeRepository profissionalRepository;
    private final AtendimentoRepository atendimentoRepository;

    public ProfissionalSaudeService(ProfissionalSaudeRepository profissionalRepository,
                                    AtendimentoRepository atendimentoRepository) {
        this.profissionalRepository = profissionalRepository;
        this.atendimentoRepository = atendimentoRepository;
    }

    public ProfissionalSaude cadastrar(ProfissionalRequest dto) {
        verificarRegistroUnico(dto.registroProfissional(), null);

        ProfissionalSaude profissional = switch (dto.categoria()) {
            case "CLINICO_GERAL" -> new ClinicoGeral(dto.nome(), dto.registroProfissional(),
                    dto.telefone(), dto.email());
            case "ESPECIALISTA" -> new Especialista(dto.nome(), dto.registroProfissional(),
                    dto.telefone(), dto.email(), dto.especialidade());
            case "CIRURGIAO" -> new Cirurgiao(dto.nome(), dto.registroProfissional(),
                    dto.telefone(), dto.email(), dto.atuacaoCirurgica());
            default -> throw new RegraNegocioException("Categoria de profissional inválida: " + dto.categoria());
        };
        return profissionalRepository.save(profissional);
    }

    public List<ProfissionalSaude> listar(Especialidade especialidade) {
        if (especialidade == null) {
            return profissionalRepository.findAll();
        }
        return profissionalRepository.findAll().stream()
                .filter(p -> p instanceof Especialista e && e.getEspecialidade() == especialidade)
                .toList();
    }

    public ProfissionalSaude buscarPorId(UUID id) {
        return profissionalRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Profissional não encontrado: " + id));
    }

    public ProfissionalSaude atualizar(UUID id, ProfissionalRequest dto) {
        ProfissionalSaude profissional = buscarPorId(id);
        verificarRegistroUnico(dto.registroProfissional(), id);

        profissional.setNome(dto.nome());
        profissional.setRegistroProfissional(dto.registroProfissional());
        profissional.setTelefone(dto.telefone());
        profissional.setEmail(dto.email());

        if (profissional instanceof Especialista especialista && dto.especialidade() != null
                && dto.especialidade() != especialista.getEspecialidade()) {
            if (!atendimentoRepository.findByProfissionalId(id).isEmpty()) {
                throw new RegraNegocioException("Não é possível alterar a especialidade de um profissional que já possui atendimentos");
            }
            especialista.setEspecialidade(dto.especialidade());
        }
        if (profissional instanceof Cirurgiao cirurgiao && dto.atuacaoCirurgica() != null) {
            cirurgiao.setAtuacaoCirurgica(dto.atuacaoCirurgica());
        }
        return profissionalRepository.save(profissional);
    }

    public void remover(UUID id) {
        buscarPorId(id);
        if (!atendimentoRepository.findByProfissionalId(id).isEmpty()) {
            throw new RegraNegocioException("Não é possível remover um profissional que possui atendimentos registrados");
        }
        profissionalRepository.deleteById(id);
    }

    public List<ProcedimentoCirurgico> listarProcedimentos(UUID cirurgiaoId) {
        if (!(buscarPorId(cirurgiaoId) instanceof Cirurgiao cirurgiao)) {
            throw new RegraNegocioException("O profissional informado não é um cirurgião");
        }
        return cirurgiao.getProcedimentosCirurgicos();
    }

    public boolean estaDisponivel(ProfissionalSaude profissional, LocalDate data, LocalTime horario) {
        return atendimentoRepository.findByProfissionalId(profissional.getId()).stream()
                .filter(this::ocupaAgenda)
                .noneMatch(a -> a.getData().equals(data) && a.getHorario().equals(horario));
    }

    public void verificarDisponibilidade(ProfissionalSaude profissional, LocalDate data, LocalTime horario) {
        if (!estaDisponivel(profissional, data, horario)) {
            throw new RegraNegocioException("O profissional " + profissional.getNome()
                    + " já possui um atendimento agendado em " + data + " às " + horario);
        }
    }

    private boolean ocupaAgenda(Atendimento atendimento) {
        if (atendimento instanceof Consulta consulta) {
            return consulta.getStatusConsulta() != StatusConsulta.CANCELADA
                    && consulta.getStatusConsulta() != StatusConsulta.NAO_COMPARECEU;
        }
        if (atendimento instanceof ProcedimentoCirurgico procedimento) {
            return procedimento.getStatusProcedimentoCirurgico() != StatusProcedimentoCirurgico.CANCELADO;
        }
        return false;
    }

    private void verificarRegistroUnico(String registro, UUID idIgnorado) {
        if (registro == null) {
            return;
        }
        profissionalRepository.findByRegistroProfissional(registro.trim()).ifPresent(existente -> {
            if (!existente.getId().equals(idIgnorado)) {
                throw new RegraNegocioException("Já existe um profissional com o registro " + registro);
            }
        });
    }
}