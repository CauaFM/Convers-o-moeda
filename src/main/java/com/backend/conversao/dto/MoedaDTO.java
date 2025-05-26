package com.backend.conversao.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MoedaDTO {

    @NotBlank(message = "Código da moeda é obrigatório")
    private String codigo;

    @NotNull(message = "Valor é obrigatório")
    private BigDecimal valor;
}
