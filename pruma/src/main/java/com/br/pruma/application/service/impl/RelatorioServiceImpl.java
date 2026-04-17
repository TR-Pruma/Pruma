package com.br.pruma.application.service.impl;

import com.br.pruma.application.dto.request.ProjetoCategoriaRequestDTO;
import com.br.pruma.application.dto.response.ProjetoCategoriaResponseDTO;
import com.br.pruma.application.dto.update.ProjetoCategoriaUpdateDTO;
import com.br.pruma.application.service.ProjetoCategoriaService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class RelatorioServiceImpl implements ProjetoCategoriaService {
    @Override
    public ProjetoCategoriaResponseDTO create(ProjetoCategoriaRequestDTO dto) {
        return null;
    }

    @Override
    public ProjetoCategoriaResponseDTO getById(Integer id) {
        return null;
    }

    @Override
    public List<ProjetoCategoriaResponseDTO> listAll() {
        return List.of();
    }

    @Override
    public Page<ProjetoCategoriaResponseDTO> list(Pageable pageable) {
        return null;
    }

    @Override
    public List<ProjetoCategoriaResponseDTO> listByProjeto(Integer projetoId) {
        return List.of();
    }

    @Override
    public ProjetoCategoriaResponseDTO update(Integer id, ProjetoCategoriaUpdateDTO dto) {
        return null;
    }

    @Override
    public void delete(Integer id) {

    }
}
