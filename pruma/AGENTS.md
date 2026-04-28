# AGENTS.md — PROJETO PRUMA
## Guia Completo para Agentes de IA: Arquitetura + Estratégia de Testes

> **Repositório:** `TR-Pruma/Pruma`
> **Branch de referência:** `test/novos_testes`
> **Stack:** Java 17+ · Spring Boot 3 · JPA/Hibernate 6 · MapStruct · Lombok · JUnit 5 · Mockito · AssertJ

---

## 1. ARQUITETURA DO PROJETO

O projeto segue uma arquitetura **Hexagonal (Ports & Adapters)** com influência de **Clean Architecture**, organizada em 4 pacotes raiz:

```
com.br.pruma
├── adapters/
│   ├── in/rest/          ← Controllers REST (entrada HTTP)
│   ├── out/              ← Implementações de saída (ex: repositórios externos)
│   └── security/         ← Filtros JWT, configuração Spring Security
├── application/
│   ├── dto/
│   │   ├── request/      ← DTOs de entrada (criação)
│   │   ├── update/       ← DTOs de entrada (atualização parcial/total)
│   │   └── response/     ← DTOs de saída
│   ├── mapper/           ← Interfaces MapStruct
│   └── service/
│       ├── XxxService    ← Interface (porta/contrato)
│       └── impl/
│           └── XxxServiceImpl ← Implementação concreta
├── config/               ← Beans de configuração Spring
└── core/
    ├── domain/           ← Entidades JPA (modelo de domínio)
    ├── enums/            ← Enumerações do domínio
    └── repository/       ← Interfaces Spring Data JPA
```

### Fluxo de dados obrigatório

```
HTTP Request
    → Controller (adapters/in/rest)
        → XxxService (interface application/service)
            → XxxServiceImpl (application/service/impl)
                → XxxRepository (core/repository)
                    → Entidade JPA (core/domain)
                ← XxxMapper (application/mapper)
            ← XxxResponseDTO (application/dto/response)
        ← ResponseEntity<XxxResponseDTO>
    ← HTTP Response
```

**REGRA:** Nunca injete `Repository` diretamente em Controllers. Nunca injete `Service` diretamente em outra entidade de domínio. O fluxo acima é inviolável.

---

## 2. DOMÍNIO — REGRAS DE ENTIDADE

### 2.1 Superclasse `AuditableEntity`

Todas as entidades de negócio herdam de `AuditableEntity`:

```java
@MappedSuperclass
@SuperBuilder
@NoArgsConstructor
@SQLRestriction("ativo = true")         // Hibernate 6 — substitui @Where deprecated
@EntityListeners(AuditingEntityListener.class)
public abstract class AuditableEntity implements Serializable {
    private LocalDateTime createdAt;    // @CreationTimestamp
    private LocalDateTime updatedAt;    // @UpdateTimestamp
    private Boolean ativo = true;       // soft-delete flag
    private Long version;               // @Version — optimistic locking
}
```

**Implicações críticas:**
- `@SQLRestriction("ativo = true")` faz com que **todo `findAll()`, `findById()` etc. filtre automaticamente registros com `ativo = false`**.
- `@Version` causa `OptimisticLockException` em updates concorrentes — nunca ignore esse campo em testes de concorrência.
- `ativo` é `Boolean` (wrapper), não `boolean` (primitivo) — pode ser `null` em entidades legadas migradas.

### 2.2 Padrão de Construtores Lombok

**TODAS as entidades usam:**
```java
@NoArgsConstructor(access = AccessLevel.PROTECTED)   // JPA precisa, mas não código externo
@AllArgsConstructor(access = AccessLevel.PRIVATE)    // só o builder usa
@Builder  // ou @SuperBuilder para entidades que herdam AuditableEntity
```

**Regra absoluta:** Fora do pacote `core.domain`, NUNCA use `new Entidade()`. Use sempre o builder:

```java
// ✅ CORRETO
Atividade a = Atividade.builder()
        .id(1)
        .descricao("Texto")
        .status(StatusAtividade.PENDENTE)
        .dataInicio(LocalDate.now())
        .build();

// ✅ Factory method para referências leves (apenas id)
Projeto p = Projeto.ofId(10);

// ❌ ERRADO — não compila ou viola encapsulamento
Projeto p = new Projeto();
Atividade a = new Atividade("desc", StatusAtividade.PENDENTE);
```

