package com.br.pruma.application.service.impl;

import com.br.pruma.application.dto.request.PosObraRequestDTO;
import com.br.pruma.application.dto.response.PosObraResponseDTO;
import com.br.pruma.application.dto.update.PosObraUpdateDTO;
import com.br.pruma.application.service.PosObraService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class PosObraServiceImpl implements PosObraService {
    @Override
    public PosObraResponseDTO create(PosObraRequestDTO dto) {
        return null;
    }

    @Override
    public PosObraResponseDTO getById(Integer id) {
        return null;
    }

    @Override
    public List<PosObraResponseDTO> listAll() {
        return List.of();
    }

    @Override
    public Page<PosObraResponseDTO> list(Pageable pageable) {
        return null;
    }

    @Override
    public List<PosObraResponseDTO> listByProjeto(Integer projetoId) {
        return List.of();
    }

    @Override
    public PosObraResponseDTO update(Integer id, PosObraUpdateDTO dto) {
        return null;
    }

    @Override
    public PosObraResponseDTO replace(Integer id, PosObraRequestDTO dto) {
        return null;
    }

    @Override
    public void delete(Integer id) {

    }
}
