package com.example.MedDesk.repository;

import com.example.MedDesk.model.Atendimento;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class AtendimentoRepository extends RepositorioEmMemoria<Atendimento, UUID> {

    @Override
    protected UUID obterId(Atendimento atendimento) {
        return atendimento.getId();
    }

    public <T extends Atendimento> List<T> findAllByTipo(Class<T> tipo) {
        return dados.values().stream()
                .filter(tipo::isInstance)
                .map(tipo::cast)
                .toList();
    }

    public List<Atendimento> findByProfissionalId(UUID profissionalId) {
        return dados.values().stream()
                .filter(a -> a.getProfissionalSaude().getId().equals(profissionalId))
                .toList();
    }
}