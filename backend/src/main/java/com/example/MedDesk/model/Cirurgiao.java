package com.example.MedDesk.model;

import com.example.MedDesk.exception.RecursoNaoEncontradoException;
import com.example.MedDesk.exception.RegraNegocioException;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Cirurgiao extends ProfissionalSaude {

    private String atuacaoCirurgica;
    private final List<ProcedimentoCirurgico> procedimentosCirurgicos;

    public Cirurgiao(String nome, String registroProfissional, String telefone, String email,
                     String atuacaoCirurgica) {
        super(nome, registroProfissional, telefone, email);
        this.procedimentosCirurgicos = new ArrayList<>();
        setAtuacaoCirurgica(atuacaoCirurgica);
    }

    public void adicionarProcedimentoCirurgico(ProcedimentoCirurgico procedimentoCirurgico) {
        if (procedimentoCirurgico == null) {
            throw new RegraNegocioException("Procedimento cirúrgico inválido");
        }
        if (procedimentoCirurgico.getProfissionalSaude() != this) {
            throw new RegraNegocioException("O procedimento pertence a outro cirurgião");
        }
        if (procedimentosCirurgicos.contains(procedimentoCirurgico)) {
            throw new RegraNegocioException("Procedimento já registrado para este cirurgião");
        }
        procedimentosCirurgicos.add(procedimentoCirurgico);
    }

    public void removerProcedimentoCirurgico(ProcedimentoCirurgico procedimentoCirurgico) {
        if (!procedimentosCirurgicos.remove(procedimentoCirurgico)) {
            throw new RecursoNaoEncontradoException("Procedimento não encontrado para este cirurgião");
        }
    }

    @Override
    public String exibirInformacoes() {
        return super.exibirInformacoes() + " | Atuação cirúrgica: " + atuacaoCirurgica
                + " | Procedimentos: " + procedimentosCirurgicos.size();
    }

    public String getAtuacaoCirurgica() { return atuacaoCirurgica; }

    @JsonIgnore
    public List<ProcedimentoCirurgico> getProcedimentosCirurgicos() {
        return Collections.unmodifiableList(procedimentosCirurgicos);
    }

    public void setAtuacaoCirurgica(String atuacaoCirurgica) {
        if (atuacaoCirurgica == null || atuacaoCirurgica.isBlank()) {
            throw new RegraNegocioException("A atuação cirúrgica é obrigatória para o cirurgião");
        }
        this.atuacaoCirurgica = atuacaoCirurgica.trim();
    }
}
