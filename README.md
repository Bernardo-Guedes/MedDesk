# Sistema de Gestão Hospitalar 

Trabalho Prático para a disciplina de Programação Modular do curso de Bacharelado em Engenharia de Software — Pontifícia Universidade Católica de Minas Gerais (PUC Minas).

---

## 📚 Índice
* [Sobre o Projeto](#-sobre-o-projeto)
* [Objetivos](#-objetivos)
* [Tecnologias Utilizadas](#-tecnologias-utilizadas)
* [Regras de Negócio](#regras-de-negocio)
* [Entidades do Sistema](#-entidades-do-sistema)
* [Integrantes](#-integrantes)

---

## 📖 Sobre o projeto

O Sistema de Informação Hospitalar foi desenvolvido para atender às necessidades de um hospital de médio porte na transição de seus registros manuais para uma solução centralizada e automatizada. O sistema organiza e gerencia informações sobre pacientes, profissionais da saúde, consultas, internações e infraestrutura hospitalar (quartos), visando reduzir erros operacionais e otimizar processos administrativos e médicos.

---

## 🎯 Objetivos
- **Modelagem Orientada a Objetos:** Aplicação sólida dos princípios de POO (Encapsulamento, Herança, Polimorfismo, Abstração e S.O.L.I.D.).
- **Arquitetura em Camadas:** Organização clara dos papéis do código (`Controller`, `Service`, `Repository`, `Model`).
- **API RESTful:** Exposição de endpoints HTTP padronizados utilizando Spring Boot.
- **Persistência de Dados:** Integração com banco de dados relacional (ex: PostgreSQL / MySQL) ou não-relacional.
- **Tratamento de Exceções:** Manipulação centralizada de erros com respostas HTTP adequadas.
- **Testes Automatizados:** Garantia de qualidade das regras de negócio através de testes unitários e de integração.

---

## 🛠 Tecnologias Utilizadas

- **Linguagem:** Java 25
- **Framework Principal:** Spring Boot 4.1.1
- **Gerenciador de Dependências:** Maven
- **Banco de dados local:** H2 com Spring Data JPA

## API da Sprint 2

No Windows, execute `.\mvnw.cmd spring-boot:run`. A API fica disponível em `http://localhost:8080` e usa HTTP Basic. O usuário padrão é `user` e a senha de desenvolvimento é gerada no log de inicialização. Para defini-los, configure `SPRING_SECURITY_USER_NAME` e `SPRING_SECURITY_USER_PASSWORD` antes de iniciar. O banco local é salvo em `data/meddesk.mv.db`.

| Recurso | Rotas | Dados para criação |
| :--- | :--- | :--- |
| Pacientes | `GET/POST /pacientes`, `GET /pacientes/{id}`, `GET /pacientes/{id}/historico` | `nome`, `cpf`, `dataNascimento`. Também aceita `telefone`, `endereco` e `email`. |
| Profissionais | `GET/POST /profissionais`, `GET /profissionais/{id}` | `categoria`, `nome`, `registro`, `telefone`, `email`. Envie `especialidade` para `ESPECIALISTA` e `atuacaoCirurgica` para `CIRURGIAO`. A outra categoria é `CLINICO_GERAL`. |
| Consultas e outros atendimentos | `GET/POST /consultas`, `GET/PATCH /consultas/{id}`, `POST /consultas/{id}/encaminhamento` | `modalidade`, `pacienteId`, `profissionalId`, `data`, `horario` e os campos da modalidade. Na emergência, data e horário podem ser preenchidos automaticamente. |
| Internações | `GET/POST /internacoes`, `GET/PATCH /internacoes/{id}` | `pacienteId`, `profissionalId`, `data`, `horario`, `quarto` (número de identificação) e `dataPrevistaAlta`. |
| Quartos | `GET/POST /quartos`, `GET /quartos/{numero}` | `numeroIdentificacao`, `andar`, `capacidadeMaximaPacientes` e `tipo`. |

Envie os corpos como JSON com `Content-Type: application/json`. Use `AAAA-MM-DD` para datas e `HH:mm` para horários. Cadastros bem-sucedidos retornam `201 Created`. Dados inválidos retornam `400`, recursos ausentes retornam `404` e conflitos de estado ou de disponibilidade retornam `409`.

- Na modalidade `ROTINA`, envie `motivo`. Em `ESPECIALIZADA`, envie `especialidade`. Em `EMERGENCIA`, envie `prioridade` (`BAIXA`, `MODERADA`, `ALTA` ou `CRITICA`). Em `CIRURGIA`, envie `descricaoProcedimento` e `tipo`.
- Para mudar a situação, envie um `PATCH` como `{"operacao":"INICIAR"}`. As consultas aceitam `CONFIRMAR`, `CANCELAR`, `INICIAR`, `CONCLUIR` e `INFORMAR_AUSENCIA` conforme a situação atual. Use `REGISTRAR_OBSERVACAO` com `observacao` para acrescentar uma anotação.
- Para encaminhar uma consulta de rotina concluída, envie `profissionalId` do especialista, `data`, `horario` e `especialidade` ao endpoint de encaminhamento.
- Emergências começam em `AGUARDANDO_AVALIACAO` e aceitam `ALTERAR_PRIORIDADE` com o campo `prioridade` enquanto aguardam ou estão em atendimento. Cirurgias aceitam `REGISTRAR_OBSERVACAO_PRE` enquanto agendadas e `REGISTRAR_OBSERVACAO_POS` após a conclusão. Ao iniciar uma cirurgia, é possível informar `dataEfetiva` e `horarioEfetivo`; se omitidos, a API registra o momento atual.
- O quarto é ocupado ao iniciar a internação e a vaga é liberada com `REGISTRAR_ALTA`. A data da alta pode ser enviada em `dataEfetiva` e, se omitida, é usada a data atual.
- Filtre `/profissionais` por `especialidade` e, para disponibilidade, informe `data` e `horario` juntos. Filtre `/consultas` e `/internacoes` por `situacao`. Filtre `/quartos` por `tipo` e `disponivel`.

Execute os testes com `.\mvnw.cmd test`. Consulte o [diagrama de classes atualizado](docs/Diagrama%20de%20Classes%20-%20Sprint%202.md) e a [imagem fornecida](docs/Diagrama%20de%20Classes%20-%20Sprint%202.png).

---

## Regras de Negocio

- **Relacionamento Múltiplo:** Um paciente pode possuir diversas consultas e internações ao longo do tempo.
- **Atribuição de Consultas:** Toda consulta deve obrigatoriamente estar associada a um profissional de saúde responsável e a um paciente.
- **Concorrência de Horários:** Um profissional de saúde não pode possuir duas consultas/atendimentos agendados para o mesmo horário.
- **Vínculo de Internação:** Toda internação deve estar vinculada a um paciente e a um quarto disponível.
- **Capacidade de Quarto:** Um quarto não pode ultrapassar sua capacidade máxima de ocupação de pacientes simultâneos.
- **Histórico Médico:** O sistema deve manter o histórico completo e inalterável de consultas e internações do paciente.
- **Integridade de Recursos:** Operações de agendamento e internação devem checar disponibilidade de recursos previamente.

---

## 🗂 Entidades do Sistema

| Entidade | Campos Principais |
| :--- | :--- |
| **Paciente** | Nome, CPF, Data de Nascimento, Telefone, Endereço, E-mail |
| **Profissional da Saúde** | Nome, Registro Profissional (CRM/COREN/etc), Especialidade, Telefone, E-mail |
| **Consulta** | Paciente, Profissional Responsável, Data, Horário, Motivo, Observações Médicas |
| **Internação** | Paciente, Profissional Responsável, Quarto, Data de Entrada, Data Prevista de Alta, Data Efetiva de Alta, Observações |
| **Quarto** | Número de Identificação, Andar, Capacidade Máxima, Situação Atual (*Disponível / Ocupado*) |

---

## 👥 Integrantes

- Bernardo Guedes da Silveira
- Gustavo Ryan Gomes Guedes
- Lucas Gomes Esteves da Silva
- Paulo César Silva Monteiro
