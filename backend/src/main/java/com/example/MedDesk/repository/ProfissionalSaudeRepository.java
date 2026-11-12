package com.example.MedDesk.repository;

import com.example.MedDesk.model.ProfissionalSaude;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class ProfissionalSaudeRepository extends RepositorioEmMemoria<ProfissionalSaude, UUID> {

    @Override
    protected UUID obterId(ProfissionalSaude profissional) {
        return profissional.getId();
    }

    public Optional<ProfissionalSaude> findByRegistroProfissional(String registroProfissional) {
        return dados.values().stream()
                .filter(p -> p.getRegistroProfissional().equalsIgnoreCase(registroProfissional))
                .findFirst();
    }
}