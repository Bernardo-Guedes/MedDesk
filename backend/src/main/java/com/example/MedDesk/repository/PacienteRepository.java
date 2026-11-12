package com.example.MedDesk.repository;

import com.example.MedDesk.model.Paciente;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class PacienteRepository extends RepositorioEmMemoria<Paciente, UUID> {

    @Override
    protected UUID obterId(Paciente paciente) {
        return paciente.getId();
    }

    public Optional<Paciente> findByCpf(String cpf) {
        return dados.values().stream()
                .filter(p -> p.getCpf().equals(cpf))
                .findFirst();
    }
}