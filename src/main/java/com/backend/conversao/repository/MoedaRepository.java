package com.backend.conversao.repository;

import com.backend.conversao.model.Moeda;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MoedaRepository extends JpaRepository<Moeda, Long> {
    Moeda findByCodigo(String codigo);
}