### 2.3 `@SuperBuilder` vs `@Builder`

| Entidade herda `AuditableEntity`? | Anotação correta |
|-----------------------------------|------------------|
| Sim (ex: `Projeto`, `Obra`)       | `@SuperBuilder`  |
| Não (ex: `Checklist` standalone)  | `@Builder`       |

### 2.4 Soft Delete

Entidades com `@SQLDelete` sobrescrevem o comportamento do `repository.delete()`:

```java
@SQLDelete(sql = "UPDATE checklist SET ativo = false WHERE checklist_id = ?")
```

- `repository.deleteById(id)` → executa o SQL acima, não um `DELETE` real
- `repository.findAll()` → retorna só registros com `ativo = true` (via `@SQLRestriction`)
- No `ServiceImpl`, o `delete()` busca a entidade, seta `ativo = false` manualmente e chama `save()` — **nunca chama `deleteById()`** diretamente

---

## 3. CAMADA APPLICATION — SERVICES

### 3.1 Estrutura obrigatória

```java
// Interface — porta de entrada
public interface AtividadeService {
    AtividadeResponseDTO create(AtividadeRequestDTO dto);
    AtividadeResponseDTO getById(Integer id);
    List<AtividadeResponseDTO> listAll();
    Page<AtividadeResponseDTO> list(Pageable pageable);
    AtividadeResponseDTO update(Integer id, AtividadeUpdateDTO dto);
    AtividadeResponseDTO replace(Integer id, AtividadeRequestDTO dto);
    void delete(Integer id);
}

// Implementação
@Service
@RequiredArgsConstructor
@Transactional
public class AtividadeServiceImpl implements AtividadeService {

    private final AtividadeRepository repository;
    private final AtividadeMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public AtividadeResponseDTO getById(Integer id) {
        return mapper.toResponse(
            repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Atividade não encontrada: " + id))
        );
    }
}
```

### 3.2 Métodos padrão de todo ServiceImpl

| Método     | HTTP    | Transação       | Comportamento                                              |
|------------|---------|-----------------|------------------------------------------------------------|
| `create`   | POST    | read-write      | `mapper.toEntity(dto)` → `save()` → `mapper.toResponse()` |
| `getById`  | GET     | readOnly        | `findById().orElseThrow(EntityNotFoundException)`          |
| `listAll`  | GET     | readOnly        | `findAll().stream().map(mapper::toResponse).toList()`      |
| `list`     | GET     | readOnly        | `findAll(pageable).map(mapper::toResponse)`                |
| `update`   | PATCH   | read-write      | busca → `mapper.updateFromDto(dto, entity)` → `save()`     |
| `replace`  | PUT     | read-write      | busca (valida existência) → `toEntity(dto)` → `setId()` → `save()` |
| `delete`   | DELETE  | read-write      | busca → `setAtivo(false)` → `save()` (soft delete)         |

### 3.3 Tratamento de exceção

- Usar sempre `jakarta.persistence.EntityNotFoundException` (não a do Spring)
- Mensagem deve conter o id: `"Entidade não encontrada: " + id`
- Nunca deixar `Optional.get()` sem `orElseThrow`

---

## 4. CAMADA APPLICATION — DTOs

### 4.1 Três tipos de DTO por entidade

```
XxxRequestDTO   → CREATE (POST)    — campos obrigatórios com @NotNull/@NotBlank
XxxUpdateDTO    → UPDATE (PATCH)   — todos os campos opcionais (sem @NotNull)
XxxResponseDTO  → resposta HTTP    — sem anotações de validação
```

### 4.2 Regras

```java
@Data
public class AtividadeRequestDTO {
    @NotNull
    private Integer projeto;        // referência por ID, nunca por objeto completo

    @NotBlank
    private String descricao;

    @NotBlank
    private String status;          // enums chegam como String, o Mapper converte

    @NotNull
    private LocalDate dataInicio;
}
```

- DTOs não carregam objetos de domínio — só IDs
- Enums chegam como `String` e são convertidos no Mapper via `@Named`
- `UpdateDTO` nunca tem `@NotNull` — campos `null` = "não alterar" (PATCH semântico)

---

