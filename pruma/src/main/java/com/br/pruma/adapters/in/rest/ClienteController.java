package com.br.pruma.adapters.in.rest;

import com.br.pruma.application.dto.request.ClienteRequestDTO;
import com.br.pruma.application.dto.response.ClienteResponseDTO;
import com.br.pruma.core.repository.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;


@RestController
@RequestMapping("/pruma/v1/clientes")
@Validated
@RequiredArgsConstructor

public class ClienteController {

    private final ClienteService clienteService;

    @Operation(summary = "Cria um novo cliente")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Cliente criado",
                    content = @Content(schema = @Schema(implementation = ClienteResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PostMapping
    public ResponseEntity<ClienteResponseDTO> create(
            @Valid @RequestBody ClienteRequestDTO request) {

        ClienteResponseDTO created = clienteService.create(request);
        URI location = URI.create("/pruma/v1/clientes/" + created.id());
        return ResponseEntity.created(location).body(created);
    }

    @Operation(summary = "Atualiza um cliente existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cliente atualizado",
                    content = @Content(schema = @Schema(implementation = ClienteResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> update(
            @Parameter(description = "ID do cliente") @PathVariable Integer id,
            @Valid @RequestBody ClienteRequestDTO request) {
        ClienteResponseDTO updated = clienteService.update(id, request);
        return ResponseEntity.ok(updated);
    }

    @Operation(summary = "Lista todos os clientes")
    @ApiResponse(responseCode = "200", description = "Lista de clientes",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = ClienteResponseDTO.class))))
    @GetMapping
    public ResponseEntity<List<ClienteResponseDTO>> findAll() {
        return ResponseEntity.ok(clienteService.findAll());
    }

    @Operation(summary = "Busca cliente por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cliente encontrado",
                    content = @Content(schema = @Schema(implementation = ClienteResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> findById(
            @Parameter(description = "ID do cliente") @PathVariable Integer id) {
        return ResponseEntity.ok(clienteService.findById(id));
    }

    @Operation(summary = "Remove um cliente")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Cliente excluído"),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID do cliente") @PathVariable Integer id) {
        clienteService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
