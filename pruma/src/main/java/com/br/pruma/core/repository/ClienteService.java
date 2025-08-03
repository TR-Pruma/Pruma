package com.br.pruma.core.repository;

import com.br.pruma.application.dto.request.ClienteRequestDTO;
import com.br.pruma.application.dto.response.ClienteResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ClienteService {
    ClienteResponseDTO criar(ClienteRequestDTO request);
    Page<ClienteResponseDTO> listarTodos(Pageable pageable);
    ClienteResponseDTO buscarPorId(Integer id);
    ClienteResponseDTO atualizar(Integer id, ClienteRequestDTO request);
    void deletar(Integer id);
    ClienteResponseDTO buscarPorCpf(String cpf);
    ClienteResponseDTO buscarPorEmail(String email);
    boolean existePorCpf(String cpf);
    boolean existePorEmail(String email);
}
