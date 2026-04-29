package com.br.pruma.application.service;

import com.br.pruma.application.dto.request.InsumoFornecedorRequestDTO;
import com.br.pruma.application.dto.response.InsumoFornecedorResponseDTO;
import com.br.pruma.application.dto.update.InsumoFornecedorUpdateDTO;
import com.br.pruma.application.mapper.InsumoFornecedorMapper;
import com.br.pruma.application.service.impl.InsumoFornecedorServiceImpl;
import com.br.pruma.core.domain.InsumoFornecedor;
import com.br.pruma.core.domain.InsumoFornecedorAuxId;
import com.br.pruma.core.repository.InsumoFornecedorRepository;
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

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InsumoFornecedorServiceTest {

    @Mock InsumoFornecedorRepository repository;
    @Mock InsumoFornecedorMapper mapper;
    @InjectMocks InsumoFornecedorServiceImpl service;

    InsumoFornecedor entity;
    InsumoFornecedorResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        entity      = mock(InsumoFornecedor.class);
        responseDTO = mock(InsumoFornecedorResponseDTO.class);
    }

    @Test
    @DisplayName("create: salva e retorna DTO")
    void create_sucesso() {
        var dto = mock(InsumoFornecedorRequestDTO.class);
        when(mapper.toEntity(dto)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toResponseDTO(entity)).thenReturn(responseDTO);

        assertThat(service.create(dto)).isEqualTo(responseDTO);
    }

    @Test
    @DisplayName("getById: lanca UnsupportedOperationException (chave composta)")
    void getById_unsupported() {
        assertThatThrownBy(() -> service.getById(1))
                .isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    @DisplayName("update: lanca UnsupportedOperationException (chave composta)")
    void update_unsupported() {
        assertThatThrownBy(() -> service.update(1, mock(InsumoFornecedorUpdateDTO.class)))
                .isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    @DisplayName("delete: lanca UnsupportedOperationException (chave composta)")
    void delete_unsupported() {
        assertThatThrownBy(() -> service.delete(1))
                .isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    @DisplayName("listAll: retorna lista mapeada")
    void listAll() {
        when(repository.findAll()).thenReturn(List.of(entity));
        when(mapper.toResponseDTO(entity)).thenReturn(responseDTO);

        assertThat(service.listAll()).containsExactly(responseDTO);
    }

    @Test
    @DisplayName("listAll: retorna lista vazia quando nao ha registros")
    void listAll_vazia() {
        when(repository.findAll()).thenReturn(List.of());

        assertThat(service.listAll()).isEmpty();
    }

    @Test
    @DisplayName("list: retorna pagina mapeada")
    void list_paginado() {
        Pageable pageable = PageRequest.of(0, 10);
        when(repository.findAll(pageable)).thenReturn(new PageImpl<>(List.of(entity)));
        when(mapper.toResponseDTO(entity)).thenReturn(responseDTO);

        assertThat(service.list(pageable).getContent()).containsExactly(responseDTO);
    }

    @Test
    @DisplayName("listByInsumo: retorna lista filtrada por insumo")
    void listByInsumo() {
        when(repository.findByIdInsumoId(1)).thenReturn(List.of(entity));
        when(mapper.toResponseDTO(entity)).thenReturn(responseDTO);

        assertThat(service.listByInsumo(1)).containsExactly(responseDTO);
    }

    @Test
    @DisplayName("listByFornecedor: retorna lista filtrada por fornecedor")
    void listByFornecedor() {
        var auxId = mock(InsumoFornecedorAuxId.class);
        when(auxId.getFornecedorId()).thenReturn(2);
        when(entity.getId()).thenReturn(auxId);
        when(repository.findAll()).thenReturn(List.of(entity));
        when(mapper.toResponseDTO(entity)).thenReturn(responseDTO);

        assertThat(service.listByFornecedor(2)).containsExactly(responseDTO);
    }

    @Test
    @DisplayName("listByFornecedor: nao retorna registros com fornecedor diferente")
    void listByFornecedor_semMatch() {
        var auxId = mock(InsumoFornecedorAuxId.class);
        when(auxId.getFornecedorId()).thenReturn(3);
        when(entity.getId()).thenReturn(auxId);
        when(repository.findAll()).thenReturn(List.of(entity));

        assertThat(service.listByFornecedor(2)).isEmpty();
    }
}
