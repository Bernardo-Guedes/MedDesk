package com.example.MedDesk.service;

import com.example.MedDesk.dto.PacienteRequest;
import com.example.MedDesk.exception.RecursoNaoEncontradoException;
import com.example.MedDesk.exception.RegraNegocioException;
import com.example.MedDesk.model.HistoricoMedico;
import com.example.MedDesk.model.Paciente;
import com.example.MedDesk.repository.PacienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PacienteService {

    private final PacienteRepository pacienteRepository;

    public PacienteService(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    public Paciente cadastrar(PacienteRequest dto) {
        Paciente paciente = new Paciente(dto.nome(), dto.cpf(), dto.dataNascimento(),
                dto.telefone(), dto.endereco(), dto.email());
        verificarCpfUnico(paciente.getCpf(), null);
        return pacienteRepository.save(paciente);
    }

    public List<Paciente> listar() {
        return pacienteRepository.findAll();
    }

    public Paciente buscarPorId(UUID id) {
        return pacienteRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Paciente não encontrado: " + id));
    }

    public Paciente atualizar(UUID id, PacienteRequest dto) {
        Paciente paciente = buscarPorId(id);
        String cpfNormalizado = dto.cpf() == null ? "" : dto.cpf().replaceAll("\\D", "");
        verificarCpfUnico(cpfNormalizado, id);

        paciente.setNome(dto.nome());
        paciente.setCpf(dto.cpf());
        paciente.setDataNascimento(dto.dataNascimento());
        paciente.setTelefone(dto.telefone());
        paciente.setEndereco(dto.endereco());
        paciente.setEmail(dto.email());
        return pacienteRepository.save(paciente);
    }

    public void remover(UUID id) {
        Paciente paciente = buscarPorId(id);
        if (!paciente.getHistoricoMedico().consultarHistorico().isEmpty()) {
            throw new RegraNegocioException("Não é possível remover um paciente que possui atendimentos no histórico");
        }
        pacienteRepository.deleteById(id);
    }

    public HistoricoMedico consultarHistorico(UUID id) {
        return buscarPorId(id).getHistoricoMedico();
    }

    private void verificarCpfUnico(String cpf, UUID idIgnorado) {
        pacienteRepository.findByCpf(cpf).ifPresent(existente -> {
            if (!existente.getId().equals(idIgnorado)) {
                throw new RegraNegocioException("Já existe um paciente cadastrado com o CPF " + cpf);
            }
        });
    }
}