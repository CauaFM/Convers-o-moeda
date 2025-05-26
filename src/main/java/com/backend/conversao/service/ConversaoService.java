package com.backend.conversao.service;

import com.backend.conversao.dto.ConversaoDTO;
import com.backend.conversao.model.Conversao;
import com.backend.conversao.model.Moeda;
import com.backend.conversao.model.Taxa;
import com.backend.conversao.repository.ConversaoRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ConversaoService {

    private static final Logger logger = LoggerFactory.getLogger(ConversaoService.class);

    private final ConversaoRepository conversaoRepository;
    private final MoedaService moedaService;
    private final TaxaService taxaService;

    public Conversao converter(ConversaoDTO dto) {
        logger.info("Iniciando conversão de {} {} para {}", dto.getValor(), dto.getMoedaOrigem(), dto.getMoedaDestino());

        Moeda origem = moedaService.buscarPorCodigo(dto.getMoedaOrigem());
        Moeda destino = moedaService.buscarPorCodigo(dto.getMoedaDestino());

        Taxa taxaOrigem = taxaService.buscarPorMoeda(dto.getMoedaOrigem());
        Taxa taxaDestino = taxaService.buscarPorMoeda(dto.getMoedaDestino());

        // Ajustando os percentuais (de 0.38 para 0.0038, etc.)
        BigDecimal iofPercent = taxaOrigem.getIof().divide(BigDecimal.valueOf(100));
        BigDecimal spreadPercent = taxaOrigem.getSpread().divide(BigDecimal.valueOf(100));

        // Calculando o fator final
        BigDecimal fatorFinal = BigDecimal.ONE
                .add(iofPercent)
                .multiply(BigDecimal.ONE.add(spreadPercent));

        // Calculando o valor convertido
        BigDecimal valorConvertido = dto.getValor()
                .multiply(taxaOrigem.getTaxaReferencia())
                .multiply(fatorFinal);

        Conversao conversao = new Conversao();
        conversao.setMoedaOrigem(origem);
        conversao.setMoedaDestino(destino);
        conversao.setValor(dto.getValor());
        conversao.setValorConvertido(valorConvertido);
        conversao.setDataHora(LocalDateTime.now());

        Conversao salva = conversaoRepository.save(conversao);
        logger.info("Conversão salva com sucesso. ID={}", salva.getId());
        return salva;
    }
}
