package com.br.pruma.adapters.in.rest;

import com.br.pruma.application.dto.request.ClienteRequestDTO;
import com.br.pruma.application.dto.response.ClienteResponseDTO;
import com.br.pruma.core.repository.ClienteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
@Api(tags = "Clientes", description = "Operações relacionadas a clientes")
public class ClienteController {

    private final ClienteService clienteService;

    @PostMapping
    @ApiOperation("Criar um novo cliente")
    public ResponseEntity<ClienteResponseDTO> criar(
            @ApiParam(value = "Dados do cliente", required = true)
            @Valid @RequestBody ClienteRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(clienteService.criar(request));
    }

    @GetMapping
    @ApiOperation("Listar todos os clientes ativos")
    public ResponseEntity<Page<ClienteResponseDTO>> listarTodos(Pageable pageable) {
        return ResponseEntity.ok(clienteService.listarTodos(pageable));
    }

    @GetMapping("/{id}")
    @ApiOperation("Buscar um cliente pelo ID")
    public ResponseEntity<ClienteResponseDTO> buscarPorId(
            @ApiParam(value = "ID do cliente", required = true)
            @PathVariable Integer id) {
        return ResponseEntity.ok(clienteService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    @ApiOperation("Atualizar um cliente existente")
    public ResponseEntity<ClienteResponseDTO> atualizar(
            @ApiParam(value = "ID do cliente", required = true)
            @PathVariable Integer id,
            @ApiParam(value = "Novos dados do cliente", required = true)
            @Valid @RequestBody ClienteRequestDTO request) {
        return ResponseEntity.ok(clienteService.atualizar(id, request));
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Desativar um cliente")
    public ResponseEntity<Void> deletar(
            @ApiParam(value = "ID do cliente", required = true)
            @PathVariable Integer id) {
        clienteService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/cpf/{cpf}")
    @ApiOperation("Buscar um cliente pelo CPF")
    public ResponseEntity<ClienteResponseDTO> buscarPorCpf(
            @ApiParam(value = "CPF do cliente", required = true)
            @PathVariable String cpf) {
        return ResponseEntity.ok(clienteService.buscarPorCpf(cpf));
    }

    @GetMapping("/email/{email}")
    @ApiOperation("Buscar um cliente pelo email")
    public ResponseEntity<ClienteResponseDTO> buscarPorEmail(
            @ApiParam(value = "Email do cliente", required = true)
            @PathVariable String email) {
        return ResponseEntity.ok(clienteService.buscarPorEmail(email));
    }
}
