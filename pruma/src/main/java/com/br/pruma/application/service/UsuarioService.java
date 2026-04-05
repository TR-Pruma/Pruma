package com.br.pruma.application.service;

import com.br.pruma.application.dto.request.CadastroRequestDTO;
import com.br.pruma.application.dto.request.LoginRequestDTO;
import com.br.pruma.application.dto.request.UsuarioRequestDTO;
import com.br.pruma.application.dto.response.TokenResponseDTO;
import com.br.pruma.application.dto.response.UsuarioResponseDTO;
import com.br.pruma.application.dto.update.UsuarioUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UsuarioService {

    // --- autenticacao ---
    TokenResponseDTO autenticar(LoginRequestDTO dto);
    void cadastrar(CadastroRequestDTO dto);

    // --- CRUD ---
    UsuarioResponseDTO create(UsuarioRequestDTO dto);
    UsuarioResponseDTO getById(Integer id);
    List<UsuarioResponseDTO> listAll();
    Page<UsuarioResponseDTO> list(Pageable pageable);
    UsuarioResponseDTO update(Integer id, UsuarioUpdateDTO dto);
    void delete(Integer id);
}
