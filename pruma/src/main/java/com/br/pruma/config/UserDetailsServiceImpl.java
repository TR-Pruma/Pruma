package com.br.pruma.config;

import com.br.pruma.core.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    /**
     * O campo 'username' usado pelo Spring Security é o CPF do usuário.
     * O AuthenticationManager chama este método durante o login para
     * carregar as credenciais e validar a senha via PasswordEncoder.
     */
    @Override
    public UserDetails loadUserByUsername(String cpf) throws UsernameNotFoundException {
        return usuarioRepository.findByCpf(cpf)
                .map(usuario -> User.builder()
                        .username(usuario.getCpf())
                        .password(usuario.getSenha())
                        .authorities(List.of(new SimpleGrantedAuthority(usuario.getTipo().name())))
                        .build())
                .orElseThrow(() -> new UsernameNotFoundException(
                        "Usuário com CPF '" + cpf + "' não encontrado."));
    }
}