## 5. CAMADA APPLICATION — MAPPERS (MapStruct)

```java
@Mapper(componentModel = "spring")
public interface AtividadeMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "projeto", source = "projeto", qualifiedByName = "projetoIdToProjeto")
    @Mapping(target = "status", source = "status", qualifiedByName = "stringToStatus")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "version", ignore = true)
    Atividade toEntity(AtividadeRequestDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDto(AtividadeUpdateDTO dto, @MappingTarget Atividade entity);

    AtividadeResponseDTO toResponse(Atividade entity);

    List<AtividadeResponseDTO> toResponseList(List<Atividade> entities);

    @Named("projetoIdToProjeto")
    default Projeto projetoIdToProjeto(Integer id) {
        return Projeto.ofId(id);
    }

    @Named("stringToStatus")
    default StatusAtividade stringToStatus(String status) {
        if (status == null) return null;
        return StatusAtividade.valueOf(status.trim().toUpperCase());
    }
}
```

**Regras:**
- Sempre `ignore = true` para: `id`, `createdAt`, `updatedAt`, `version`, coleções lazy
- `updateFromDto` é sempre `void` com `@MappingTarget` — nunca cria nova entidade
- Referências a entidades associadas: sempre via `ofId()` ou factory method

---

## 6. CAMADA ADAPTERS — CONTROLLERS

```java
@RestController
@RequestMapping("/api/v1/atividades")
@RequiredArgsConstructor
@Tag(name = "Atividades", description = "Gerenciamento de atividades de projeto")
public class AtividadeController {

    private final AtividadeService service;     // injeta a INTERFACE, nunca o Impl

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AtividadeResponseDTO create(@RequestBody @Valid AtividadeRequestDTO dto) {
        return service.create(dto);
    }

    @GetMapping("/{id}")
    public AtividadeResponseDTO getById(@PathVariable Integer id) {
        return service.getById(id);
    }

    @GetMapping
    public Page<AtividadeResponseDTO> list(Pageable pageable) {
        return service.list(pageable);
    }

    @PatchMapping("/{id}")
    public AtividadeResponseDTO update(@PathVariable Integer id,
                                       @RequestBody @Valid AtividadeUpdateDTO dto) {
        return service.update(id, dto);
    }

    @PutMapping("/{id}")
    public AtividadeResponseDTO replace(@PathVariable Integer id,
                                        @RequestBody @Valid AtividadeRequestDTO dto) {
        return service.replace(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        service.delete(id);
    }
}
```

**Regras:**
- Controller não tem lógica de negócio — apenas delega
- Injeta sempre a **interface** `XxxService`
- `POST` → `201 CREATED`; `DELETE` → `204 NO_CONTENT`
- `@Valid` obrigatório em todo `@RequestBody`

---

## 7. ESTRATÉGIA DE TESTES UNITÁRIOS

### 7.1 Identificar padrão Lombok antes de escrever o teste

Antes de criar qualquer fixture de teste, inspecionar a entidade alvo:

| Anotação na classe | Tem `.builder()`? | Como instanciar no teste |
|--------------------|-------------------|--------------------------|
| `@SuperBuilder` | ✅ Sim | `Entidade.builder()...build()` |
| `@Builder` | ✅ Sim | `Entidade.builder()...build()` |
| `@Data` + `@Builder` | ✅ Sim | `Dto.builder()...build()` |
| `@Data` (sem `@Builder`) | ❌ **Não** | `new XxxDTO()` + `dto.setXxx(valor)` |
| `@NoArgsConstructor(PROTECTED)` + `@Builder` | ✅ Sim (só builder) | `Entidade.builder()...build()` — `new` proibido |

> **Regra crítica:** `@Data` sozinho **não gera `.builder()`**. Verificar sempre se há `@Builder` ou `@SuperBuilder` combinado antes de usar `.builder()` no teste. DTOs de update costumam ter apenas `@Data`.

**Exemplo correto para DTO com `@Data` sem `@Builder`:**
```java
// ❌ Vai falhar em compilação — @Data não tem builder
var dto = ChecklistUpdateDTO.builder().nome("Novo").build();

// ✅ Correto
var dto = new ChecklistUpdateDTO();
dto.setNome("Novo");
```
### 7.0 Checklist obrigatório ANTES de escrever qualquer teste

