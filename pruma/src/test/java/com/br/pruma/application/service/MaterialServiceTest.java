package com.br.pruma.application.service;

import com.br.pruma.application.dto.request.MaterialRequestDTO;
import com.br.pruma.application.dto.response.MaterialResponseDTO;
import com.br.pruma.application.dto.update.MaterialUpdateDTO;
import com.br.pruma.application.mapper.MaterialMapper;
import com.br.pruma.core.domain.Material;
import com.br.pruma.core.repository.MaterialRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MaterialServiceTest {

    @Mock
    private MaterialRepository repository;

    @Mock
    private MaterialMapper mapper;

    @InjectMocks
    private MaterialService service;

    private Material material;
    private MaterialRequestDTO requestDTO;
    private MaterialResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        material = Material.builder()
                .descricao("Cimento CP-II")
                .quantidade(100)
                .custoUnitario(new BigDecimal("32.50"))
                .build();

        requestDTO = new MaterialRequestDTO();
        requestDTO.setDescricao("Cimento CP-II");
        requestDTO.setQuantidade(100);
        requestDTO.setCustoUnitario(new BigDecimal("32.50"));

        responseDTO = new MaterialResponseDTO();
        responseDTO.setDescricao("Cimento CP-II");
        responseDTO.setQuantidade(100);
        responseDTO.setCustoUnitario(new BigDecimal("32.50"));
    }

    // -----------------------------------------------------------------------
    // create
    // -----------------------------------------------------------------------

    @Test
    @DisplayName("create: deve salvar e retornar DTO quando descrição é única")
    void create_sucesso() {
        when(repository.findByDescricao(requestDTO.getDescricao())).thenReturn(Optional.empty());
        when(mapper.toEntity(requestDTO)).thenReturn(material);
        when(repository.save(material)).thenReturn(material);
        when(mapper.toResponse(material)).thenReturn(responseDTO);

        MaterialResponseDTO result = service.create(requestDTO);

        assertThat(result).isNotNull();
        assertThat(result.getDescricao()).isEqualTo("Cimento CP-II");
        verify(repository).save(material);
    }

    @Test
    @DisplayName("create: deve lançar IllegalArgumentException quando descrição já existe")
    void create_descricaoDuplicada() {
        when(repository.findByDescricao(requestDTO.getDescricao())).thenReturn(Optional.of(material));

        assertThatThrownBy(() -> service.create(requestDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Já existe material com essa descrição");

        verify(repository, never()).save(any());
    }

    // -----------------------------------------------------------------------
    // getById
    // -----------------------------------------------------------------------

    @Test
    @DisplayName("getById: deve retornar DTO quando material existe")
    void getById_encontrado() {
        when(repository.findById(1)).thenReturn(Optional.of(material));
        when(mapper.toResponse(material)).thenReturn(responseDTO);

        MaterialResponseDTO result = service.getById(1);

        assertThat(result).isNotNull();
        assertThat(result.getDescricao()).isEqualTo("Cimento CP-II");
    }

    @Test
    @DisplayName("getById: deve lançar EntityNotFoundException quando material não existe")
    void getById_naoEncontrado() {
        when(repository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.getById(99))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("99");
    }

    // -----------------------------------------------------------------------
    // listAll
    // -----------------------------------------------------------------------

    @Test
    @DisplayName("listAll: deve retornar lista com todos os materiais")
    void listAll_retornaLista() {
        when(repository.findAllByOrderByDescricaoAsc()).thenReturn(List.of(material));
        when(mapper.toResponse(material)).thenReturn(responseDTO);

        List<MaterialResponseDTO> result = service.listAll();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getDescricao()).isEqualTo("Cimento CP-II");
    }

    @Test
    @DisplayName("listAll: deve retornar lista vazia quando não há materiais")
    void listAll_listaVazia() {
        when(repository.findAllByOrderByDescricaoAsc()).thenReturn(List.of());

        List<MaterialResponseDTO> result = service.listAll();

        assertThat(result).isEmpty();
    }

    // -----------------------------------------------------------------------
    // update
    // -----------------------------------------------------------------------

    @Test
    @DisplayName("update: deve atualizar material com nova descrição única")
    void update_sucesso() {
        MaterialUpdateDTO updateDTO = new MaterialUpdateDTO();
        updateDTO.setDescricao("Cimento CP-III");

        Material materialComId = material;
        // simula id preenchido
        try {
            var field = Material.class.getDeclaredField("id");
            field.setAccessible(true);
            field.set(materialComId, 1);
        } catch (Exception ignored) {}

        when(repository.findById(1)).thenReturn(Optional.of(materialComId));
        when(repository.findByDescricao("Cimento CP-III")).thenReturn(Optional.empty());
        when(repository.save(materialComId)).thenReturn(materialComId);
        when(mapper.toResponse(materialComId)).thenReturn(responseDTO);

        MaterialResponseDTO result = service.update(1, updateDTO);

        assertThat(result).isNotNull();
        verify(mapper).updateFromDto(updateDTO, materialComId);
        verify(repository).save(materialComId);
    }

    @Test
    @DisplayName("update: deve lançar EntityNotFoundException quando material não existe")
    void update_naoEncontrado() {
        when(repository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.update(99, new MaterialUpdateDTO()))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("99");
    }

    @Test
    @DisplayName("update: deve lançar IllegalArgumentException quando nova descrição pertence a outro material")
    void update_descricaoDuplicadaOutroMaterial() {
        MaterialUpdateDTO updateDTO = new MaterialUpdateDTO();
        updateDTO.setDescricao("Cimento CP-III");

        Material outro = Material.builder()
                .descricao("Cimento CP-III")
                .quantidade(50)
                .custoUnitario(BigDecimal.TEN)
                .build();
        try {
            var field = Material.class.getDeclaredField("id");
            field.setAccessible(true);
            field.set(material, 1);
            field.set(outro, 2);
        } catch (Exception ignored) {}

        when(repository.findById(1)).thenReturn(Optional.of(material));
        when(repository.findByDescricao("Cimento CP-III")).thenReturn(Optional.of(outro));

        assertThatThrownBy(() -> service.update(1, updateDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Outro material já usa essa descrição");
    }

    // -----------------------------------------------------------------------
    // delete
    // -----------------------------------------------------------------------

    @Test
    @DisplayName("delete: deve deletar quando material existe")
    void delete_sucesso() {
        when(repository.existsById(1)).thenReturn(true);

        service.delete(1);

        verify(repository).deleteById(1);
    }

    @Test
    @DisplayName("delete: deve lançar EntityNotFoundException quando material não existe")
    void delete_naoEncontrado() {
        when(repository.existsById(99)).thenReturn(false);

        assertThatThrownBy(() -> service.delete(99))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("99");

        verify(repository, never()).deleteById(any());
    }
}
