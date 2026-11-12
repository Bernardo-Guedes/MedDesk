package com.example.MedDesk.service;

import com.example.MedDesk.dto.QuartoRequest;
import com.example.MedDesk.exception.RecursoNaoEncontradoException;
import com.example.MedDesk.exception.RegraNegocioException;
import com.example.MedDesk.model.Internacao;
import com.example.MedDesk.model.Quarto;
import com.example.MedDesk.model.enums.SituacaoQuarto;
import com.example.MedDesk.repository.AtendimentoRepository;
import com.example.MedDesk.repository.QuartoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuartoService {

    private final QuartoRepository quartoRepository;
    private final AtendimentoRepository atendimentoRepository;

    public QuartoService(QuartoRepository quartoRepository, AtendimentoRepository atendimentoRepository) {
        this.quartoRepository = quartoRepository;
        this.atendimentoRepository = atendimentoRepository;
    }

    public Quarto cadastrar(QuartoRequest dto) {
        if (quartoRepository.existsById(dto.numeroIdentificacao())) {
            throw new RegraNegocioException("Já existe um quarto com o número " + dto.numeroIdentificacao());
        }
        Quarto quarto = new Quarto(dto.numeroIdentificacao(), dto.andar(), dto.capacidadeMaximaPacientes());
        return quartoRepository.save(quarto);
    }

    public List<Quarto> listar(SituacaoQuarto situacao) {
        return quartoRepository.findAll().stream()
                .filter(q -> situacao == null || q.getSituacaoAtual() == situacao)
                .toList();
    }

    public Quarto buscarPorNumero(int numero) {
        return quartoRepository.findById(numero)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Quarto não encontrado: " + numero));
    }

    public Quarto atualizar(int numero, QuartoRequest dto) {
        Quarto quarto = buscarPorNumero(numero);
        quarto.setAndar(dto.andar());
        quarto.setCapacidadeMaximaPacientes(dto.capacidadeMaximaPacientes());
        return quartoRepository.save(quarto);
    }

    public void remover(int numero) {
        Quarto quarto = buscarPorNumero(numero);
        if (!quarto.getPacientes().isEmpty()) {
            throw new RegraNegocioException("Não é possível remover um quarto com pacientes internados");
        }
        boolean possuiHistorico = atendimentoRepository.findAllByTipo(Internacao.class).stream()
                .anyMatch(i -> i.getQuarto().getNumeroIdentificacao() == numero);
        if (possuiHistorico) {
            throw new RegraNegocioException("Não é possível remover um quarto que possui internações registradas");
        }
        quartoRepository.deleteById(numero);
    }
}