| O que verificar | Onde olhar | Impacto no teste |
|---|---|---|
| Domain usa `@SuperBuilder`? | `core/domain/XxxEntity.java` | Usar `.builder().build()` — `new` proibido |
| Domain estende `AuditableEntity`? | mesma classe | Campo `ativo` é `Boolean` → usar `getAtivo()`, nunca `isAtivo()` |
| RequestDTO é `record` ou tem `@Builder`? | `dto/request/XxxRequestDTO.java` | `record` → construtor direto; `@Builder` → `.builder()` |
| ResponseDTO é `record` ou tem `@Builder`? | `dto/response/XxxResponseDTO.java` | `record` → construtor direto; `@Builder` → `.builder()` |
| UpdateDTO é `record`, `@Builder` ou só `@Data`? | `dto/update/XxxUpdateDTO.java` | `@Data` sem `@Builder` → `new XxxUpdateDTO()` + setters |
| Service usa `Repository` direto ou `Port`? | `service/impl/XxxServiceImpl.java` | Mock do tipo correto |
| Tipo do ID (`Integer`, `UUID`, `Long`)? | domain ou service | Fixtures e `findById()` com o tipo certo |
| Delete é soft ou hard? | `ServiceImpl.delete()` | Soft → `getAtivo() == false` + `verify(save)` |
| Exceção lançada? | `ServiceImpl` | `EntityNotFoundException` vs `RecursoNaoEncontradoException` |
| Service tem dependências extras? | campos do `ServiceImpl` | Adicionar `@Mock` para cada uma |


### 7.2 Configuração base do teste

```java
@ExtendWith(MockitoExtension.class)         // NUNCA @SpringBootTest em teste unitário
@DisplayName("XxxServiceImpl — testes unitários")
class XxxServiceImplTest {

    @Mock
    private XxxRepository repository;

    @Mock
    private XxxMapper mapper;               // mockar o mapper — não testar MapStruct aqui

    @InjectMocks
    private XxxServiceImpl service;         // SEMPRE o Impl, nunca a interface
}
```

### 7.3 Imports padrão completos

```java
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;
```

### 7.4 Cobertura mínima obrigatória — 8 cenários por ServiceImpl

| # | Método    | Cenário                                                                 |
|---|-----------|-------------------------------------------------------------------------|
| 1 | `create`  | sucesso: mapper.toEntity → save → mapper.toResponse chamados            |
| 2 | `getById` | encontrado: retorna response correto                                    |
| 3 | `getById` | não encontrado: lança `EntityNotFoundException` com id na mensagem      |
| 4 | `listAll` | com registros: retorna lista mapeada                                    |
| 5 | `update`  | sucesso: updateFromDto chamado → save chamado                           |
| 6 | `update`  | não encontrado: lança `EntityNotFoundException`                         |
| 7 | `replace` | sucesso: id injetado antes do save (verificar com `argThat`)            |
| 8 | `delete`  | soft delete: save chamado, `never().deleteById()` verificado            |

### 7.5 Template de teste — `create`

```java
@Test
@DisplayName("create — deve mapear, salvar e retornar response")
void create_success() {
    AtividadeRequestDTO dto = new AtividadeRequestDTO();
    dto.setDescricao("Nova atividade");
    dto.setStatus("PENDENTE");

    Atividade entity = Atividade.builder()
            .id(1).descricao("Nova atividade").status(StatusAtividade.PENDENTE).build();

    AtividadeResponseDTO response = new AtividadeResponseDTO();
    response.setId(1);

    when(mapper.toEntity(dto)).thenReturn(entity);
    when(repository.save(entity)).thenReturn(entity);
    when(mapper.toResponse(entity)).thenReturn(response);

    AtividadeResponseDTO result = service.create(dto);

    assertThat(result.getId()).isEqualTo(1);
    verify(mapper).toEntity(dto);
    verify(repository).save(entity);
    verify(mapper).toResponse(entity);
}
```

### 7.6 Template de teste — `getById` (EntityNotFoundException)

```java
@Test
@DisplayName("getById — deve lançar EntityNotFoundException quando não encontrado")
void getById_notFound() {
    when(repository.findById(99)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> service.getById(99))
            .isInstanceOf(EntityNotFoundException.class)
            .hasMessageContaining("99");

    verify(mapper, never()).toResponse(any());
}
```

