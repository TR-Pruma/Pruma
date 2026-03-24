package com.br.pruma.application.service;

import com.br.pruma.application.dto.request.CadastroRequestDTO;
import com.br.pruma.application.dto.request.LoginRequestDTO;
import com.br.pruma.application.dto.response.TokenResponseDTO;
import com.br.pruma.config.JwtService;
import com.br.pruma.core.domain.Usuario;
import com.br.pruma.core.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final AuthenticationManager authManager;
    private final JwtService jwtService;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public TokenResponseDTO autenticar(LoginRequestDTO dto) {
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getCpf(), dto.getSenha())
        );
        Usuario usuario = usuarioRepository.findByCpf(dto.getCpf())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        String token = jwtService.gerarToken(usuario.getCpf());
        return new TokenResponseDTO(token, "Bearer", usuario.getCpf(), usuario.getTipo().name());
    }

    public void cadastrar(CadastroRequestDTO dto) {
        if (usuarioRepository.existsByCpf(dto.getCpf())) {
            throw new RuntimeException("CPF já cadastrado");
        }
        Usuario novo = Usuario.builder()
                .cpf(dto.getCpf())
                .senha(passwordEncoder.encode(dto.getSenha()))
                .tipo(dto.getTipo())
                .build();
        usuarioRepository.save(novo);
    }
}
