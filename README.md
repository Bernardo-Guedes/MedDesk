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

- **Linguagem:** Java 21+
- **Framework Principal:** Spring Boot 3.x
- **Gerenciador de Dependências:** Maven

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
