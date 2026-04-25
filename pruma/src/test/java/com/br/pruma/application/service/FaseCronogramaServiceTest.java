package com.br.pruma.application.service;

import com.br.pruma.application.dto.request.FaseCronogramaRequestDTO;
import com.br.pruma.application.dto.response.FaseCronogramaResponseDTO;
import com.br.pruma.application.dto.update.FaseCronogramaUpdateDTO;
import com.br.pruma.application.mapper.FaseCronogramaMapper;
import com.br.pruma.application.service.impl.FaseCronogramaServiceImpl;
import com.br.pruma.core.domain.Cronograma;
import com.br.pruma.core.domain.FaseCronograma;
import com.br.pruma.core.repository.CronogramaRepository;
import com.br.pruma.core.repository.FaseCronogramaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("FaseCronogramaService — testes unitários")
class FaseCronogramaServiceTest {

    @Mock private FaseCronogramaRepository repository;
    @Mock private CronogramaRepository cronogramaRepository;
    @Mock private FaseCronogramaMapper mapper;
    @InjectMocks private FaseCronogramaServiceImpl service;

    // ── Cronograma:              @SuperBuilder  → Cronograma.builder().build()
    // ── FaseCronograma:          @Builder + @NoArgsConstructor público → builder ou new
    // ── FaseCronogramaRequestDTO:  @Data        → new + setters (getCronogramaId())
    // ── FaseCronogramaResponseDTO: @Data+@Builder → FaseCronogramaResponseDTO.builder().build()
    // ── FaseCronogramaUpdateDTO:   @Data        → new + setters
    // ── ativo herdado de AuditableEntity: Boolean wrapper → getAtivo(), não isAtivo()

    private static final LocalDate DATA_INICIO = LocalDate.now().plusDays(1);
    private static final LocalDate DATA_FIM    = LocalDate.now().plusMonths(3);

    private Cronograma buildCronograma(Integer id) {
        return Cronograma.builder()
                .id(id)
                .nome("Cronograma Teste")
                .dataInicio(DATA_INICIO)
                .dataFim(DATA_FIM)
                .ativo(true)
                .build();
    }

    private FaseCronograma buildFase(Integer id, Cronograma cronograma) {
        return FaseCronograma.builder()
                .id(id)
                .nome("Fase Teste")
                .cronograma(cronograma)
                .dataInicio(DATA_INICIO)
                .dataFim(DATA_FIM)
                .build();
    }

    private FaseCronogramaRequestDTO buildRequest(Integer cronogramaId) {
        var req = new FaseCronogramaRequestDTO();
        req.setCronogramaId(cronogramaId);
        req.setNome("Fase Teste");
        req.setDataInicio(DATA_INICIO);
        req.setDataFim(DATA_FIM);
        return req;
    }

    private FaseCronogramaResponseDTO buildResponse(Integer id) {
        return FaseCronogramaResponseDTO.builder()
                .id(id)
                .cronogramaNome("Cronograma Teste")
                .nome("Fase Teste")
                .dataInicio(DATA_INICIO)
                .dataFim(DATA_FIM)
                .build();
    }

    // ── create ───────────────────────────────────────────────────────────────

