package com.example.MedDesk.service;

import com.example.MedDesk.dto.CadastroDto.*;
import com.example.MedDesk.model.*;
import com.example.MedDesk.repository.*;
import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@Transactional(readOnly = true)
public class CadastroService {
    private final PacienteRepository pacientes;
    private final ProfissionalRepository profissionais;
    private final QuartoRepository quartos;
    private final AtendimentoRepository atendimentos;
    private final EntityManager entityManager;

    public CadastroService(PacienteRepository pacientes, ProfissionalRepository profissionais, QuartoRepository quartos,
            AtendimentoRepository atendimentos, EntityManager entityManager) {
        this.pacientes = pacientes;
        this.profissionais = profissionais;
        this.quartos = quartos;
        this.atendimentos = atendimentos;
        this.entityManager = entityManager;
    }

    @Transactional
    public DadosPaciente cadastrar(NovoPaciente dados) {
        Paciente paciente = new Paciente(dados.getNome().strip(), dados.getCpf().strip(), dados.getDataNascimento(),
                dados.getTelefone(), dados.getEndereco(), dados.getEmail());
        return new DadosPaciente(pacientes.save(paciente));
    }

    public ArrayList<DadosPaciente> listarPacientes() {
        ArrayList<DadosPaciente> resultado = new ArrayList<>();
        for (Paciente paciente : pacientes.findAll()) {
            resultado.add(new DadosPaciente(paciente));
        }
        return resultado;
    }

    public DadosPaciente buscarPaciente(Long id) {
        Paciente paciente = pacientes.findById(id).orElse(null);
        if (paciente == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Paciente não encontrado.");
        }
        return new DadosPaciente(paciente);
    }

    @Transactional
    public DadosProfissional cadastrar(NovoProfissional dados) {
        String nome = dados.getNome().strip();
        String registro = dados.getRegistro().strip();
        String telefone = dados.getTelefone().strip();
        String email = dados.getEmail();
        ProfissionalSaude profissional;
        switch (dados.getCategoria()) {
            case CLINICO_GERAL:
                profissional = new ClinicoGeral(nome, registro, telefone, email);
                break;
            case ESPECIALISTA:
                String especialidade = obrigatorio(dados.getEspecialidade(), "Informe a especialidade do profissional.");
                profissional = new Especialista(nome, registro, telefone, email, especialidade);
                break;
            case CIRURGIAO:
                String atuacao = obrigatorio(dados.getAtuacaoCirurgica(), "Informe a atuação cirúrgica do profissional.");
                profissional = new Cirurgiao(nome, registro, telefone, email, atuacao);
                break;
            default:
                throw new IllegalArgumentException("Categoria profissional inválida.");
        }
        return new DadosProfissional(profissionais.save(profissional));
    }

    public ArrayList<DadosProfissional> listarProfissionais(String especialidade, LocalDate data, LocalTime horario) {
        if ((data == null) != (horario == null)) {
            throw new IllegalArgumentException("Informe data e horário juntos para consultar disponibilidade.");
        }

        HashMap<Long, Boolean> ocupados = new HashMap<>();
        if (data != null) {
            for (Long id : atendimentos.profissionaisOcupados(data, horario.withNano(0))) {
                ocupados.put(id, true);
            }
        }

        ArrayList<DadosProfissional> resultado = new ArrayList<>();
        for (ProfissionalSaude profissional : profissionais.findAll()) {
            if (ocupados.containsKey(profissional.getId())) {
                continue;
            }
            if (especialidade != null
                    && (!(profissional instanceof Especialista) || !((Especialista) profissional).atuaEm(especialidade))) {
                continue;
            }
            resultado.add(new DadosProfissional(profissional));
        }
        return resultado;
    }

    public DadosProfissional buscarProfissional(Long id) {
        ProfissionalSaude profissional = profissionais.findById(id).orElse(null);
        if (profissional == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Profissional não encontrado.");
        }
        return new DadosProfissional(profissional);
    }

    @Transactional
    public DadosQuarto cadastrar(NovoQuarto dados) {
        Quarto quarto = new Quarto(dados.getNumeroIdentificacao().strip(), dados.getAndar(),
                dados.getCapacidadeMaximaPacientes(), dados.getTipo().strip());
        entityManager.persist(quarto);
        return new DadosQuarto(quarto);
    }

    public ArrayList<DadosQuarto> listarQuartos(String tipo, Boolean disponivel) {
        ArrayList<DadosQuarto> resultado = new ArrayList<>();
        for (Quarto quarto : quartos.findAll()) {
            if (tipo != null && !quarto.getTipo().equalsIgnoreCase(tipo.strip())) {
                continue;
            }
            if (disponivel != null && quarto.verificarDisponibilidade() != disponivel) {
                continue;
            }
            resultado.add(new DadosQuarto(quarto));
        }
        return resultado;
    }

    public DadosQuarto buscarQuarto(String numero) {
        Quarto quarto = quartos.findById(numero).orElse(null);
        if (quarto == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Quarto não encontrado.");
        }
        return new DadosQuarto(quarto);
    }

    private String obrigatorio(String valor, String mensagem) {
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException(mensagem);
        }
        return valor.strip();
    }
}
