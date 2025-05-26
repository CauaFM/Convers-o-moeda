package com.backend.conversao.service;

import com.backend.conversao.dto.ConversaoDTO;
import com.backend.conversao.model.Conversao;
import com.backend.conversao.model.Moeda;
import com.backend.conversao.model.Taxa;
import com.backend.conversao.repository.ConversaoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ConversaoServiceTest {

    @Mock
    private MoedaService moedaService;

    @Mock
    private TaxaService taxaService;

    @Mock
    private ConversaoRepository conversaoRepository;

    @InjectMocks
    private ConversaoService conversaoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testConverter() {
        ConversaoDTO dto = new ConversaoDTO();
        dto.setMoedaOrigem("USD");
        dto.setMoedaDestino("BRL");
        dto.setValor(BigDecimal.valueOf(100));

        Moeda moedaOrigem = new Moeda();
        moedaOrigem.setId(1L);
        moedaOrigem.setCodigo("USD");
        moedaOrigem.setNome("Dólar");

        Moeda moedaDestino = new Moeda();
        moedaDestino.setId(2L);
        moedaDestino.setCodigo("BRL");
        moedaDestino.setNome("Real");

        Taxa taxaOrigem = new Taxa();
        taxaOrigem.setMoeda(moedaOrigem);
        taxaOrigem.setTaxaReferencia(BigDecimal.valueOf(1));
        taxaOrigem.setIof(BigDecimal.valueOf(0.38));
        taxaOrigem.setSpread(BigDecimal.valueOf(1));

        Taxa taxaDestino = new Taxa();
        taxaDestino.setMoeda(moedaDestino);
        taxaDestino.setTaxaReferencia(BigDecimal.valueOf(5));
        taxaDestino.setIof(BigDecimal.valueOf(1));
        taxaDestino.setSpread(BigDecimal.valueOf(2));

        when(moedaService.buscarPorCodigo("USD")).thenReturn(moedaOrigem);
        when(moedaService.buscarPorCodigo("BRL")).thenReturn(moedaDestino);
        when(taxaService.buscarPorMoeda("USD")).thenReturn(taxaOrigem);
        when(taxaService.buscarPorMoeda("BRL")).thenReturn(taxaDestino);
        when(conversaoRepository.save(any(Conversao.class))).thenAnswer(i -> i.getArgument(0));

        Conversao resultado = conversaoService.converter(dto);

        assertNotNull(resultado);
        assertEquals(BigDecimal.valueOf(100), resultado.getValor());
        assertEquals("USD", resultado.getMoedaOrigem().getCodigo());
        assertEquals("BRL", resultado.getMoedaDestino().getCodigo());
        assertNotNull(resultado.getValorConvertido());
        assertNotNull(resultado.getDataHora());

        verify(conversaoRepository, times(1)).save(any(Conversao.class));
    }
}