### 7.7 Template de teste — `delete` (soft delete)

```java
@Test
@DisplayName("delete — deve setar ativo=false e salvar (soft delete)")
void delete_softDelete() {
    Atividade entity = Atividade.builder().id(1).build();
    when(repository.findById(1)).thenReturn(Optional.of(entity));

    service.delete(1);

    verify(repository).save(entity);
    verify(repository, never()).deleteById(any());
    assertThat(entity.getAtivo()).isFalse();
}
```

### 7.8 Template de teste — `replace` (verificar id injetado)

```java
@Test
@DisplayName("replace — deve injetar id na entidade antes de salvar")
void replace_injectsId() {
    AtividadeRequestDTO dto = new AtividadeRequestDTO();
    Atividade entity = Atividade.builder().id(0).build();

    when(repository.findById(1)).thenReturn(Optional.of(Atividade.builder().id(1).build()));
    when(mapper.toEntity(dto)).thenReturn(entity);
    when(repository.save(any())).thenReturn(entity);
    when(mapper.toResponse(any())).thenReturn(new AtividadeResponseDTO());

    service.replace(1, dto);

    verify(repository).save(argThat(e -> e.getId().equals(1)));
}
```

### 7.9 Estrutura de arquivos de teste

```
src/test/java/com/br/pruma/
└── application/
    └── service/
        └── impl/
            ├── AtividadeServiceImplTest.java
            ├── CronogramaServiceImplTest.java
            ├── ChecklistServiceImplTest.java
            └── ...        ← espelha o path do source
```

Sufixo sempre `Test` (não `Tests`).

---

## 8. CHECKLIST — ANTES DE ESCREVER QUALQUER CÓDIGO

### Entidades
- [ ] Herda `AuditableEntity`? → usar `@SuperBuilder`
- [ ] Tem `@SQLDelete`? → soft delete ativo, `delete()` no service usa `save()`
- [ ] Tem `@SQLRestriction`? → queries filtram `ativo = true` automaticamente
- [ ] Tem `@Version`? → protegido contra race condition (optimistic lock)

### Services
- [ ] Interface `XxxService` existe?
- [ ] Impl usa `@Service @RequiredArgsConstructor @Transactional`?
- [ ] Métodos de leitura têm `@Transactional(readOnly = true)`?
- [ ] `orElseThrow(EntityNotFoundException)` em todos os `findById()`?
- [ ] `delete()` faz soft delete (`setAtivo(false)` + `save()`)?

### Mappers
- [ ] `toEntity` ignora `id`, `createdAt`, `updatedAt`, `version`?
- [ ] `updateFromDto` usa `NullValuePropertyMappingStrategy.IGNORE`?
- [ ] Conversão de enum usa `@Named` com `null` check e `toUpperCase()`?

### Testes
- [ ] Entidades via `.builder().build()` — nunca `new`?
- [ ] DTOs via `new XxxDTO()` + setters?
- [ ] `@InjectMocks` aponta para `XxxServiceImpl`?
- [ ] Todos os 8 cenários cobertos?
- [ ] `delete` verifica `never().deleteById()`?
- [ ] `replace` verifica id com `argThat`?
- [ ] Exception verifica que o id está na mensagem (`hasMessageContaining`)?

---

## 9. ANTIPADRÕES — NUNCA FAÇA

| ❌ Antipadrão | ✅ Correto |
|---|---|
| `new Entidade()` em teste ou service | `Entidade.builder()...build()` |
| `@InjectMocks XxxService service` | `@InjectMocks XxxServiceImpl service` |
| `repository.deleteById(id)` no delete | `entity.setAtivo(false); repository.save(entity)` |
| `Optional.get()` sem orElseThrow | `.orElseThrow(() -> new EntityNotFoundException(...))` |
| `@SpringBootTest` em teste unitário | `@ExtendWith(MockitoExtension.class)` |
| Injetar `XxxServiceImpl` no Controller | Injetar `XxxService` (interface) |
| Lógica de negócio no Controller | Delegar tudo ao Service |
| `@Autowired` em campo | `@RequiredArgsConstructor` + `final` |
| `EntityNotFoundException` sem mensagem | Mensagem com o id: `"Entidade não encontrada: " + id` |
| Testar mapper em teste de service | Mockar o mapper — testar só o service |
| `@Mock XxxServiceImpl` no teste | `@Mock XxxService` (interface) |

