package com.br.pruma.application.service;

import com.br.pruma.application.dto.request.AtividadeRequestDTO;
import com.br.pruma.application.dto.response.AtividadeResponseDTO;
import com.br.pruma.application.dto.update.AtividadeUpdateDTO;
import com.br.pruma.application.mapper.AtividadeMapper;
import com.br.pruma.application.service.impl.AtividadeServiceImpl;
import com.br.pruma.core.domain.Atividade;
import com.br.pruma.core.repository.AtividadeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("AtividadeServiceImpl — testes unitários")
class AtividadeServiceTest {

    @Mock
    private AtividadeRepository repository;

    @Mock
    private AtividadeMapper mapper;

    @InjectMocks
    private AtividadeServiceImpl service;

    private Atividade entity;
    private AtividadeRequestDTO requestDTO;
    private AtividadeUpdateDTO updateDTO;
    private AtividadeResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        // ✅ builder via Lombok — único acesso válido fora do pacote
        entity = Atividade.builder()
                .id(1)
                .descricao("Concretar fundação")
                .status(com.br.pruma.core.enums.StatusAtividade.PENDENTE)
                .dataInicio(LocalDate.of(2025, 1, 10))
                .build();


        requestDTO = new AtividadeRequestDTO();
        requestDTO.setProjeto(10);
        requestDTO.setDescricao("Concretar fundação");
        requestDTO.setStatus("PENDENTE");
        requestDTO.setDataInicio(LocalDate.of(2025, 1, 10));
        requestDTO.setDataFim(LocalDate.of(2025, 3, 1));

        updateDTO = new AtividadeUpdateDTO();
        updateDTO.setDescricao("Descrição atualizada");
        updateDTO.setStatus("EM_ANDAMENTO");

        responseDTO = new AtividadeResponseDTO();
        responseDTO.setId(1);
        responseDTO.setDescricao("Concretar fundação");
        responseDTO.setStatus("PENDENTE");
    }

    @Test
    @DisplayName("create — mapeia DTO para entidade, persiste e retorna response")
    void create_sucesso() {
        when(mapper.toEntity(requestDTO)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toResponse(entity)).thenReturn(responseDTO);

        AtividadeResponseDTO result = service.create(requestDTO);

        assertThat(result).isEqualTo(responseDTO);
        verify(mapper).toEntity(requestDTO);
        verify(repository).save(entity);
        verify(mapper).toResponse(entity);
    }

    @Test
    @DisplayName("getById — retorna response quando encontrado")
    void getById_encontrado() {
        when(repository.findById(1)).thenReturn(Optional.of(entity));
        when(mapper.toResponse(entity)).thenReturn(responseDTO);

        AtividadeResponseDTO result = service.getById(1);

        assertThat(result).isEqualTo(responseDTO);
        verify(repository).findById(1);
        verify(mapper).toResponse(entity);
    }

    @Test
    @DisplayName("getById — lança EntityNotFoundException quando id não existe")
    void getById_naoEncontrado() {
        when(repository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.getById(99))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("99");

        verify(mapper, never()).toResponse(any());
    }
    @Test
    @DisplayName("listAll — retorna lista mapeada de todas as atividades")
    void listAll_comRegistros() {
        when(repository.findAll()).thenReturn(List.of(entity));
        when(mapper.toResponse(entity)).thenReturn(responseDTO);

        List<AtividadeResponseDTO> result = service.listAll();

        assertThat(result).hasSize(1).contains(responseDTO);
        verify(repository).findAll();
        verify(mapper).toResponse(entity);
    }

    @Test
    @DisplayName("listAll — retorna lista vazia quando repositório não tem registros")
    void listAll_vazio() {
        when(repository.findAll()).thenReturn(List.of());

        assertThat(service.listAll()).isEmpty();
        verify(mapper, never()).toResponse(any());
    }

    @Test
    @DisplayName("list — retorna Page mapeado com Pageable")
    void list_paginado() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Atividade> page = new PageImpl<>(List.of(entity));

        when(repository.findAll(pageable)).thenReturn(page);
        when(mapper.toResponse(entity)).thenReturn(responseDTO);

        Page<AtividadeResponseDTO> result = service.list(pageable);

        assertThat(result.getContent()).hasSize(1).contains(responseDTO);
        verify(repository).findAll(pageable);
    }

    @Test
    @DisplayName("list — retorna Page vazio quando repositório vazio")
    void list_paginadoVazio() {
        Pageable pageable = PageRequest.of(0, 10);
        when(repository.findAll(pageable)).thenReturn(Page.empty());

        Page<AtividadeResponseDTO> result = service.list(pageable);

        assertThat(result.getContent()).isEmpty();
    }
    @Test
    @DisplayName("update — aplica patch parcial via mapper e salva")
    void update_sucesso() {
        when(repository.findById(1)).thenReturn(Optional.of(entity));
        // updateFromDto é void — doNothing é implícito, mas explicitamos para clareza
        doNothing().when(mapper).updateFromDto(updateDTO, entity);
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toResponse(entity)).thenReturn(responseDTO);

        AtividadeResponseDTO result = service.update(1, updateDTO);

        assertThat(result).isEqualTo(responseDTO);
        verify(mapper).updateFromDto(updateDTO, entity);
        verify(repository).save(entity);
    }

    @Test
    @DisplayName("update — lança EntityNotFoundException quando id não existe")
    void update_naoEncontrado() {
        when(repository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.update(99, updateDTO))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("99");

        verify(mapper, never()).updateFromDto(any(), any());
        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("replace — cria nova entidade do DTO, injeta o id e persiste")
    void replace_sucesso() {
        // Atividade sem id para simular a nova entidade que o mapper vai criar
        Atividade novaEntity = Atividade.builder()
                .descricao("Nova descrição completa")
                .status(com.br.pruma.core.enums.StatusAtividade.PENDENTE)
                .dataInicio(LocalDate.of(2025, 2, 1))
                .build();

        when(repository.findById(1)).thenReturn(Optional.of(entity));
        when(mapper.toEntity(requestDTO)).thenReturn(novaEntity);
        when(repository.save(novaEntity)).thenReturn(novaEntity);
        when(mapper.toResponse(novaEntity)).thenReturn(responseDTO);

        AtividadeResponseDTO result = service.replace(1, requestDTO);

        assertThat(novaEntity.getId()).isEqualTo(1);
        assertThat(result).isEqualTo(responseDTO);
        verify(repository).save(novaEntity);
    }

    @Test
    @DisplayName("replace — lança EntityNotFoundException quando id não existe")
    void replace_naoEncontrado() {
        when(repository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.replace(99, requestDTO))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("99");

        verify(mapper, never()).toEntity(any());
        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("delete — faz soft delete: busca entidade, chama save e NÃO chama deleteById")
    void delete_sucesso() {
        when(repository.findById(1)).thenReturn(Optional.of(entity));
        when(repository.save(entity)).thenReturn(entity);

        service.delete(1);
        verify(repository).findById(1);
        verify(repository).save(entity);
        verify(repository, never()).deleteById(any());
    }

    @Test
    @DisplayName("delete — lança EntityNotFoundException quando id não existe")
    void delete_naoEncontrado() {
        when(repository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.delete(99))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("99");

        verify(repository, never()).save(any());
        verify(repository, never()).deleteById(any());
    }
}