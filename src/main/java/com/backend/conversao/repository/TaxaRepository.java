package com.backend.conversao.repository;

import com.backend.conversao.model.Moeda;
import com.backend.conversao.model.Taxa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaxaRepository extends JpaRepository<Taxa, Long> {
    Taxa findByMoeda(Moeda moeda);
}
