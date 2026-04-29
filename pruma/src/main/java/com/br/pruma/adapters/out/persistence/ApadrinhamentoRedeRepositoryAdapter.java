package com.br.pruma.adapters.out.persistence;

import com.br.pruma.core.domain.ApadrinhamentoRede;
import com.br.pruma.core.repository.ApadrinhamentoRedeRepository;
import com.br.pruma.core.repository.port.ApadrinhamentoRedeRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ApadrinhamentoRedeRepositoryAdapter implements ApadrinhamentoRedeRepositoryPort {

    private final ApadrinhamentoRedeRepository apadrinhamentoRedeRepository;

    @Override
    public ApadrinhamentoRede save(ApadrinhamentoRede apadrinhamentoRede) {
        return apadrinhamentoRedeRepository.save(apadrinhamentoRede);
    }

    @Override
    public Optional<ApadrinhamentoRede> findById(Long id) {
        return apadrinhamentoRedeRepository.findById(id);
    }

    @Override
    public List<ApadrinhamentoRede> findAll() {
        return apadrinhamentoRedeRepository.findAll();
    }

    @Override
    public Page<ApadrinhamentoRede> findAll(Pageable pageable) {
        return apadrinhamentoRedeRepository.findAll(pageable);
    }

    @Override
    public List<ApadrinhamentoRede> findAllByPadrinhoId(Long padrinhoId) {
        return apadrinhamentoRedeRepository.findAllByPadrinhoId(padrinhoId);
    }

    @Override
    public List<ApadrinhamentoRede> findAllByAfilhadoId(Long afilhadoId) {
        return apadrinhamentoRedeRepository.findAllByAfilhadoId(afilhadoId);
    }

    @Override
    public List<ApadrinhamentoRede> findAllByStatus(String status) {
        return apadrinhamentoRedeRepository.findAllByStatus(status);
    }

    @Override
    public boolean existsByPadrinhoIdAndAfilhadoIdAndStatus(Long padrinhoId, Long afilhadoId, String status) {
        return apadrinhamentoRedeRepository.existsByPadrinhoIdAndAfilhadoIdAndStatus(padrinhoId, afilhadoId, status);
    }

    @Override
    public void deleteById(Long id) {
        apadrinhamentoRedeRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return apadrinhamentoRedeRepository.existsById(id);
    }
}