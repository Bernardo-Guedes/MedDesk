# Sistema de Gestão Hospitalar 

Trabalho Prático para a disciplina de Programação Modular do curso de Bacharelado em Engenharia de Software — Pontifícia Universidade Católica de Minas Gerais (PUC Minas).

---

## 📚 Índice
* [Sobre o Projeto](#-sobre-o-projeto)
* [Objetivos](#-objetivos)
* [Tecnologias Utilizadas](#-tecnologias-utilizadas)
* [Regras de Negócio](#regras-de-negocio)
* [Entidades do Sistema](#-entidades-do-sistema)
* [Arquitetura e Estrutura do Projeto](#-arquitetura-e-estrutura-do-projeto)
* [Modelagem Orientada a Objetos](#-modelagem-orientada-a-objetos)
* [Ciclo de Vida dos Atendimentos](#-ciclo-de-vida-dos-atendimentos)
* [Endpoints da API](#-endpoints-da-api)
* [Exemplos de Requisição](#-exemplos-de-requisição)
* [Tratamento de Erros](#-tratamento-de-erros)
* [Persistência de Dados](#-persistência-de-dados)
* [Como Executar](#-como-executar)
* [Frontend](#-frontend)
* [Documentação](#-documentação)
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

- **Linguagem:** Java 21+
- **Framework Principal:** Spring Boot 3.x
- **Gerenciador de Dependências:** Maven

### Detalhes das versões e bibliotecas

Versões efetivamente configuradas no projeto (`backend/pom.xml` e `frontend/package.json`):

**Backend**

| Item | Versão / Uso |
| :--- | :--- |
| Java | 25 (`java.version` no `pom.xml`) |
| Spring Boot | 4.1.1 (`spring-boot-starter-parent`) |
| Spring Web MVC | `spring-boot-starter-webmvc` — exposição dos endpoints REST |
| Bean Validation | `spring-boot-starter-validation` — validação dos DTOs (`@NotNull`, `@NotBlank`, `@Email`, `@Past`, `@Min`, `@Pattern`) |
| Jackson | Serialização JSON, incluindo polimorfismo via `@JsonTypeInfo` / `@JsonSubTypes` |
| Testes | `spring-boot-starter-webmvc-test` (JUnit 5) |
| Maven Wrapper | `mvnw` / `mvnw.cmd` (Maven 3.9.16) — não é necessário ter o Maven instalado |

**Frontend**

| Item | Versão / Uso |
| :--- | :--- |
| React | 19 |
| TypeScript | 6 |
| Vite | 8 — servidor de desenvolvimento e build |
| Tailwind CSS | 4 — estilização |
| React Router | 8 — roteamento das telas |
| Lucide React / Base UI | Ícones e componentes de interface |

---

## Regras de Negocio

**Pacientes**
- O CPF é normalizado (apenas dígitos), deve conter **11 dígitos** e é **único** no sistema.
- A data de nascimento é obrigatória e não pode ser futura.
- Um paciente que já possui atendimentos no histórico **não pode ser removido**.

**Profissionais da saúde**
- O registro profissional é obrigatório e **único** (comparação sem diferenciar maiúsculas/minúsculas).
- A categoria deve ser `CLINICO_GERAL`, `ESPECIALISTA` ou `CIRURGIAO`.
- Especialista exige **especialidade**; cirurgião exige **atuação cirúrgica**.
- Não é possível alterar a especialidade de um especialista que já possui atendimentos.
- Um profissional com atendimentos registrados **não pode ser removido**.

**Agenda e disponibilidade**
- Consultas e procedimentos cirúrgicos só podem ser agendados para **data/horário futuros**.
- Um profissional não pode ter duas consultas/procedimentos no mesmo dia e horário. Consultas `CANCELADA` / `NAO_COMPARECEU` e procedimentos `CANCELADO` **liberam** o horário.
- É possível consultar os profissionais **disponíveis por especialidade** em uma data e horário.

**Modalidades de atendimento** (cada uma implementa seu próprio método `validar()`)
- **Consulta de rotina:** exige motivo e deve ser realizada por um **clínico geral**.
- **Consulta especializada:** deve ser realizada por um **especialista cuja especialidade seja igual** à da consulta.
- **Encaminhamento:** somente um clínico geral pode encaminhar, e sempre para um especialista (gera uma consulta especializada).
- **Emergência:** exige classificação de prioridade (`BAIXA`, `MODERADA`, `ALTA`, `CRITICA`) e é registrada com data/hora do momento. A listagem é ordenada da prioridade **mais crítica para a mais baixa**.
- **Procedimento cirúrgico:** exige descrição e tipo de cirurgia e deve ser realizado por um **cirurgião**. Não pode ser concluído sem antes ter sido iniciado; ao ser iniciado, registra data e horário efetivos.
- **Internação:** o quarto deve estar `DISPONIVEL` e com vaga; a data prevista de alta não pode ser anterior à data de entrada; um paciente não pode ter **duas internações ativas**; a alta só pode ser registrada uma vez e **libera a vaga** do quarto.

**Observações**
- Observações de consulta só podem ser registradas com a consulta `EM_ATENDIMENTO` ou `CONCLUIDA`.
- Observações pré-operatórias só podem ser registradas com o procedimento `AGENDADO`; pós-operatórias, somente após `CONCLUIDO`.
- Toda observação registrada também é adicionada às **informações relevantes do histórico médico** do paciente.

**Quartos**
- Número de identificação positivo e único, andar não negativo e capacidade máxima maior que zero.
- A capacidade não pode ser reduzida abaixo da quantidade de pacientes internados.
- A situação (`DISPONIVEL` / `OCUPADO`) é **calculada automaticamente** conforme a ocupação.
- Quartos com pacientes internados ou com internações registradas **não podem ser removidos**.
- Um quarto não pode ultrapassar sua capacidade máxima de ocupação de pacientes simultâneos.


---

## 🗂 Entidades do Sistema

| Entidade | Campos Principais |
| :--- | :--- |
| **Paciente** | Nome, CPF, Data de Nascimento, Telefone, Endereço, E-mail |
| **Profissional da Saúde** | Nome, Registro Profissional (CRM/COREN/etc), Especialidade, Telefone, E-mail |
| **Consulta** | Paciente, Profissional Responsável, Data, Horário, Motivo, Observações Médicas |
| **Internação** | Paciente, Profissional Responsável, Quarto, Data de Entrada, Data Prevista de Alta, Data Efetiva de Alta, Observações |
| **Quarto** | Número de Identificação, Andar, Capacidade Máxima, Situação Atual (*Disponível / Ocupado*) |
| **Clínico Geral** | Campos comuns de Profissional da Saúde; pode **encaminhar** pacientes para especialistas |
| **Especialista** | Campos comuns + Especialidade (`CARDIOLOGIA`, `NEUROLOGIA`, `ORTOPEDIA`) |
| **Cirurgião** | Campos comuns + Atuação Cirúrgica, Lista de Procedimentos Cirúrgicos realizados |
| **Atendimento** *(abstrata)* | Id (UUID), Paciente, Profissional, Data, Horário, Observações |
| **Consulta de Rotina** | Campos de Atendimento + Motivo, Status da Consulta |
| **Consulta Especializada** | Campos de Atendimento + Especialidade, Status da Consulta |
| **Emergência** | Campos de Atendimento + Prioridade, Status da Emergência |
| **Procedimento Cirúrgico** | Campos de Atendimento + Descrição, Tipo de Cirurgia, Observações Pré e Pós-operatórias, Data e Horário Efetivos, Status do Procedimento |
| **Histórico Médico** | Atendimentos do paciente (ordenados por data/horário), Informações Relevantes registradas |

---

## 🧱 Arquitetura e Estrutura do Projeto

O backend segue a arquitetura em camadas **Controller -> Service -> Repository -> Model**:

- **Controller:** recebe as requisições HTTP, valida os DTOs com `@Valid` e delega para os serviços.
- **Service:** orquestra os casos de uso (busca entidades, verifica disponibilidade, unicidade e regras entre entidades).
- **Repository:** armazena e consulta os dados.
- **Model:** classes de domínio com encapsulamento e regras de negócio próprias (validações nos setters, transições de status, `validar()` por modalidade).
- **DTO:** `records` de entrada das requisições, desacoplando a API do modelo de domínio.
- **Exception:** exceções de domínio e tratamento global de erros.

```
MedDesk/
├── backend/                         # API REST (Spring Boot)
│   ├── pom.xml
│   ├── mvnw / mvnw.cmd
│   └── src/
│       ├── main/
│       │   ├── java/com/example/MedDesk/
│       │   │   ├── application/     # MedDeskApplication (classe principal)
│       │   │   ├── controller/      # Endpoints REST
│       │   │   ├── dto/             # Requests (records) com Bean Validation
│       │   │   ├── exception/       # Exceções e GlobalExceptionHandler
│       │   │   ├── model/           # Classes de domínio e interfaces
│       │   │   │   └── enums/       # Especialidade, Prioridade, Status...
│       │   │   ├── repository/      # Repositórios em memória
│       │   │   └── service/         # Regras de negócio / casos de uso
│       │   └── resources/
│       │       └── application.properties
│       └── test/                    # Testes automatizados
├── frontend/                        # Interface web (React + Vite)
├── docs/                            # Diagramas UML e Cartões CRC
└── orientacoes-sprints/             # Enunciados das sprints
```

---

## 🧬 Modelagem Orientada a Objetos

### Hierarquias e interfaces

- **`ProfissionalSaude`** (abstrata) -> `ClinicoGeral`, `Especialista`, `Cirurgiao`
- **`Atendimento`** (abstrata, método abstrato `validar()`) ->
  - **`Consulta`** (abstrata, implementa `Agendavel`) -> `ConsultaRotina`, `ConsultaEspecializada`
  - `Emergencia`
  - `ProcedimentoCirurgico` (implementa `Agendavel`)
  - `Internacao`
- **`Agendavel`** — interface para atendimentos que podem ser agendados (`agendar(...)`).
- **`ConsultavelPorEspecialidade`** — interface implementada pelo `ConsultaService` para buscar profissionais disponíveis por especialidade.
- **`Paciente`** possui um **`HistoricoMedico`** (composição), que agrega seus atendimentos e informações relevantes.
- **`Quarto`** mantém a lista de pacientes internados e calcula sua situação.


### Enumerações

| Enum | Valores |
| :--- | :--- |
| `Especialidade` | `CARDIOLOGIA`, `NEUROLOGIA`, `ORTOPEDIA` |
| `Prioridade` | `BAIXA`, `MODERADA`, `ALTA`, `CRITICA` |
| `StatusConsulta` | `AGENDADA`, `CONFIRMADA`, `EM_ATENDIMENTO`, `CONCLUIDA`, `CANCELADA`, `NAO_COMPARECEU` |
| `StatusEmergencia` | `AGUARDANDO_AVALIACAO`, `INICIADO`, `CONCLUIDO` |
| `StatusProcedimentoCirurgico` | `AGENDADO`, `EM_ANDAMENTO`, `CONCLUIDO`, `CANCELADO` |
| `SituacaoQuarto` | `DISPONIVEL`, `OCUPADO` |

### Polimorfismo no JSON

As respostas da API indicam o tipo concreto de cada objeto:
- Profissionais trazem o campo **`categoria`** (`CLINICO_GERAL`, `ESPECIALISTA`, `CIRURGIAO`).
- Atendimentos trazem o campo **`modalidade`** (`CONSULTA_ROTINA`, `CONSULTA_ESPECIALIZADA`, `EMERGENCIA`, `PROCEDIMENTO_CIRURGICO`, `INTERNACAO`).

---

## 🔄 Ciclo de Vida dos Atendimentos

As transições de status são controladas pelas próprias classes de domínio. Qualquer transição fora das abaixo é rejeitada com HTTP `422`.

### Internação

Uma internação é **ativa** enquanto não possui data efetiva de alta. Ao registrar a alta, a data efetiva é preenchida com a data atual e o paciente é removido do quarto, liberando a vaga.

---

## 🌐 Endpoints da API

URL base padrão: `http://localhost:8080`

Formatos: datas em `yyyy-MM-dd`, horários em `HH:mm`, identificadores em UUID (exceto quartos, identificados pelo número).

### Pacientes — `/pacientes`

| Método | Rota | Descrição |
| :--- | :--- | :--- |
| `POST` | `/pacientes` | Cadastra um paciente |
| `GET` | `/pacientes` | Lista todos os pacientes |
| `GET` | `/pacientes/{id}` | Busca um paciente pelo id |
| `PUT` | `/pacientes/{id}` | Atualiza os dados de um paciente |
| `DELETE` | `/pacientes/{id}` | Remove um paciente (sem atendimentos no histórico) |
| `GET` | `/pacientes/{id}/historico` | Consulta o histórico médico (atendimentos + informações relevantes) |

### Profissionais — `/profissionais`

| Método | Rota | Descrição |
| :--- | :--- | :--- |
| `POST` | `/profissionais` | Cadastra um profissional (`categoria`: `CLINICO_GERAL`, `ESPECIALISTA` ou `CIRURGIAO`) |
| `GET` | `/profissionais?especialidade=` | Lista profissionais (filtro opcional por especialidade) |
| `GET` | `/profissionais/disponiveis?especialidade=&data=&horario=` | Lista especialistas **disponíveis** na especialidade, data e horário informados |
| `GET` | `/profissionais/{id}` | Busca um profissional pelo id |
| `PUT` | `/profissionais/{id}` | Atualiza um profissional |
| `DELETE` | `/profissionais/{id}` | Remove um profissional (sem atendimentos) |
| `GET` | `/profissionais/{id}/procedimentos` | Lista os procedimentos cirúrgicos de um cirurgião |

### Consultas — `/consultas`

| Método | Rota | Descrição |
| :--- | :--- | :--- |
| `POST` | `/consultas/rotina` | Agenda uma consulta de rotina (clínico geral) |
| `POST` | `/consultas/especializada` | Agenda uma consulta especializada (especialista) |
| `POST` | `/consultas/encaminhamentos` | Clínico geral encaminha o paciente a um especialista |
| `GET` | `/consultas?status=` | Lista consultas (filtro opcional por `StatusConsulta`) |
| `GET` | `/consultas/{id}` | Busca uma consulta pelo id |
| `PATCH` | `/consultas/{id}/confirmar` | Confirma a consulta |
| `PATCH` | `/consultas/{id}/iniciar` | Inicia o atendimento |
| `PATCH` | `/consultas/{id}/concluir` | Conclui a consulta |
| `PATCH` | `/consultas/{id}/cancelar` | Cancela a consulta |
| `PATCH` | `/consultas/{id}/ausencia` | Registra que o paciente não compareceu |
| `PATCH` | `/consultas/{id}/observacoes` | Registra observações médicas |

### Internações — `/internacoes`

| Método | Rota | Descrição |
| :--- | :--- | :--- |
| `POST` | `/internacoes` | Interna um paciente em um quarto |
| `GET` | `/internacoes?ativas=` | Lista internações (`ativas=true` → sem alta; `ativas=false` → com alta) |
| `GET` | `/internacoes/{id}` | Busca uma internação pelo id |
| `PATCH` | `/internacoes/{id}/alta` | Registra a alta e libera a vaga do quarto |
| `PATCH` | `/internacoes/{id}/observacoes` | Registra observações |

### Quartos — `/quartos`

| Método | Rota | Descrição |
| :--- | :--- | :--- |
| `POST` | `/quartos` | Cadastra um quarto |
| `GET` | `/quartos?situacao=` | Lista quartos (filtro opcional: `DISPONIVEL` ou `OCUPADO`) |
| `GET` | `/quartos/{numero}` | Busca um quarto pelo número |
| `PUT` | `/quartos/{numero}` | Atualiza andar e capacidade |
| `DELETE` | `/quartos/{numero}` | Remove um quarto (sem pacientes e sem internações registradas) |

### Emergências — `/emergencias`

| Método | Rota | Descrição |
| :--- | :--- | :--- |
| `POST` | `/emergencias` | Registra um atendimento de emergência |
| `GET` | `/emergencias?status=` | Lista emergências ordenadas por prioridade (filtro opcional por `StatusEmergencia`) |
| `GET` | `/emergencias/{id}` | Busca uma emergência pelo id |
| `PATCH` | `/emergencias/{id}/iniciar` | Inicia a avaliação |
| `PATCH` | `/emergencias/{id}/concluir` | Conclui o atendimento |
| `PATCH` | `/emergencias/{id}/observacoes` | Registra observações |

### Procedimentos cirúrgicos — `/procedimentos`

| Método | Rota | Descrição |
| :--- | :--- | :--- |
| `POST` | `/procedimentos` | Agenda um procedimento cirúrgico |
| `GET` | `/procedimentos?status=` | Lista procedimentos (filtro opcional por `StatusProcedimentoCirurgico`) |
| `GET` | `/procedimentos/{id}` | Busca um procedimento pelo id |
| `PATCH` | `/procedimentos/{id}/iniciar` | Inicia o procedimento |
| `PATCH` | `/procedimentos/{id}/concluir` | Conclui o procedimento |
| `PATCH` | `/procedimentos/{id}/cancelar` | Cancela o procedimento |
| `PATCH` | `/procedimentos/{id}/observacoes-pre` | Registra observações pré-operatórias |
| `PATCH` | `/procedimentos/{id}/observacoes-pos` | Registra observações pós-operatórias |

---

## 🧪 Exemplos de Requisição

**Cadastrar paciente** — `POST /pacientes`
```json
{
  "nome": "Maria Souza",
  "cpf": "123.456.789-09",
  "dataNascimento": "1990-05-20",
  "telefone": "(31) 99999-0000",
  "endereco": "Rua A, 100 - Belo Horizonte",
  "email": "maria@email.com"
}
```

**Cadastrar profissional** — `POST /profissionais`
```json
{
  "categoria": "ESPECIALISTA",
  "nome": "Dr. João Lima",
  "registroProfissional": "CRM-MG 12345",
  "telefone": "(31) 98888-0000",
  "email": "joao@hospital.com",
  "especialidade": "CARDIOLOGIA"
}
```
> Para `CIRURGIAO`, informe `atuacaoCirurgica` no lugar de `especialidade`. Para `CLINICO_GERAL`, nenhum dos dois é necessário.

**Cadastrar quarto** — `POST /quartos`
```json
{ "numeroIdentificacao": 101, "andar": 1, "capacidadeMaximaPacientes": 2 }
```

**Agendar consulta de rotina** — `POST /consultas/rotina`
```json
{
  "pacienteId": "<uuid-do-paciente>",
  "profissionalId": "<uuid-do-clinico-geral>",
  "data": "2026-12-01",
  "horario": "14:30",
  "motivo": "Check-up anual"
}
```

**Agendar consulta especializada** — `POST /consultas/especializada`
```json
{
  "pacienteId": "<uuid-do-paciente>",
  "profissionalId": "<uuid-do-especialista>",
  "data": "2026-12-02",
  "horario": "09:00",
  "especialidade": "CARDIOLOGIA"
}
```

**Encaminhar para especialista** — `POST /consultas/encaminhamentos`
```json
{
  "clinicoGeralId": "<uuid-do-clinico-geral>",
  "pacienteId": "<uuid-do-paciente>",
  "especialistaId": "<uuid-do-especialista>",
  "data": "2026-12-03",
  "horario": "10:00"
}
```

**Internar paciente** — `POST /internacoes`
```json
{
  "pacienteId": "<uuid-do-paciente>",
  "profissionalId": "<uuid-do-profissional>",
  "numeroQuarto": 101,
  "dataPrevistaAlta": "2026-12-10",
  "observacoes": "Observação clínica inicial"
}
```

**Registrar emergência** — `POST /emergencias`
```json
{
  "pacienteId": "<uuid-do-paciente>",
  "profissionalId": "<uuid-do-profissional>",
  "prioridade": "ALTA"
}
```

**Agendar procedimento cirúrgico** — `POST /procedimentos`
```json
{
  "pacienteId": "<uuid-do-paciente>",
  "cirurgiaoId": "<uuid-do-cirurgiao>",
  "data": "2026-12-05",
  "horario": "08:00",
  "descricao": "Artroscopia de joelho",
  "tipo": "Ortopédica"
}
```

**Registrar observações** — `PATCH /consultas/{id}/observacoes` (mesmo formato para emergências, internações e observações pré/pós de procedimentos)
```json
{ "observacao": "Paciente apresentou melhora do quadro." }
```

**Consultar profissionais disponíveis**
```
GET /profissionais/disponiveis?especialidade=CARDIOLOGIA&data=2026-12-02&horario=09:00
```

---

## 🚨 Tratamento de Erros

Os erros são tratados de forma centralizada pelo `GlobalExceptionHandler` (`@RestControllerAdvice`):

| Situação | Exceção | Status HTTP |
| :--- | :--- | :--- |
| Recurso não encontrado (paciente, profissional, consulta, quarto...) | `RecursoNaoEncontradoException` | `404 Not Found` |
| Violação de regra de negócio | `RegraNegocioException` | `422 Unprocessable Entity` |
| Campos inválidos no corpo da requisição (Bean Validation) | `MethodArgumentNotValidException` | `400 Bad Request` |
| JSON malformado, data/horário em formato inválido ou valor de enum inexistente | `HttpMessageNotReadableException` | `400 Bad Request` |
| Parâmetro de URL com tipo inválido (ex.: UUID ou enum inválido) | `MethodArgumentTypeMismatchException` | `400 Bad Request` |

Formato padrão da resposta de erro (`ErroResponse`):

```json
{
  "timestamp": "2026-11-12T10:15:30",
  "status": 422,
  "mensagem": "O profissional Dr. João Lima já possui um atendimento agendado em 2026-12-02 às 09:00",
  "detalhes": []
}
```

Em erros de validação, `detalhes` lista cada campo inválido (ex.: `"cpf: must not be blank"`).

---

## 💾 Persistência de Dados

Nesta etapa (Sprint 2), os repositórios são implementados **em memória** por meio da classe genérica `RepositorioEmMemoria<T, ID>`, que oferece `save`, `findById`, `findAll`, `deleteById` e `existsById`. Os repositórios concretos são:

- `PacienteRepository` — busca adicional por CPF;
- `ProfissionalSaudeRepository` — busca adicional por registro profissional;
- `QuartoRepository` — identificado pelo número do quarto;
- `AtendimentoRepository` — armazena todas as modalidades de atendimento, com busca por tipo e por profissional.

---

## 🚀 Como Executar

### Pré-requisitos
- **JDK 25**
- **Node.js** (com npm) para o frontend

### Backend

```bash
cd backend
./mvnw spring-boot:run        # Linux/macOS
mvnw.cmd spring-boot:run      # Windows
```

A API ficará disponível em `http://localhost:8080` (porta configurada em `application.properties`).

Para executar os testes automatizados:

```bash
cd backend
./mvnw test
```

### Frontend

```bash
cd frontend
npm install
npm run dev
```

A aplicação ficará disponível no endereço informado pelo Vite (normalmente `http://localhost:5173`).

---

## 💻 Frontend

Interface web em React + TypeScript, organizada em dois perfis de acesso. Nesta etapa as telas utilizam **dados de exemplo** e ainda não consomem a API.

**Administrativo** (`/admin`)
| Rota | Tela |
| :--- | :--- |
| `/admin/` | Dashboard |
| `/admin/pacientes` | Lista de pacientes |
| `/admin/pacientes/1` | Detalhes do paciente |
| `/admin/profissionais` | Lista de profissionais |
| `/admin/internacoes` | Internações e quartos |
| `/admin/internacoes/quartos/:numeroQuarto` | Detalhes do quarto |
| `/admin/atendimentos/novo` | Criação de atendimento |
| `/admin/consultas/:consultaId` | Detalhes da consulta |

**Profissional da saúde** (`/profissional-saude`)
| Rota | Tela |
| :--- | :--- |
| `/profissional-saude` | Home do profissional |
| `/profissional-saude/agenda` | Agenda |
| `/profissional-saude/nova-consulta` | Nova consulta |
| `/profissional-saude/perfil` | Perfil |
| `/profissional-saude/consultas/:consultaId` | Detalhes da consulta |

---

## 📁 Documentação

Artefatos de modelagem disponíveis na pasta [`docs/`](docs/):

- [Diagrama UML — Sprint 1](docs/Diagrama%20UML%20-%20Trabalho%20Programação%20Modular.pdf)
- [Diagrama UML — Sprint 2](docs/Diagrama%20UML%20-%20Sprint%2002%20-%20Trabalho%20de%20Programação%20Modular.pdf)
- [Cartões CRC — Sprint 1](docs/Cartoes_CRC_Sistema_Medico.pdf)
- [Cartões CRC — Sprint 2](docs/Cartões%20CRC%20-%20MedDesk%20-%20Sprint%2002.pdf)

Enunciados das sprints disponíveis em [`orientacoes-sprints/`](orientacoes-sprints/).

---

## 👥 Integrantes

- Bernardo Guedes da Silveira
- Gustavo Ryan Gomes Guedes
- Lucas Gomes Esteves da Silva
- Paulo César Silva Monteiro
