package com.example.MedDesk.dto;

import com.example.MedDesk.model.Cirurgiao;
import com.example.MedDesk.model.ClinicoGeral;
import com.example.MedDesk.model.Especialista;
import com.example.MedDesk.model.Paciente;
import com.example.MedDesk.model.ProfissionalSaude;
import com.example.MedDesk.model.Quarto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import java.time.LocalDate;
import java.util.ArrayList;

public final class CadastroDto {
    private CadastroDto() {}

    public enum Categoria {
        CLINICO_GERAL, ESPECIALISTA, CIRURGIAO
    }

    public static class NovoPaciente {
        @NotBlank
        private String nome;
        @NotBlank
        private String cpf;
        @NotNull
        @PastOrPresent
        private LocalDate dataNascimento;
        private String telefone;
        private String endereco;
        @Email
        private String email;

        public String getNome() {
            return nome;
        }

        public void setNome(String nome) {
            this.nome = nome;
        }

        public String getCpf() {
            return cpf;
        }

        public void setCpf(String cpf) {
            this.cpf = cpf;
        }

        public LocalDate getDataNascimento() {
            return dataNascimento;
        }

        public void setDataNascimento(LocalDate dataNascimento) {
            this.dataNascimento = dataNascimento;
        }

        public String getTelefone() {
            return telefone;
        }

        public void setTelefone(String telefone) {
            this.telefone = telefone;
        }

        public String getEndereco() {
            return endereco;
        }

        public void setEndereco(String endereco) {
            this.endereco = endereco;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }

    public static class NovoProfissional {
        @NotNull
        private Categoria categoria;
        @NotBlank
        private String nome;
        @NotBlank
        private String registro;
        @NotBlank
        private String telefone;
        @NotBlank
        @Email
        private String email;
        private String especialidade;
        private String atuacaoCirurgica;

        public Categoria getCategoria() {
            return categoria;
        }

        public void setCategoria(Categoria categoria) {
            this.categoria = categoria;
        }

        public String getNome() {
            return nome;
        }

        public void setNome(String nome) {
            this.nome = nome;
        }

        public String getRegistro() {
            return registro;
        }

        public void setRegistro(String registro) {
            this.registro = registro;
        }

        public String getTelefone() {
            return telefone;
        }

        public void setTelefone(String telefone) {
            this.telefone = telefone;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getEspecialidade() {
            return especialidade;
        }

        public void setEspecialidade(String especialidade) {
            this.especialidade = especialidade;
        }

        public String getAtuacaoCirurgica() {
            return atuacaoCirurgica;
        }

        public void setAtuacaoCirurgica(String atuacaoCirurgica) {
            this.atuacaoCirurgica = atuacaoCirurgica;
        }
    }

    public static class NovoQuarto {
        @NotBlank
        private String numeroIdentificacao;
        @NotNull
        private Integer andar;
        @NotNull
        @Positive
        private Integer capacidadeMaximaPacientes;
        @NotBlank
        private String tipo;

        public String getNumeroIdentificacao() {
            return numeroIdentificacao;
        }

        public void setNumeroIdentificacao(String numeroIdentificacao) {
            this.numeroIdentificacao = numeroIdentificacao;
        }

        public Integer getAndar() {
            return andar;
        }

        public void setAndar(Integer andar) {
            this.andar = andar;
        }

        public Integer getCapacidadeMaximaPacientes() {
            return capacidadeMaximaPacientes;
        }

        public void setCapacidadeMaximaPacientes(Integer capacidadeMaximaPacientes) {
            this.capacidadeMaximaPacientes = capacidadeMaximaPacientes;
        }

        public String getTipo() {
            return tipo;
        }

        public void setTipo(String tipo) {
            this.tipo = tipo;
        }
    }

    public static class DadosPaciente {
        private final Long id;
        private final String nome;
        private final String cpf;
        private final LocalDate dataNascimento;
        private final String telefone;
        private final String endereco;
        private final String email;
        private final Long historicoMedicoId;

        public DadosPaciente(Paciente paciente) {
            id = paciente.getId();
            nome = paciente.getNome();
            cpf = paciente.getCpf();
            dataNascimento = paciente.getDataNascimento();
            telefone = paciente.getTelefone();
            endereco = paciente.getEndereco();
            email = paciente.getEmail();
            historicoMedicoId = paciente.getHistoricoMedico().getId();
        }

        public Long getId() {
            return id;
        }

        public String getNome() {
            return nome;
        }

        public String getCpf() {
            return cpf;
        }

        public LocalDate getDataNascimento() {
            return dataNascimento;
        }

        public String getTelefone() {
            return telefone;
        }

        public String getEndereco() {
            return endereco;
        }

        public String getEmail() {
            return email;
        }

        public Long getHistoricoMedicoId() {
            return historicoMedicoId;
        }
    }

    public static class DadosProfissional {
        private final Long id;
        private final Categoria categoria;
        private final String nome;
        private final String registro;
        private final String telefone;
        private final String email;
        private final String especialidade;
        private final String atuacaoCirurgica;

        public DadosProfissional(ProfissionalSaude profissional) {
            id = profissional.getId();
            nome = profissional.getNome();
            registro = profissional.getRegistro();
            telefone = profissional.getTelefone();
            email = profissional.getEmail();

            String especialidadeDoProfissional = null;
            String atuacaoDoProfissional = null;
            if (profissional instanceof ClinicoGeral) {
                categoria = Categoria.CLINICO_GERAL;
            } else if (profissional instanceof Especialista) {
                categoria = Categoria.ESPECIALISTA;
                especialidadeDoProfissional = ((Especialista) profissional).getEspecialidade();
            } else if (profissional instanceof Cirurgiao) {
                categoria = Categoria.CIRURGIAO;
                atuacaoDoProfissional = ((Cirurgiao) profissional).getAtuacaoCirurgica();
            } else {
                throw new IllegalStateException("Categoria profissional desconhecida.");
            }
            especialidade = especialidadeDoProfissional;
            atuacaoCirurgica = atuacaoDoProfissional;
        }

        public Long getId() {
            return id;
        }

        public Categoria getCategoria() {
            return categoria;
        }

        public String getNome() {
            return nome;
        }

        public String getRegistro() {
            return registro;
        }

        public String getTelefone() {
            return telefone;
        }

        public String getEmail() {
            return email;
        }

        public String getEspecialidade() {
            return especialidade;
        }

        public String getAtuacaoCirurgica() {
            return atuacaoCirurgica;
        }
    }

    public static class DadosQuarto {
        private final String numeroIdentificacao;
        private final int andar;
        private final int capacidadeMaximaPacientes;
        private final String tipo;
        private final boolean disponivel;
        private final ArrayList<Long> pacientes;

        public DadosQuarto(Quarto quarto) {
            numeroIdentificacao = quarto.getNumeroIdentificacao();
            andar = quarto.getAndar();
            capacidadeMaximaPacientes = quarto.getCapacidadeMaximaPacientes();
            tipo = quarto.getTipo();
            disponivel = quarto.verificarDisponibilidade();
            pacientes = new ArrayList<>();
            for (Paciente paciente : quarto.getPacientes()) {
                pacientes.add(paciente.getId());
            }
        }

        public String getNumeroIdentificacao() {
            return numeroIdentificacao;
        }

        public int getAndar() {
            return andar;
        }

        public int getCapacidadeMaximaPacientes() {
            return capacidadeMaximaPacientes;
        }

        public String getTipo() {
            return tipo;
        }

        public boolean isDisponivel() {
            return disponivel;
        }

        public ArrayList<Long> getPacientes() {
            return pacientes;
        }
    }
}
