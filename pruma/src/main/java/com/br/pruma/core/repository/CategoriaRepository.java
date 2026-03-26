package com.br.pruma.core.repository;

import com.br.pruma.core.domain.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {
    List<Categoria> findByNomeContainingIgnoreCase(String nome);
}
