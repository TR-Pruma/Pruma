package com.br.pruma.adapters.in.rest;

import com.br.pruma.application.dto.request.CadastroRequestDTO;
import com.br.pruma.application.dto.request.LoginRequestDTO;
import com.br.pruma.application.dto.response.TokenResponseDTO;
import com.br.pruma.application.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuario")
@Tag(name = "Autenticação")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping("/login")
    @Operation(summary = "Login com CPF e senha")
    public ResponseEntity<TokenResponseDTO> login(@Valid @RequestBody LoginRequestDTO dto) {
        return ResponseEntity.ok(usuarioService.autenticar(dto));
    }

    @PostMapping("/cadastro")
    @Operation(summary = "Cadastrar novo usuário")
    public ResponseEntity<Void> cadastrar(@Valid @RequestBody CadastroRequestDTO dto) {
        usuarioService.cadastrar(dto);
        return ResponseEntity.status(201).build();
    }
}
