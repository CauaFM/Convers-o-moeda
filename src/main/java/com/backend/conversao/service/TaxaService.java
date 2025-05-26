package com.backend.conversao.service;

import com.backend.conversao.exception.MoedaNotFoundException;
import com.backend.conversao.model.Moeda;
import com.backend.conversao.model.Taxa;
import com.backend.conversao.repository.MoedaRepository;
import com.backend.conversao.repository.TaxaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class TaxaService {

    private static final Logger logger = LoggerFactory.getLogger(TaxaService.class);

    private final TaxaRepository taxaRepository;
    private final MoedaRepository moedaRepository;

    public TaxaService(TaxaRepository taxaRepository, MoedaRepository moedaRepository) {
        this.taxaRepository = taxaRepository;
        this.moedaRepository = moedaRepository;
    }

    public Taxa buscarPorMoeda(String codigo) {
        logger.info("Buscando taxa para moeda: {}", codigo);
        Moeda moeda = moedaRepository.findByCodigo(codigo);
        if (moeda == null) {
            logger.error("Moeda não encontrada para o código: {}", codigo);
            throw new MoedaNotFoundException(codigo);
        }
        Taxa taxa = taxaRepository.findByMoeda(moeda);
        logger.info("Taxa encontrada para moeda {}: id={}", codigo, taxa.getId());
        return taxa;
    }

    public Taxa salvar(Taxa taxa) {
        logger.info("Salvando taxa para moeda: id={}", taxa.getMoeda().getId());

        Moeda moedaCompleta = moedaRepository.findById(taxa.getMoeda().getId())
                .orElseThrow(() -> new MoedaNotFoundException("ID: " + taxa.getMoeda().getId()));

        taxa.setMoeda(moedaCompleta);

        Taxa salva = taxaRepository.save(taxa);
        logger.info("Taxa salva com sucesso: id={}", salva.getId());
        return salva;
    }

    public Taxa atualizar(Long id, Taxa novaTaxa) {
        Taxa taxaExistente = taxaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Taxa não encontrada!"));

        taxaExistente.setTaxaReferencia(novaTaxa.getTaxaReferencia());
        taxaExistente.setIof(novaTaxa.getIof());
        taxaExistente.setSpread(novaTaxa.getSpread());

        // Se quiser permitir atualizar a moeda:
        if (novaTaxa.getMoeda() != null) {
            Moeda moedaCompleta = moedaRepository.findById(novaTaxa.getMoeda().getId())
                    .orElseThrow(() -> new MoedaNotFoundException("ID: " + novaTaxa.getMoeda().getId()));
            taxaExistente.setMoeda(moedaCompleta);
        }

        Taxa atualizada = taxaRepository.save(taxaExistente);
        logger.info("Taxa atualizada com sucesso. ID={}", atualizada.getId());
        return atualizada;
    }

    public void deletar(Long id) {
        Taxa taxa = taxaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Taxa não encontrada!"));
        taxaRepository.delete(taxa);
        logger.info("Taxa deletada com sucesso. ID={}", id);
    }
}