---

## 10. REFERÊNCIA RÁPIDA — MAPA DE DEPENDÊNCIAS

```
Controller
    └── injeta → XxxService (interface)
                    └── implementado por → XxxServiceImpl
                                             ├── injeta → XxxRepository
                                             └── injeta → XxxMapper
                                                            └── usa → Entidade.builder() / ofId()
```

Cada camada conhece apenas a camada imediatamente abaixo — nunca pula camadas.

---

## 11. JAVA 21 — FEATURES HABILITADAS E PADRÕES DO PROJETO

> O projeto exige Java 21+ via `maven-enforcer-plugin` (`<requireJavaVersion>[21,)</requireJavaVersion>`).
> Spring Boot 3.5.x já tira proveito das APIs modernas do Java 21. Use as features abaixo — elas **já funcionam** no projeto sem nenhuma configuração extra.

### 11.1 `.toList()` em vez de `collect(Collectors.toList())`

Java 21 (na verdade desde Java 16) entrega `Stream.toList()` — imutável, mais legível, zero import.  
O projeto **já usa** esse padrão nos services:

```java
// ✅ Padrão do projeto — Java 16+, disponível no Java 21
return repository.findAll().stream()
        .map(mapper::toResponse)
        .toList();

// ❌ Verboso — não usar
return repository.findAll().stream()
        .map(mapper::toResponse)
        .collect(Collectors.toList());
```

**Atenção:** `Stream.toList()` retorna lista **imutável**. Se precisar adicionar elementos depois, use `new ArrayList<>(lista)`.

---

### 11.2 Records para Projeções e Respostas Simples

Use `record` para DTOs de resposta leves, projeções de query ou objetos de transferência internos que são **imutáveis por natureza**:

```java
// ✅ Record — imutável, compacto, equals/hashCode/toString automáticos
public record ResumoProjetoDTO(Integer id, String nome, String status) {}

// Uso no service
repository.findAll().stream()
        .map(p -> new ResumoProjetoDTO(p.getId(), p.getNome(), p.getStatus().name()))
        .toList();
```

**Quando usar `record` vs `@Data`:**

| Situação | Usar |
|---|---|
| DTO de resposta read-only, sem setter | `record` |
| DTO de request/update (precisa de setter ou `@Valid`) | `@Data` class |
| Projeção de query JPA com interface Spring | interface de projeção |
| Objeto mutável com Lombok | `@Data` / `@Builder` class |

> **Não use `record` para entidades JPA** — Hibernate exige no-args constructor e mutabilidade.

---

### 11.3 Pattern Matching para `instanceof`

Elimine casts explícitos — Java 16+ (disponível no 21):

```java
// ✅ Pattern matching — Java 21
if (objeto instanceof EntityNotFoundException ex) {
    log.error("Entidade não encontrada: {}", ex.getMessage());
}

// ❌ Verboso — não usar
if (objeto instanceof EntityNotFoundException) {
    EntityNotFoundException ex = (EntityNotFoundException) objeto;
    log.error("Entidade não encontrada: {}", ex.getMessage());
}
```

---

### 11.4 Switch Expressions (Arrow Switch)

Use em conversões de enum e lógica condicional com retorno — disponível desde Java 14, estável no 21:

```java
// ✅ Switch expression — Java 21
String descricaoStatus = switch (atividade.getStatus()) {
    case PENDENTE    -> "Aguardando início";
    case EM_PROGRESSO -> "Em andamento";
    case CONCLUIDA   -> "Finalizada";
    case CANCELADA   -> "Cancelada";
};

// ❌ Switch statement clássico — mais verboso
String descricaoStatus;
switch (atividade.getStatus()) {
    case PENDENTE: descricaoStatus = "Aguardando início"; break;
    // ...
}
```

> **No Mapper:** prefira `switch expression` a `if-else` encadeado para conversões de enum com muitos casos.

---

### 11.5 Text Blocks para SQL e Mensagens Longas

Use text blocks (Java 15+, disponível no 21) em queries JPQL/SQL nativas e mensagens de erro longas:

