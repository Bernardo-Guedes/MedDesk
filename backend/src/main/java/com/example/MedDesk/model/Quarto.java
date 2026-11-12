package com.example.MedDesk.model;

import com.example.MedDesk.exception.RegraNegocioException;
import com.example.MedDesk.model.enums.SituacaoQuarto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Quarto {

    private final int numeroIdentificacao;
    private int andar;
    private int capacidadeMaximaPacientes;
    private SituacaoQuarto situacaoAtual;
    private final List<Paciente> pacientes;

    public Quarto(int numeroIdentificacao, int andar, int capacidadeMaximaPacientes) {
        if (numeroIdentificacao <= 0) {
            throw new RegraNegocioException("O número de identificação do quarto deve ser positivo");
        }
        this.numeroIdentificacao = numeroIdentificacao;
        this.pacientes = new ArrayList<>();
        setAndar(andar);
        setCapacidadeMaximaPacientes(capacidadeMaximaPacientes);
    }

    public String exibirInformacoes() {
        return "Quarto " + numeroIdentificacao + " | Andar: " + andar
                + " | Ocupação: " + pacientes.size() + "/" + capacidadeMaximaPacientes
                + " | Situação: " + situacaoAtual;
    }

    public void adicionarPaciente(Paciente paciente) {
        if (paciente == null) {
            throw new RegraNegocioException("Paciente inválido");
        }
        if (pacientes.contains(paciente)) {
            throw new RegraNegocioException("O paciente já está neste quarto");
        }
        if (quantidadeDisponivel() <= 0) {
            throw new RegraNegocioException("O quarto " + numeroIdentificacao + " atingiu sua capacidade máxima");
        }
        pacientes.add(paciente);
        atualizarSituacao();
    }

    public void removerPaciente(Paciente paciente) {
        if (!pacientes.remove(paciente)) {
            throw new RegraNegocioException("O paciente não está neste quarto");
        }
        atualizarSituacao();
    }

    public int quantidadeDisponivel() {
        return capacidadeMaximaPacientes - pacientes.size();
    }

    private void atualizarSituacao() {
        this.situacaoAtual = quantidadeDisponivel() > 0 ? SituacaoQuarto.DISPONIVEL : SituacaoQuarto.OCUPADO;
    }


    public int getNumeroIdentificacao() { return numeroIdentificacao; }
    public int getAndar() { return andar; }
    public int getCapacidadeMaximaPacientes() { return capacidadeMaximaPacientes; }
    public SituacaoQuarto getSituacaoAtual() { return situacaoAtual; }
    public List<Paciente> getPacientes() { return Collections.unmodifiableList(pacientes); }


    public void setAndar(int andar) {
        if (andar < 0) {
            throw new RegraNegocioException("O andar não pode ser negativo");
        }
        this.andar = andar;
    }

    public void setCapacidadeMaximaPacientes(int capacidadeMaximaPacientes) {
        if (capacidadeMaximaPacientes <= 0) {
            throw new RegraNegocioException("A capacidade máxima deve ser maior que zero");
        }
        if (capacidadeMaximaPacientes < pacientes.size()) {
            throw new RegraNegocioException("A capacidade não pode ser menor que a quantidade de pacientes atualmente no quarto ("
                    + pacientes.size() + ")");
        }
        this.capacidadeMaximaPacientes = capacidadeMaximaPacientes;
        atualizarSituacao();
    }
}

