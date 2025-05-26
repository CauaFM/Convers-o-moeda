package com.backend.conversao.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TaxaDTO {

    @NotBlank(message = "Código da moeda é obrigatório")
    private String moedaCodigo;

    @NotNull(message = "Taxa referência é obrigatória")
    @PositiveOrZero(message = "Taxa referência deve ser maior ou igual a zero")
    private BigDecimal taxaReferencia;

    @NotNull(message = "IOF é obrigatório")
    @PositiveOrZero(message = "IOF deve ser maior ou igual a zero")
    private BigDecimal iof;

    @NotNull(message = "Spread é obrigatório")
    @PositiveOrZero(message = "Spread deve ser maior ou igual a zero")
    private BigDecimal spread;
}
