package com.backend.conversao.repository;

import com.backend.conversao.model.Conversao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConversaoRepository extends JpaRepository<Conversao, Long> {
}
