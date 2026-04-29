package com.br.pruma.application.service;

import com.br.pruma.application.dto.request.LembreteRequestDTO;
import com.br.pruma.application.dto.response.LembreteResponseDTO;
import com.br.pruma.application.dto.update.LembreteUpdateDTO;
import com.br.pruma.application.mapper.LembreteMapper;
import com.br.pruma.application.service.impl.LembreteServiceImpl;
import com.br.pruma.core.domain.Cliente;
import com.br.pruma.core.domain.Lembrete;
import com.br.pruma.core.domain.TipoUsuario;
import com.br.pruma.core.repository.ClienteRepository;
import com.br.pruma.core.repository.LembreteRepository;
import com.br.pruma.core.repository.TipoUsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LembreteServiceTest {

    @Mock LembreteRepository lembreteRepo;
    @Mock ClienteRepository clienteRepo;
    @Mock TipoUsuarioRepository tipoUsuarioRepo;
    @Mock LembreteMapper mapper;
    @InjectMocks LembreteServiceImpl service;

    Lembrete entity;
    LembreteResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        entity      = mock(Lembrete.class);
        responseDTO = mock(LembreteResponseDTO.class);
    }

    @Test
    @DisplayName("create: salva e retorna DTO")
    void create_sucesso() {
        var dto         = mock(LembreteRequestDTO.class);
        var cliente     = mock(Cliente.class);
        var tipoUsuario = mock(TipoUsuario.class);
        when(dto.getClienteCpf()).thenReturn("1");
        when(dto.getTipoUsuarioId()).thenReturn(2);
        when(clienteRepo.findById(1)).thenReturn(Optional.of(cliente));
        when(tipoUsuarioRepo.findById(2)).thenReturn(Optional.of(tipoUsuario));
        when(mapper.toEntity(dto)).thenReturn(entity);
        when(lembreteRepo.save(entity)).thenReturn(entity);
        when(mapper.toResponse(entity)).thenReturn(responseDTO);

        assertThat(service.create(dto)).isEqualTo(responseDTO);
        verify(entity).setCliente(cliente);
        verify(entity).setTipoUsuario(tipoUsuario);
    }

    @Test
    @DisplayName("create: lanca excecao quando cliente nao encontrado")
    void create_clienteNaoEncontrado() {
        var dto = mock(LembreteRequestDTO.class);
        when(dto.getClienteCpf()).thenReturn("99");
        when(clienteRepo.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.create(dto))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    @DisplayName("create: lanca excecao quando tipoUsuario nao encontrado")
    void create_tipoUsuarioNaoEncontrado() {
        var dto     = mock(LembreteRequestDTO.class);
        var cliente = mock(Cliente.class);
        when(dto.getClienteCpf()).thenReturn("1");
        when(dto.getTipoUsuarioId()).thenReturn(99);
        when(clienteRepo.findById(1)).thenReturn(Optional.of(cliente));
        when(tipoUsuarioRepo.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.create(dto))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    @DisplayName("getById: retorna DTO quando existe")
    void getById_encontrado() {
        when(lembreteRepo.findById(1)).thenReturn(Optional.of(entity));
        when(mapper.toResponse(entity)).thenReturn(responseDTO);

        assertThat(service.getById(1)).isEqualTo(responseDTO);
    }

    @Test
    @DisplayName("getById: lanca EntityNotFoundException quando nao existe")
    void getById_naoEncontrado() {
        when(lembreteRepo.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.getById(99))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    @DisplayName("listAll: retorna lista mapeada")
    void listAll() {
        when(lembreteRepo.findAll()).thenReturn(List.of(entity));
        when(mapper.toResponse(entity)).thenReturn(responseDTO);

        assertThat(service.listAll()).containsExactly(responseDTO);
    }

    @Test
    @DisplayName("list: retorna pagina mapeada")
    void list_paginado() {
        Pageable pageable = PageRequest.of(0, 10);
        when(lembreteRepo.findAll(pageable)).thenReturn(new PageImpl<>(List.of(entity)));
        when(mapper.toResponse(entity)).thenReturn(responseDTO);

        assertThat(service.list(pageable).getContent()).containsExactly(responseDTO);
    }

    @Test
    @DisplayName("update: atualiza quando existe")
    void update_sucesso() {
        var updateDTO = mock(LembreteUpdateDTO.class);
        when(lembreteRepo.findById(1)).thenReturn(Optional.of(entity));
        when(lembreteRepo.save(entity)).thenReturn(entity);
        when(mapper.toResponse(entity)).thenReturn(responseDTO);

        assertThat(service.update(1, updateDTO)).isEqualTo(responseDTO);
        verify(mapper).updateFromDto(updateDTO, entity);
    }

    @Test
    @DisplayName("update: lanca EntityNotFoundException quando nao existe")
    void update_naoEncontrado() {
        var updateDTO = mock(LembreteUpdateDTO.class);
        when(lembreteRepo.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.update(99, updateDTO))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    @DisplayName("replace: substitui quando existe")
    void replace_sucesso() {
        var dto = mock(LembreteRequestDTO.class);
        when(lembreteRepo.findById(1)).thenReturn(Optional.of(entity));
        when(mapper.toEntity(dto)).thenReturn(entity);
        when(lembreteRepo.save(entity)).thenReturn(entity);
        when(mapper.toResponse(entity)).thenReturn(responseDTO);

        assertThat(service.replace(1, dto)).isEqualTo(responseDTO);
        verify(entity).setId(1);
    }

    @Test
    @DisplayName("replace: lanca EntityNotFoundException quando nao existe")
    void replace_naoEncontrado() {
        var dto = mock(LembreteRequestDTO.class);
        when(lembreteRepo.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.replace(99, dto))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    @DisplayName("delete: deleta fisicamente quando existe")
    void delete_sucesso() {
        when(lembreteRepo.existsById(1)).thenReturn(true);

        service.delete(1);

        verify(lembreteRepo).deleteById(1);
    }

    @Test
    @DisplayName("delete: lanca EntityNotFoundException quando nao existe")
    void delete_naoEncontrado() {
        when(lembreteRepo.existsById(99)).thenReturn(false);

        assertThatThrownBy(() -> service.delete(99))
                .isInstanceOf(EntityNotFoundException.class);
    }
}
