package com.backend.conversao.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "taxas")
@Getter
@Setter
public class Taxa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Moeda moeda;

    @Column(nullable = false)
    private BigDecimal taxaReferencia;

    @Column(nullable = false)
    private BigDecimal iof;

    @Column(nullable = false)
    private BigDecimal spread;

}
