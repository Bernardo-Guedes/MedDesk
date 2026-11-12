package com.example.MedDesk.model;

import com.example.MedDesk.exception.RecursoNaoEncontradoException;
import com.example.MedDesk.exception.RegraNegocioException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

public class HistoricoMedico {

    private final UUID id;
    private final List<Atendimento> atendimentos;
    private final List<String> informacoesRelevantes;

    public HistoricoMedico() {
        this.id = UUID.randomUUID();
        this.atendimentos = new ArrayList<>();
        this.informacoesRelevantes = new ArrayList<>();
    }

    public List<Atendimento> consultarHistorico() {
        List<Atendimento> ordenados = new ArrayList<>(atendimentos);
        ordenados.sort(Comparator.comparing(Atendimento::getData)
                .thenComparing(Atendimento::getHorario));
        return Collections.unmodifiableList(ordenados);
    }

    public void adicionarAtendimento(Atendimento atendimento) {
        if (atendimento == null) {
            throw new RegraNegocioException("Atendimento inválido");
        }
        if (atendimentos.contains(atendimento)) {
            throw new RegraNegocioException("Atendimento já registrado no histórico");
        }
        atendimentos.add(atendimento);
    }

    public void removerAtendimento(Atendimento atendimento) {
        if (!atendimentos.remove(atendimento)) {
            throw new RecursoNaoEncontradoException("Atendimento não encontrado no histórico");
        }
    }

    public Atendimento buscarAtendimento(UUID id) {
        return atendimentos.stream()
                .filter(a -> a.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RecursoNaoEncontradoException("Atendimento não encontrado no histórico"));
    }

    public void adicionarInformacaoRelevante(String informacao) {
        if (informacao == null || informacao.isBlank()) {
            throw new RegraNegocioException("A informação relevante não pode ser vazia");
        }
        informacoesRelevantes.add(informacao);
    }


    public UUID getId() { return id; }

    public List<Atendimento> getAtendimentos() { return consultarHistorico(); }

    public List<String> getInformacoesRelevantes() {
        return Collections.unmodifiableList(informacoesRelevantes);
    }
}
