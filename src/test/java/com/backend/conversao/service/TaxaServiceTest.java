package com.backend.conversao.service;

import com.backend.conversao.exception.MoedaNotFoundException;
import com.backend.conversao.model.Moeda;
import com.backend.conversao.model.Taxa;
import com.backend.conversao.repository.MoedaRepository;
import com.backend.conversao.repository.TaxaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TaxaServiceTest {

    @Mock
    private TaxaRepository taxaRepository;

    @Mock
    private MoedaRepository moedaRepository;

    @InjectMocks
    private TaxaService taxaService;

    private Moeda moeda;
    private Taxa taxa;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        moeda = new Moeda();
        moeda.setId(1L);
        moeda.setCodigo("USD");
        moeda.setNome("Dólar");

        taxa = new Taxa();
        taxa.setId(1L);
        taxa.setMoeda(moeda);
        taxa.setTaxaReferencia(BigDecimal.valueOf(5.0));
        taxa.setIof(BigDecimal.valueOf(0.38));
        taxa.setSpread(BigDecimal.valueOf(1.0));
    }

    @Test
    public void testBuscarPorMoeda_ComSucesso() {
        when(moedaRepository.findByCodigo("USD")).thenReturn(moeda);
        when(taxaRepository.findByMoeda(moeda)).thenReturn(taxa);

        Taxa resultado = taxaService.buscarPorMoeda("USD");

        assertNotNull(resultado);
        assertEquals(taxa.getId(), resultado.getId());
        verify(moedaRepository).findByCodigo("USD");
        verify(taxaRepository).findByMoeda(moeda);
    }

    @Test
    public void testBuscarPorMoeda_MoedaNaoEncontrada() {
        when(moedaRepository.findByCodigo("EUR")).thenReturn(null);

        assertThrows(MoedaNotFoundException.class, () -> taxaService.buscarPorMoeda("EUR"));
        verify(moedaRepository).findByCodigo("EUR");
    }

    @Test
    public void testSalvarTaxa() {
        when(moedaRepository.findById(1L)).thenReturn(Optional.of(moeda));
        when(taxaRepository.save(taxa)).thenReturn(taxa);

        Taxa resultado = taxaService.salvar(taxa);

        assertNotNull(resultado);
        assertEquals(taxa.getId(), resultado.getId());
        verify(taxaRepository).save(taxa);
    }
}