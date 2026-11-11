# Diagrama de classes da Sprint 2

Representação do domínio implementado a partir do [UML fornecido](Diagrama%20de%20Classes%20-%20Sprint%202.png). A assinatura que menciona `ConsultaGeral` usa a classe `ConsultaRotina` desenhada no UML. O tipo de quarto continua sendo `String`.

```mermaid
classDiagram
    class Paciente {
        -Long id
        -String nome
        -String cpf
        -LocalDate dataNascimento
        -String telefone
        -String endereco
        -String email
        +exibirInformacoes()
    }
    class HistoricoMedico {
        -Long id
        +consultarHistorico()
        +adicionarAtendimento(Atendimento atendimento)
        +removerAtendimento(Atendimento atendimento)
        +buscarAtendimento(String id)
    }
    class ProfissionalSaude {
        <<abstract>>
        -Long id
        -String nome
        -String registro
        -String telefone
        -String email
        +exibirInformacoes()
    }
    class ClinicoGeral {
        +encaminharPaciente(Paciente paciente)
        +registrarConsulta(ConsultaRotina consulta)
    }
    class Especialista {
        -String especialidade
        +consultarProfissionaisDisponiveis()
        +registrarConsulta(ConsultaEspecializada consulta)
    }
    class Cirurgiao {
        -String atuacaoCirurgica
        +registrarCirurgia(Cirurgia cirurgia)
    }
    class Atendimento {
        <<abstract>>
        -String id
        -LocalDate data
        -LocalTime horario
        -List~String~ observacoes
        #validarCondicoes()
        +registrarObservacao(String observacao)
        +exibirInformacoes()
        +confirmar()
        +cancelar()
        +iniciar()
        +concluir()
        +informarAusencia()
        +encaminharEspecializacao()
    }
    class Agendavel {
        <<interface>>
        +consultarProfissionaisDisponiveis()
        +agendar()
    }
    class ConsultaRotina {
        -String motivo
    }
    class ConsultaEspecializada {
        -String especialidade
    }
    class AtendimentoEmergencia {
        +alterarPrioridade(Prioridade prioridade)
    }
    class Cirurgia {
        -String descricaoProcedimento
        -String tipo
        -List~String~ observacoesPreOperatorias
        -List~String~ observacoesPosOperatorias
        -LocalDate dataEfetiva
        -LocalTime horarioEfetivo
        +registrarObservacaoPre(String observacao)
        +registrarObservacaoPos(String observacao)
        +realizarCirurgia()
    }
    class Internacao {
        -LocalDate dataPrevistaAlta
        -LocalDate dataEfetivaAlta
        +registrarAlta()
    }
    class Quarto {
        -String numeroIdentificacao
        -int andar
        -int capacidadeMaximaPacientes
        -boolean disponivel
        -String tipo
        +verificarDisponibilidade() boolean
        +adicionarPaciente(Paciente paciente)
        +removerPaciente(Long id)
    }
    class Situacao {
        <<enumeration>>
        AGENDADA
        CONFIRMADA
        AGUARDANDO_AVALIACAO
        EM_ATENDIMENTO
        CONCLUIDA
        CANCELADA
        NAO_COMPARECEU
    }
    class Prioridade {
        <<enumeration>>
        BAIXA
        MODERADA
        ALTA
        CRITICA
    }

    Paciente "1" *-- "1" HistoricoMedico : historicoMedico
    HistoricoMedico "1" --> "0..*" Atendimento : consultas
    Atendimento "0..*" --> "1" Paciente : paciente
    Atendimento "0..*" --> "1" ProfissionalSaude : profissionalResponsavel
    Atendimento --> Situacao : situacao
    AtendimentoEmergencia --> Prioridade : prioridade
    ProfissionalSaude <|-- ClinicoGeral
    ProfissionalSaude <|-- Especialista
    ProfissionalSaude <|-- Cirurgiao
    Atendimento <|-- ConsultaRotina
    Atendimento <|-- ConsultaEspecializada
    Atendimento <|-- AtendimentoEmergencia
    Atendimento <|-- Cirurgia
    Atendimento <|-- Internacao
    Agendavel <|.. ConsultaRotina
    Agendavel <|.. ConsultaEspecializada
    Agendavel <|.. Cirurgia
    Agendavel <|.. Internacao
    ClinicoGeral "1" --> "0..*" ConsultaRotina : consultasGerais
    Especialista "1" --> "0..*" ConsultaEspecializada : consultasEspecializadas
    Cirurgiao "1" --> "0..*" Cirurgia : cirurgias
    Internacao "0..*" --> "1" Quarto : quarto
    Quarto "0..1" --> "0..*" Paciente : pacientes
```