```java
// ✅ Text block — legível, sem concatenação
@Query("""
    SELECT p FROM Projeto p
    WHERE p.ativo = true
      AND p.status = :status
      AND p.dataInicio >= :dataInicio
    ORDER BY p.dataInicio DESC
    """)
List<Projeto> findByStatusAndDataInicio(
        @Param("status") StatusProjeto status,
        @Param("dataInicio") LocalDate dataInicio);

// ❌ Concatenação de string — difícil de ler e manter
@Query("SELECT p FROM Projeto p WHERE p.ativo = true " +
       "AND p.status = :status AND p.dataInicio >= :dataInicio " +
       "ORDER BY p.dataInicio DESC")
```

---

### 11.6 `var` para Variáveis Locais Óbvias

Use `var` quando o tipo é evidente pelo lado direito — reduz repetição sem perder clareza:

```java
// ✅ var — tipo óbvio pelo contexto
var entity = repository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Atividade não encontrada: " + id));

var response = mapper.toResponse(entity);

// ❌ Evitar var quando o tipo não é imediatamente claro
var x = projetoService.processar(dto);   // o que retorna processar()?
```

**Regra:** se remover o `var` e adicionar o tipo explícito não acrescenta informação útil ao leitor, use `var`. Se acrescenta, use o tipo explícito.

---

### 11.7 Virtual Threads (Project Loom) — Awareness

Java 21 introduziu **Virtual Threads** como feature estável. O Spring Boot 3.2+ habilita suporte nativo via propriedade:

```yaml
# application.yml — habilitar Virtual Threads no projeto
spring:
  threads:
    virtual:
      enabled: true
```

**Impacto no projeto Pruma:**
- Cada request HTTP passa a rodar em uma virtual thread — melhora throughput em operações I/O-bound (banco, e-mail, JWT)
- **Não muda nada no código** dos services ou controllers — é transparente
- `@Transactional` continua funcionando normalmente
- **Atenção:** não use `synchronized` em código que pode rodar em virtual threads — prefira `ReentrantLock` ou estruturas de `java.util.concurrent`

---

### 11.8 Sealed Classes para Hierarquias de Domínio Fechadas

Use `sealed` quando o conjunto de subtipos é fixo e conhecido — útil para modelar estados ou resultados:

```java
// ✅ Sealed class — hierarquia fechada e explícita
public sealed interface ResultadoOperacao
        permits ResultadoOperacao.Sucesso, ResultadoOperacao.Falha {

    record Sucesso(Object dados) implements ResultadoOperacao {}
    record Falha(String mensagem, int codigo) implements ResultadoOperacao {}
}

// Uso com pattern matching
ResultadoOperacao resultado = service.executar(dto);
return switch (resultado) {
    case ResultadoOperacao.Sucesso s -> ResponseEntity.ok(s.dados());
    case ResultadoOperacao.Falha f  -> ResponseEntity.status(f.codigo()).body(f.mensagem());
};
```

> No projeto Pruma, aplique `sealed` com moderação — apenas quando a hierarquia for realmente fechada e o switch exhaustivo agregar valor real.

---

### 11.9 O que NÃO usar ainda no projeto

| Feature Java 21 | Motivo para evitar |
|---|---|
| `String Templates` (JEP 430) | Preview no Java 21, estável só no Java 23+ — não usar |
| `Unnamed Classes` (JEP 445) | Preview — não usar em produção |
| `Scoped Values` (JEP 446) | Preview no 21 — use `ThreadLocal` enquanto não estabilizar |
| `Structured Concurrency` (JEP 453) | Preview no 21 — aguardar GA |

---

### 11.10 Resumo: O que usar vs evitar

| ✅ Usar no projeto | ❌ Evitar |
|---|---|
| `.toList()` nos streams | `collect(Collectors.toList())` |
| `record` para DTOs imutáveis | `record` para entidades JPA |
| Pattern matching `instanceof` | Cast explícito com if/instanceof |
| Switch expression (`->`) | Switch statement com `break` |
| Text blocks para JPQL/SQL | Concatenação de string em queries |
| `var` quando tipo é óbvio | `var` quando tipo é ambíguo |
| Virtual Threads via propriedade | `synchronized` em virtual threads |
| `sealed` para hierarquias fechadas | `sealed` para tudo indiscriminadamente |
