package com.backend.conversao.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ConversaoDTO {

    @NotBlank(message = "Moeda de origem é obrigatória")
    private String moedaOrigem;

    @NotBlank(message = "Moeda de destino é obrigatória")
    private String moedaDestino;

    @NotNull(message = "Valor é obrigatório")
    @Positive(message = "Valor deve ser positivo")
    private BigDecimal valor;
}
