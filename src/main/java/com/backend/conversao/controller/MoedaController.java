package com.backend.conversao.controller;

import com.backend.conversao.model.Moeda;
import com.backend.conversao.service.MoedaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/moeda")
@RequiredArgsConstructor
public class MoedaController {

    private final MoedaService service;
    private static final Logger logger = LoggerFactory.getLogger(MoedaController.class);

    @PostMapping
    @Operation(summary = "Cria uma nova moeda")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Moeda criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    public Moeda criar(@RequestBody Moeda moeda) {
        logger.info("Requisição para criar moeda");
        return service.salvar(moeda);
    }

    @GetMapping("/codigo/{codigo}")
    @Operation(summary = "Busca uma moeda pelo código")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Moeda encontrada"),
            @ApiResponse(responseCode = "404", description = "Moeda não encontrada")
    })
    public Moeda buscarPorCodigo(
            @Parameter(description = "Código da moeda") @PathVariable String codigo) {
        logger.info("Requisição para buscar moeda por código: {}", codigo);
        return service.buscarPorCodigo(codigo);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza uma moeda pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Moeda atualizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Moeda não encontrada")
    })
    public Moeda atualizar(
            @Parameter(description = "ID da moeda") @PathVariable Long id,
            @RequestBody Moeda nova) {
        logger.info("Requisição para atualizar moeda id: {}", id);
        return service.atualizar(id, nova);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta uma moeda pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Moeda deletada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Moeda não encontrada")
    })
    public void deletar(
            @Parameter(description = "ID da moeda") @PathVariable Long id) {
        logger.info("Requisição para deletar moeda id: {}", id);
        service.deletar(id);
    }
}
