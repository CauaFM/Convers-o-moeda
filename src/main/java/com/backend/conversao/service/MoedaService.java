package com.backend.conversao.service;

import com.backend.conversao.exception.MoedaNotFoundException;
import com.backend.conversao.model.Moeda;
import com.backend.conversao.repository.MoedaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MoedaService {

    private static final Logger logger = LoggerFactory.getLogger(MoedaService.class);

    private final MoedaRepository repository;

    public MoedaService(MoedaRepository repository) {
        this.repository = repository;
    }

    public Moeda salvar(Moeda moeda) {
        logger.info("Salvando moeda: código={}, nome={}", moeda.getCodigo(), moeda.getNome());
        Moeda salvo = repository.save(moeda);
        logger.info("Moeda salva com sucesso: id={}", salvo.getId());
        return salvo;
    }

    public Moeda buscarPorCodigo(String codigo) {
        logger.info("Buscando moeda pelo código: {}", codigo);
        return Optional.ofNullable(repository.findByCodigo(codigo))
                .orElseThrow(() -> {
                    logger.error("Moeda não encontrada para o código: {}", codigo);
                    return new MoedaNotFoundException(codigo);
                });
    }

    public Moeda atualizar(Long id, Moeda nova) {
        logger.info("Atualizando moeda com id={}", id);
        Moeda moeda = repository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Moeda não encontrada para atualizar com id: {}", id);
                    return new MoedaNotFoundException("ID: " + id);
                });
        moeda.setNome(nova.getNome());
        moeda.setCodigo(nova.getCodigo());
        Moeda atualizado = repository.save(moeda);
        logger.info("Moeda atualizada com sucesso: id={}", atualizado.getId());
        return atualizado;
    }

    public void deletar(Long id) {
        logger.info("Deletando moeda com id={}", id);
        if (!repository.existsById(id)) {
            logger.error("Moeda não encontrada para deletar com id: {}", id);
            throw new MoedaNotFoundException("ID: " + id);
        }
        repository.deleteById(id);
        logger.info("Moeda deletada com sucesso: id={}", id);
    }
}
