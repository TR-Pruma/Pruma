package com.br.pruma.application.service.impl;

import com.br.pruma.application.dto.request.PreObraRequestDTO;
import com.br.pruma.application.dto.response.PreObraResponseDTO;
import com.br.pruma.application.dto.update.PreObraUpdateDTO;
import com.br.pruma.application.mapper.PreObraMapper;
import com.br.pruma.application.service.PreObraService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PreObraServiceImpl implements PreObraService {

    private final PreObraRepositoryPort posObraRepositoryPort;
    private final PreObraMapper posObraMapper;

    @Override
    public PreObraResponseDTO create(PreObraRequestDTO dto) {
        return null;
    }

    @Override
    public PreObraResponseDTO getById(Integer id) {
        return null;
    }

    @Override
    public List<PreObraResponseDTO> listAll() {
        return List.of();
    }

    @Override
    public Page<PreObraResponseDTO> list(Pageable pageable) {
        return null;
    }

    @Override
    public List<PreObraResponseDTO> listByProjeto(Integer projetoId) {
        return List.of();
    }

    @Override
    public PreObraResponseDTO update(Integer id, PreObraUpdateDTO dto) {
        return null;
    }

    @Override
    public PreObraResponseDTO replace(Integer id, PreObraRequestDTO dto) {
        return null;
    }

    @Override
    public void delete(Integer id) {

    }
}