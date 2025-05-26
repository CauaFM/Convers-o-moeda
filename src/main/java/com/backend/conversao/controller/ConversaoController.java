package com.backend.conversao.controller;

import com.backend.conversao.dto.ConversaoDTO;
import com.backend.conversao.model.Conversao;
import com.backend.conversao.service.ConversaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/conversao")
@RequiredArgsConstructor
public class ConversaoController {

    private final ConversaoService service;
    private static final Logger logger = LoggerFactory.getLogger(ConversaoController.class);

    @PostMapping
    @Operation(summary = "Realiza a conversão de moedas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Conversão realizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida")
    })
    public Conversao converter(@RequestBody @Valid ConversaoDTO dto) {
        logger.info("Requisição para conversão de moeda");
        return service.converter(dto);
    }
}