    @Test
    @DisplayName("create: busca cronograma, salva e retorna DTO")
    void create_sucesso() {
        var req        = buildRequest(10);
        var cronograma = buildCronograma(10);
        var entity     = buildFase(null, cronograma);
        var saved      = buildFase(1, cronograma);
        var response   = buildResponse(1);

        when(cronogramaRepository.findById(10)).thenReturn(Optional.of(cronograma));
        when(mapper.toEntity(req)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(saved);
        when(mapper.toResponseDTO(saved)).thenReturn(response);

        assertThat(service.create(req)).isEqualTo(response);
        verify(repository).save(argThat(f -> f.getCronograma().equals(cronograma)));
    }

    @Test
    @DisplayName("create: cronograma não encontrado lança EntityNotFoundException")
    void create_cronogramaNaoEncontrado() {
        when(cronogramaRepository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.create(buildRequest(99)))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("99");

        verify(repository, never()).save(any());
    }

    // ── getById ──────────────────────────────────────────────────────────────

    @Test
    @DisplayName("getById: retorna DTO quando fase existe")
    void getById_encontrado() {
        var entity   = buildFase(1, buildCronograma(10));
        var response = buildResponse(1);

        when(repository.findById(1)).thenReturn(Optional.of(entity));
        when(mapper.toResponseDTO(entity)).thenReturn(response);

        assertThat(service.getById(1)).isEqualTo(response);
    }

    @Test
    @DisplayName("getById: não encontrado lança EntityNotFoundException")
    void getById_naoEncontrado() {
        when(repository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.getById(99))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("99");
    }

    // ── listAll ──────────────────────────────────────────────────────────────

    @Test
    @DisplayName("listAll: retorna lista completa")
    void listAll_comRegistros() {
        var entity   = buildFase(1, buildCronograma(10));
        var response = buildResponse(1);

        when(repository.findAll()).thenReturn(List.of(entity));
        when(mapper.toResponseDTO(entity)).thenReturn(response);

        assertThat(service.listAll()).hasSize(1).containsExactly(response);
    }

    @Test
    @DisplayName("listAll: retorna lista vazia")
    void listAll_vazio() {
        when(repository.findAll()).thenReturn(List.of());
        assertThat(service.listAll()).isEmpty();
    }

    // ── listByCronograma ─────────────────────────────────────────────────────

    @Test
    @DisplayName("listByCronograma: retorna fases do cronograma informado")
    void listByCronograma_comRegistros() {
        var entity   = buildFase(1, buildCronograma(10));
        var response = buildResponse(1);

        when(repository.findAllByCronograma_Id(10)).thenReturn(List.of(entity));
        when(mapper.toResponseDTO(entity)).thenReturn(response);

        assertThat(service.listByCronograma(10)).hasSize(1).containsExactly(response);
        verify(repository).findAllByCronograma_Id(10);
    }

    @Test
    @DisplayName("listByCronograma: retorna lista vazia quando não há fases")
    void listByCronograma_vazio() {
        when(repository.findAllByCronograma_Id(10)).thenReturn(List.of());
        assertThat(service.listByCronograma(10)).isEmpty();
    }

    // ── update ───────────────────────────────────────────────────────────────

    @Test
    @DisplayName("update: não troca cronograma quando cronogramaId for null")
    void update_semTrocarCronograma() {
        var upd      = new FaseCronogramaUpdateDTO();   // cronogramaId = null
        var entity   = buildFase(1, buildCronograma(10));
        var response = buildResponse(1);

        when(repository.findById(1)).thenReturn(Optional.of(entity));
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toResponseDTO(entity)).thenReturn(response);

        assertThat(service.update(1, upd)).isEqualTo(response);
        verify(cronogramaRepository, never()).findById(any());
        verify(mapper).updateFromDto(upd, entity);
    }

    @Test
    @DisplayName("update: troca cronograma quando cronogramaId não for null")
    void update_comTrocaDeCronograma() {
        var upd = new FaseCronogramaUpdateDTO();
        upd.setCronogramaId(10);

        var novoCronograma = buildCronograma(10);
        var entity         = buildFase(1, buildCronograma(99));
        var response       = buildResponse(1);

        when(repository.findById(1)).thenReturn(Optional.of(entity));
        when(cronogramaRepository.findById(10)).thenReturn(Optional.of(novoCronograma));
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toResponseDTO(entity)).thenReturn(response);

        service.update(1, upd);

        assertThat(entity.getCronograma()).isEqualTo(novoCronograma);
    }

    @Test
    @DisplayName("update: fase não encontrada lança EntityNotFoundException")
    void update_naoEncontrado() {
        when(repository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.update(99, new FaseCronogramaUpdateDTO()))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("99");
    }

    // ── replace ──────────────────────────────────────────────────────────────

    @Test
    @DisplayName("replace: substitui fase completa mantendo o mesmo ID")
    void replace_sucesso() {
        var req        = buildRequest(10);
        var cronograma = buildCronograma(10);
        var nova       = buildFase(null, cronograma);
        var saved      = buildFase(1, cronograma);
        var response   = buildResponse(1);

        when(repository.findById(1)).thenReturn(Optional.of(buildFase(1, cronograma)));
        when(cronogramaRepository.findById(10)).thenReturn(Optional.of(cronograma));
        when(mapper.toEntity(req)).thenReturn(nova);
        when(repository.save(any())).thenReturn(saved);
        when(mapper.toResponseDTO(saved)).thenReturn(response);

        assertThat(service.replace(1, req)).isEqualTo(response);
        verify(repository).save(argThat(f -> f.getId().equals(1) && f.getCronograma().equals(cronograma)));
    }

    @Test
    @DisplayName("replace: fase não encontrada lança EntityNotFoundException")
    void replace_naoEncontrado() {
        when(repository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.replace(99, buildRequest(10)))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("99");
    }

    // ── delete (soft) ────────────────────────────────────────────────────────

    @Test
    @DisplayName("delete: marca ativo=false sem remover do banco")
    void delete_softDelete() {
        var entity = buildFase(1, buildCronograma(10));
        when(repository.findById(1)).thenReturn(Optional.of(entity));

        service.delete(1);

        // Boolean wrapper → getAtivo(), não isAtivo()
        verify(repository).save(argThat(f -> !f.getAtivo()));
        verify(repository, never()).delete(any());
    }

    @Test
    @DisplayName("delete: não encontrado lança EntityNotFoundException")
    void delete_naoEncontrado() {
        when(repository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.delete(99))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("99");
    }
}