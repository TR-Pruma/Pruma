package com.br.pruma.application.service;

import com.br.pruma.application.dto.request.LembreteRequestDTO;
import com.br.pruma.application.dto.response.LembreteResponseDTO;
import com.br.pruma.application.dto.update.LembreteUpdateDTO;
import com.br.pruma.application.mapper.LembreteMapper;
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

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LembreteServiceTest {

    @Mock LembreteRepository lembreteRepo;
    @Mock ClienteRepository clienteRepo;
    @Mock TipoUsuarioRepository tipoUsuarioRepo;
    @Mock LembreteMapper mapper;
    @InjectMocks LembreteService service;

    Lembrete lembrete;
    LembreteRequestDTO requestDTO;
    LembreteResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        lembrete    = mock(Lembrete.class);
        requestDTO  = mock(LembreteRequestDTO.class);
        responseDTO = mock(LembreteResponseDTO.class);
    }

    @Test
    @DisplayName("create: salva e retorna DTO")
    void create_sucesso() {
        when(requestDTO.getClienteCpf()).thenReturn("1");
        when(requestDTO.getTipoUsuarioId()).thenReturn(1);
        when(clienteRepo.findById(1)).thenReturn(Optional.of(mock(Cliente.class)));
        when(tipoUsuarioRepo.findById(1)).thenReturn(Optional.of(mock(TipoUsuario.class)));
        when(mapper.toEntity(requestDTO)).thenReturn(lembrete);
        when(lembreteRepo.save(lembrete)).thenReturn(lembrete);
        when(mapper.toResponse(lembrete)).thenReturn(responseDTO);

        assertThat(service.create(requestDTO)).isEqualTo(responseDTO);
    }

    @Test
    @DisplayName("getById: retorna DTO quando existe")
    void getById_encontrado() {
        when(lembreteRepo.findById(1)).thenReturn(Optional.of(lembrete));
        when(mapper.toResponse(lembrete)).thenReturn(responseDTO);

        assertThat(service.getById(1)).isEqualTo(responseDTO);
    }

    @Test
    @DisplayName("getById: lanca EntityNotFoundException quando nao existe")
    void getById_naoEncontrado() {
        when(lembreteRepo.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.getById(99))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("99");
    }

    @Test
    @DisplayName("listAll: retorna lista mapeada")
    void listAll() {
        when(lembreteRepo.findAll()).thenReturn(List.of(lembrete));
        when(mapper.toResponse(lembrete)).thenReturn(responseDTO);

        assertThat(service.listAll()).containsExactly(responseDTO);
    }

    @Test
    @DisplayName("update: atualiza quando existe")
    void update_sucesso() {
        var updateDTO = mock(LembreteUpdateDTO.class);
        when(lembreteRepo.findById(1)).thenReturn(Optional.of(lembrete));
        when(lembreteRepo.save(lembrete)).thenReturn(lembrete);
        when(mapper.toResponse(lembrete)).thenReturn(responseDTO);

        assertThat(service.update(1, updateDTO)).isEqualTo(responseDTO);
        verify(mapper).updateFromDto(updateDTO, lembrete);
    }

    @Test
    @DisplayName("update: lanca EntityNotFoundException quando nao existe")
    void update_naoEncontrado() {
        when(lembreteRepo.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.update(99, mock(LembreteUpdateDTO.class)))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    @DisplayName("delete: deleta quando existe")
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
        verify(lembreteRepo, never()).deleteById(any());
    }
}
