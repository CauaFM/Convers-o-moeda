package com.backend.conversao.controller;

import com.backend.conversao.model.Taxa;
import com.backend.conversao.service.TaxaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/taxa")
@RequiredArgsConstructor
public class TaxaController {

    private final TaxaService service;
    private static final Logger logger = LoggerFactory.getLogger(TaxaController.class);

    @PostMapping
    @Operation(summary = "Cria uma nova taxa de conversão")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Taxa criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    public Taxa criar(@RequestBody Taxa taxa) {
        logger.info("Requisição para criar taxa");
        return service.salvar(taxa);
    }

    @GetMapping("/moeda/{codigo}")
    @Operation(summary = "Busca taxa por código da moeda")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Taxa encontrada"),
            @ApiResponse(responseCode = "404", description = "Taxa não encontrada")
    })
    public Taxa buscarPorMoeda(
            @Parameter(description = "Código da moeda") @PathVariable String codigo) {
        logger.info("Requisição para buscar taxa por moeda: {}", codigo);
        return service.buscarPorMoeda(codigo);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza uma taxa pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Taxa atualizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Taxa não encontrada")
    })
    public Taxa atualizar(
            @Parameter(description = "ID da taxa") @PathVariable Long id,
            @RequestBody Taxa novaTaxa) {
        logger.info("Requisição para atualizar taxa id: {}", id);
        return service.atualizar(id, novaTaxa);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta uma taxa pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Taxa deletada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Taxa não encontrada")
    })
    public void deletar(
            @Parameter(description = "ID da taxa") @PathVariable Long id) {
        logger.info("Requisição para deletar taxa id: {}", id);
        service.deletar(id);
    }
}
