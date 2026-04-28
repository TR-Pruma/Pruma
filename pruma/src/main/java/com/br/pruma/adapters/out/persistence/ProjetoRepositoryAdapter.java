package com.br.pruma.adapters.out.persistence;

import com.br.pruma.core.domain.Projeto;
import com.br.pruma.core.repository.ProjetoRepository;
import com.br.pruma.core.repository.port.ProjetoRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Adapter de saída que implementa {@link ProjetoRepositoryPort}
 * delegando ao {@link ProjetoRepository} do Spring Data JPA.
 */
@Component
@RequiredArgsConstructor
public class ProjetoRepositoryAdapter implements ProjetoRepositoryPort {

    private final ProjetoRepository projetoRepository;

    @Override
    public Projeto save(Projeto projeto) {
        return projetoRepository.save(projeto);
    }

    @Override
    public Optional<Projeto> findById(Integer id) {
        return projetoRepository.findById(id);
    }

    @Override
    public List<Projeto> findAll() {
        return projetoRepository.findAll();
    }

    @Override
    public Page<Projeto> findAll(Pageable pageable) {
        return projetoRepository.findAll(pageable);
    }

    @Override
    public List<Projeto> findAllByNomeContainingIgnoreCase(String nome) {
        return projetoRepository.findAllByNomeContainingIgnoreCase(nome);
    }

    @Override
    public Optional<Projeto> findByNome(String nome) {
        return projetoRepository.findByNome(nome);
    }

    @Override
    public boolean existsByNome(String nome) {
        return projetoRepository.existsByNome(nome);
    }

    @Override
    public Page<Projeto> findAllByDataCriacaoBetween(LocalDate start, LocalDate end, Pageable pageable) {
        return projetoRepository.findAllByDataCriacaoBetween(start, end, pageable);
    }

    @Override
    public Page<Projeto> findAllByDataCriacao(LocalDate date, Pageable pageable) {
        return projetoRepository.findAllByDataCriacao(date, pageable);
    }

    @Override
    public Optional<Projeto> findByIdAndAtivoTrue(Integer projetoId) {
        return projetoRepository.findByIdAndAtivoTrue(projetoId);
    }

    @Override
    public boolean existsByIdAndAtivoTrue(Integer projetoId) {
        return projetoRepository.existsByIdAndAtivoTrue(projetoId);
    }

    @Override
    public void deleteById(Integer id) {
        projetoRepository.deleteById(id);
    }

    @Override
    public void delete(Projeto projeto) {
        projetoRepository.delete(projeto);
    }

    @Override
    public boolean existsById(Integer id) {
        return projetoRepository.existsById(id);
    }
}
