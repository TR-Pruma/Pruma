package com.br.pruma.adapters.in.rest;

import com.br.pruma.application.dto.request.PermissaoUsuarioRequestDTO;
import com.br.pruma.application.dto.response.PermissaoUsuarioResponseDTO;
import com.br.pruma.application.dto.update.PermissaoUsuarioUpdateDTO;
import com.br.pruma.application.service.PermissaoUsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Tag(name = "PermissaoUsuario", description = "Operações relacionadas a permissões de usuários")
@RestController
@RequestMapping("/pruma/v1/permissoes-usuario")
@RequiredArgsConstructor
public class PermissaoUsuarioController {

    private final PermissaoUsuarioService service;

    @Operation(summary = "Lista todas as permissões de usuário")
    @GetMapping
    public ResponseEntity<List<PermissaoUsuarioResponseDTO>> listarTodos() {
        return ResponseEntity.ok(service.listAll());
    }

    @Operation(summary = "Lista permissões por usuário")
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<PermissaoUsuarioResponseDTO>> listarPorUsuario(@PathVariable Integer usuarioId) {
        return ResponseEntity.ok(service.listByUsuario(usuarioId));
    }

    @Operation(summary = "Busca permissão de usuário por ID")
    @GetMapping("/{id}")
    public ResponseEntity<PermissaoUsuarioResponseDTO> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @Operation(summary = "Cria nova permissão de usuário")
    @PostMapping
    public ResponseEntity<PermissaoUsuarioResponseDTO> criar(@RequestBody @Valid PermissaoUsuarioRequestDTO dto) {
        PermissaoUsuarioResponseDTO salvo = service.create(dto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(salvo.getId()).toUri();
        return ResponseEntity.created(location).body(salvo);
    }

    @Operation(summary = "Atualiza permissão de usuário por ID")
    @PatchMapping("/{id}")
    public ResponseEntity<PermissaoUsuarioResponseDTO> atualizar(@PathVariable Integer id,
                                                                 @RequestBody @Valid PermissaoUsuarioUpdateDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @Operation(summary = "Deleta permissão de usuário por ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
