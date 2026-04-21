package com.br.pruma.adapters.out.persistence;

import com.br.pruma.core.domain.Anexo;
import com.br.pruma.core.repository.AnexoRepository;
import com.br.pruma.core.repository.port.AnexoRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AnexoRepositoryAdapter implements AnexoRepositoryPort {

    private final AnexoRepository anexoRepository;

    @Override
    public Anexo save(Anexo anexo) {
        return anexoRepository.save(anexo);
    }

    @Override
    public Optional<Anexo> findById(Integer id) {
        return anexoRepository.findById(id);
    }

    @Override
    public List<Anexo> findAll() {
        return anexoRepository.findAll();
    }

    @Override
    public Page<Anexo> findAll(Pageable pageable) {
        return anexoRepository.findAll(pageable);
    }

    @Override
    public void deleteById(Integer id) {
        anexoRepository.softDelete(id);
    }

    @Override
    public void delete(Anexo anexo) {
        anexoRepository.delete(anexo);
    }

    @Override
    public boolean existsById(Integer id) {
        return anexoRepository.existsById(id);
    }

    @Override
    public Optional<Anexo> findByIdAndAtivoTrue(Integer id) {
        return anexoRepository.findByIdAndAtivoTrue(id);
    }

    @Override
    public List<Anexo> findAllByProjetoIdAndAtivoTrue(Integer projetoId) {
        return anexoRepository.findAllByProjetoIdAndAtivoTrue(projetoId);
    }

    @Override
    public Page<Anexo> findAllByAtivoTrue(Pageable pageable) {
        return anexoRepository.findAllByAtivoTrue(pageable);
    }

    @Override
    public void softDelete(Integer id) {
        anexoRepository.softDelete(id);
    }

}
