package com.backend.conversao.service;

import com.backend.conversao.exception.MoedaNotFoundException;
import com.backend.conversao.model.Moeda;
import com.backend.conversao.repository.MoedaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MoedaServiceTest {

    @InjectMocks
    private MoedaService moedaService;

    @Mock
    private MoedaRepository moedaRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void salvar_DeveSalvarMoedaComSucesso() {
        Moeda moeda = new Moeda();
        moeda.setCodigo("USD");
        moeda.setNome("Dólar Americano");

        Moeda moedaSalva = new Moeda();
        moedaSalva.setId(1L);
        moedaSalva.setCodigo("USD");
        moedaSalva.setNome("Dólar Americano");

        when(moedaRepository.save(moeda)).thenReturn(moedaSalva);

        Moeda resultado = moedaService.salvar(moeda);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        verify(moedaRepository, times(1)).save(moeda);
    }

    @Test
    void buscarPorCodigo_DeveRetornarMoedaQuandoExiste() {
        Moeda moeda = new Moeda();
        moeda.setId(1L);
        moeda.setCodigo("USD");
        moeda.setNome("Dólar Americano");

        when(moedaRepository.findByCodigo("USD")).thenReturn(moeda);

        Moeda resultado = moedaService.buscarPorCodigo("USD");

        assertNotNull(resultado);
        assertEquals("USD", resultado.getCodigo());
    }

    @Test
    void buscarPorCodigo_DeveLancarExcecaoQuandoNaoEncontrar() {
        when(moedaRepository.findByCodigo("XYZ")).thenReturn(null);

        assertThrows(MoedaNotFoundException.class, () -> moedaService.buscarPorCodigo("XYZ"));
    }

    @Test
    void atualizar_DeveAtualizarMoedaQuandoExiste() {
        Moeda moedaExistente = new Moeda();
        moedaExistente.setId(1L);
        moedaExistente.setCodigo("USD");
        moedaExistente.setNome("Dólar");

        Moeda novaMoeda = new Moeda();
        novaMoeda.setCodigo("USD");
        novaMoeda.setNome("Dólar Atualizado");

        when(moedaRepository.findById(1L)).thenReturn(Optional.of(moedaExistente));
        when(moedaRepository.save(moedaExistente)).thenReturn(moedaExistente);

        Moeda resultado = moedaService.atualizar(1L, novaMoeda);

        assertEquals("Dólar Atualizado", resultado.getNome());
    }

    @Test
    void deletar_DeveExcluirMoedaQuandoExiste() {
        when(moedaRepository.existsById(1L)).thenReturn(true);

        moedaService.deletar(1L);

        verify(moedaRepository).deleteById(1L);
    }

    @Test
    void deletar_DeveLancarExcecaoQuandoNaoExiste() {
        when(moedaRepository.existsById(99L)).thenReturn(false);

        assertThrows(MoedaNotFoundException.class, () -> moedaService.deletar(99L));
    }
}