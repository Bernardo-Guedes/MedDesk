package com.example.MedDesk.repository;

import com.example.MedDesk.model.Quarto;
import org.springframework.stereotype.Repository;

@Repository
public class QuartoRepository extends RepositorioEmMemoria<Quarto, Integer> {

    @Override
    protected Integer obterId(Quarto quarto) {
        return quarto.getNumeroIdentificacao();
    }
}