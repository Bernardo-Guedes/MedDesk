package com.example.MedDesk.service;

import com.example.MedDesk.dto.AtendimentoDto.*;
import com.example.MedDesk.model.*;
import com.example.MedDesk.repository.*;
import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@Transactional(readOnly = true)
public class AtendimentoService {
    private final AtendimentoRepository atendimentos;
    private final PacienteRepository pacientes;
    private final ProfissionalRepository profissionais;
    private final QuartoRepository quartos;
    private final EntityManager entityManager;

    public AtendimentoService(AtendimentoRepository atendimentos, PacienteRepository pacientes,
            ProfissionalRepository profissionais, QuartoRepository quartos, EntityManager entityManager) {
        this.atendimentos = atendimentos;
        this.pacientes = pacientes;
        this.profissionais = profissionais;
        this.quartos = quartos;
        this.entityManager = entityManager;
    }

    @Transactional
    public DadosAtendimento criarConsulta(NovaConsulta dados) {
        boolean emergencia = dados.getModalidade() == Modalidade.EMERGENCIA;
        if ((dados.getData() == null) != (dados.getHorario() == null) || (!emergencia && dados.getData() == null)) {
            throw new IllegalArgumentException("Informe data e horário juntos. São obrigatórios para agendamentos.");
        }
        LocalDateTime momento = dados.getData() == null ? LocalDateTime.now()
                : LocalDateTime.of(dados.getData(), dados.getHorario());
        LocalDate data = momento.toLocalDate();
        LocalTime horario = momento.toLocalTime().withNano(0);
        ProfissionalSaude profissional = bloquearProfissional(dados.getProfissionalId());
        Paciente paciente = bloquearPaciente(dados.getPacienteId());
        Atendimento atendimento;
        switch (dados.getModalidade()) {
            case ROTINA:
                atendimento = new ConsultaRotina(paciente, profissional, data, horario, dados.getMotivo());
                break;
            case ESPECIALIZADA:
                atendimento = new ConsultaEspecializada(paciente, profissional, data, horario, dados.getEspecialidade());
                break;
            case EMERGENCIA:
                atendimento = new AtendimentoEmergencia(paciente, profissional, data, horario, dados.getPrioridade());
                break;
            case CIRURGIA:
                atendimento = new Cirurgia(paciente, profissional, data, horario, dados.getDescricaoProcedimento(),
                        dados.getTipo());
                break;
            default:
                throw new IllegalArgumentException("Cadastre internações no endpoint /internacoes.");
        }
        return registrar(atendimento);
    }

    @Transactional
    public DadosAtendimento criarInternacao(NovaInternacao dados) {
        ProfissionalSaude profissional = bloquearProfissional(dados.getProfissionalId());
        Paciente paciente = bloquearPaciente(dados.getPacienteId());
        Quarto quarto = bloquearQuarto(dados.getQuarto().strip());
        return registrar(new Internacao(paciente, profissional, dados.getData(), dados.getHorario().withNano(0), quarto,
                dados.getDataPrevistaAlta()));
    }

    @Transactional
    public DadosAtendimento encaminhar(String id, NovoEncaminhamento dados) {
        ProfissionalSaude especialista = bloquearProfissional(dados.getProfissionalId());
        Atendimento origem = buscar(id, false);
        origem.encaminharEspecializacao();
        Paciente paciente = bloquearPaciente(origem.getPaciente().getId());
        ClinicoGeral clinico = (ClinicoGeral) origem.getProfissionalResponsavel();
        clinico.encaminharPaciente(paciente);
        return registrar(new ConsultaEspecializada(paciente, especialista, dados.getData(), dados.getHorario().withNano(0),
                dados.getEspecialidade()));
    }

    private DadosAtendimento registrar(Atendimento atendimento) {
        ProfissionalSaude profissional = atendimento.getProfissionalResponsavel();
        if (atendimento instanceof Agendavel
                && atendimentos.profissionaisOcupados(atendimento.getData(), atendimento.getHorario())
                        .contains(profissional.getId())) {
            throw new IllegalStateException("O profissional já tem um atendimento nesse horário.");
        }
        entityManager.persist(atendimento);
        atendimento.getPaciente().getHistoricoMedico().adicionarAtendimento(atendimento);
        if (atendimento instanceof ConsultaRotina) {
            ((ClinicoGeral) profissional).registrarConsulta((ConsultaRotina) atendimento);
        } else if (atendimento instanceof ConsultaEspecializada) {
            ((Especialista) profissional).registrarConsulta((ConsultaEspecializada) atendimento);
        } else if (atendimento instanceof Cirurgia) {
            ((Cirurgiao) profissional).registrarCirurgia((Cirurgia) atendimento);
        }
        return new DadosAtendimento(atendimento);
    }

