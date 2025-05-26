package com.backend.conversao.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "conversoes")
@Getter
@Setter
public class Conversao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Moeda moedaOrigem;

    @ManyToOne
    private Moeda moedaDestino;

    private BigDecimal valor;

    private BigDecimal valorConvertido;

    private LocalDateTime dataHora;
}