    @Transactional
    public DadosAtendimento alterar(String id, boolean internacao, Alteracao dados) {
        Atendimento atendimento = verificarModalidade(atendimentos.bloquear(id).orElse(null), internacao);
        switch (dados.getOperacao()) {
            case CONFIRMAR:
                atendimento.confirmar();
                break;
            case CANCELAR:
                atendimento.cancelar();
                break;
            case INICIAR:
                iniciar(atendimento, dados);
                break;
            case CONCLUIR:
                if (atendimento instanceof Internacao) {
                    throw new IllegalArgumentException("Use REGISTRAR_ALTA para concluir uma internação.");
                }
                atendimento.concluir();
                break;
            case INFORMAR_AUSENCIA:
                atendimento.informarAusencia();
                break;
            case REGISTRAR_ALTA:
                registrarAlta(atendimento, dados.getDataEfetiva());
                break;
            case ALTERAR_PRIORIDADE:
                if (!(atendimento instanceof AtendimentoEmergencia)) {
                    throw new IllegalArgumentException("Operação incompatível com a modalidade do atendimento.");
                }
                ((AtendimentoEmergencia) atendimento).alterarPrioridade(dados.getPrioridade());
                break;
            case REGISTRAR_OBSERVACAO:
                atendimento.registrarObservacao(dados.getObservacao());
                break;
            case REGISTRAR_OBSERVACAO_PRE:
                cirurgiaDoAtendimento(atendimento).registrarObservacaoPre(dados.getObservacao());
                break;
            case REGISTRAR_OBSERVACAO_POS:
                cirurgiaDoAtendimento(atendimento).registrarObservacaoPos(dados.getObservacao());
                break;
        }
        return new DadosAtendimento(atendimento);
    }

    private void iniciar(Atendimento atendimento, Alteracao dados) {
        if (atendimento instanceof Internacao) {
            Internacao internacao = (Internacao) atendimento;
            Long paciente = internacao.getPaciente().getId();
            bloquearPaciente(paciente);
            bloquearQuartoDaInternacao(internacao);
            if (atendimentos.existeInternacaoAtiva(paciente)) {
                throw new IllegalStateException("O paciente já tem uma internação em andamento.");
            }
        }
        if (atendimento instanceof Cirurgia
                && (dados.getDataEfetiva() != null || dados.getHorarioEfetivo() != null)) {
            if (dados.getDataEfetiva() == null || dados.getHorarioEfetivo() == null) {
                throw new IllegalArgumentException("Informe data e horário efetivos juntos.");
            }
            Cirurgia cirurgia = (Cirurgia) atendimento;
            cirurgia.realizarCirurgia(LocalDateTime.of(dados.getDataEfetiva(), dados.getHorarioEfetivo().withNano(0)));
            return;
        }
        atendimento.iniciar();
    }

    private void registrarAlta(Atendimento atendimento, LocalDate dataAlta) {
        if (!(atendimento instanceof Internacao)) {
            throw new IllegalArgumentException("Operação incompatível com a modalidade do atendimento.");
        }
        Internacao internacao = (Internacao) atendimento;
        bloquearPaciente(internacao.getPaciente().getId());
        bloquearQuartoDaInternacao(internacao);
        if (dataAlta == null) {
            dataAlta = LocalDate.now();
        }
        internacao.registrarAlta(dataAlta);
    }

    private Cirurgia cirurgiaDoAtendimento(Atendimento atendimento) {
        if (!(atendimento instanceof Cirurgia)) {
            throw new IllegalArgumentException("Operação incompatível com a modalidade do atendimento.");
        }
        return (Cirurgia) atendimento;
    }

    public DadosAtendimento detalhar(String id, boolean internacao) {
        return new DadosAtendimento(buscar(id, internacao));
    }

    public ArrayList<DadosAtendimento> listarConsultas(Situacao situacao) {
        return converterAtendimentos(atendimentos.listarConsultas(situacao));
    }

    public ArrayList<DadosAtendimento> listarInternacoes(Situacao situacao) {
        return converterAtendimentos(atendimentos.listarInternacoes(situacao));
    }

    public ArrayList<DadosAtendimento> historico(Long id) {
        Paciente paciente = pacientes.findById(id).orElse(null);
        if (paciente == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Paciente não encontrado.");
        }
        return converterAtendimentos(paciente.getHistoricoMedico().consultarHistorico());
    }

    private ArrayList<DadosAtendimento> converterAtendimentos(Iterable<? extends Atendimento> registros) {
        ArrayList<DadosAtendimento> resultado = new ArrayList<>();
        for (Atendimento atendimento : registros) {
            resultado.add(new DadosAtendimento(atendimento));
        }
        return resultado;
    }

    private Atendimento buscar(String id, boolean internacao) {
        return verificarModalidade(atendimentos.findById(id).orElse(null), internacao);
    }

    private Atendimento verificarModalidade(Atendimento atendimento, boolean internacao) {
        if (atendimento == null || internacao != (atendimento instanceof Internacao)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Atendimento não encontrado.");
        }
        return atendimento;
    }

    private Paciente bloquearPaciente(Long id) {
        Paciente paciente = pacientes.bloquear(id).orElse(null);
        if (paciente == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Paciente não encontrado.");
        }
        return paciente;
    }

    private ProfissionalSaude bloquearProfissional(Long id) {
        ProfissionalSaude profissional = profissionais.bloquear(id).orElse(null);
        if (profissional == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Profissional não encontrado.");
        }
        return profissional;
    }

    private Quarto bloquearQuarto(String numero) {
        Quarto quarto = quartos.bloquear(numero).orElse(null);
        if (quarto == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Quarto não encontrado.");
        }
        return quarto;
    }

    private void bloquearQuartoDaInternacao(Internacao internacao) {
        // Obtenha o ID sem carregar a ocupação antes de adquirir o bloqueio.
        String numero = (String) entityManager.getEntityManagerFactory().getPersistenceUnitUtil()
                .getIdentifier(internacao.getQuarto());
        bloquearQuarto(numero);
    }
}
