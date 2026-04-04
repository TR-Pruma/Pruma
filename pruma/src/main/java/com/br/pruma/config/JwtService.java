package com.br.pruma.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * Serviço responsável por geração, validação e extração de informações de tokens JWT.
 *
 * <p><b>Segurança:</b> o {@code JWT_SECRET} deve ter no mínimo 32 caracteres (256 bits)
 * para garantir a segurança do algoritmo HMAC-SHA256. Em produção, gere com:</p>
 * <pre>{@code openssl rand -base64 64}</pre>
 *
 * <p>A chave é derivada uma única vez no startup via {@link #init()} e cacheada,
 * evitando reprocessamento a cada request.</p>
 */
@Slf4j
@Component
public class JwtService {

    /** Tamanho mínimo do secret em bytes para HS256 (256 bits = 32 bytes). */
    private static final int MINIMUM_SECRET_LENGTH = 32;

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    /** Chave derivada uma única vez no startup e reutilizada em todas as operações. */
    private SecretKey cachedKey;

    /**
     * Valida o secret e inicializa a {@link SecretKey} cacheada.
     * Falha imediatamente no startup (fail-fast) se o secret estiver ausente ou fraco.
     *
     * @throws IllegalStateException se {@code jwt.secret} não estiver definido
     *                               ou tiver menos de {@value #MINIMUM_SECRET_LENGTH} caracteres.
     */
    @PostConstruct
    void init() {
        if (secret == null || secret.isBlank()) {
            throw new IllegalStateException(
                    "[PRUMA] jwt.secret não está definido. " +
                    "Defina a variável de ambiente JWT_SECRET. " +
                    "Gere um valor seguro com: openssl rand -base64 64"
            );
        }

        byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);
        if (keyBytes.length < MINIMUM_SECRET_LENGTH) {
            throw new IllegalStateException(
                    "[PRUMA] jwt.secret tem apenas " + keyBytes.length + " bytes. " +
                    "Mínimo exigido: " + MINIMUM_SECRET_LENGTH + " bytes (256 bits para HS256). " +
                    "Gere um valor seguro com: openssl rand -base64 64"
            );
        }

        this.cachedKey = Keys.hmacShaKeyFor(keyBytes);
        log.info("[JWT] SecretKey inicializada com sucesso ({} bytes).", keyBytes.length);
    }

    /**
     * Gera um token JWT assinado com o ID interno do usuário.
     * Nunca inclui CPF ou dados pessoais no payload (LGPD).
     *
     * @param usuarioId ID interno do usuário
     * @param role      papel do usuário (ex: ADMIN, PROFISSIONAL)
     * @return token JWT assinado
     */
    public String gerarToken(Integer usuarioId, String role) {
        return Jwts.builder()
                .subject(String.valueOf(usuarioId))
                .claim("role", role)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(cachedKey)
                .compact();
    }

    /**
     * Extrai o ID do usuário do subject do token.
     *
     * @param token JWT válido
     * @return ID interno do usuário
     */
    public Integer extrairUsuarioId(String token) {
        return Integer.valueOf(getClaims(token).getSubject());
    }

    /**
     * Extrai o papel (role) do token.
     *
     * @param token JWT válido
     * @return role do usuário
     */
    public String extrairRole(String token) {
        return getClaims(token).get("role", String.class);
    }

    /**
     * Valida o token JWT verificando assinatura e expiração.
     * Distingue os tipos de falha para melhor observabilidade nos logs.
     *
     * @param token JWT a ser validado
     * @return {@code true} se o token for válido e não estiver expirado
     */
    public boolean tokenValido(String token) {
        try {
            return getClaims(token).getExpiration().after(new Date());
        } catch (ExpiredJwtException e) {
            log.debug("Token expirado: {}", e.getMessage());
            return false;
        } catch (SignatureException e) {
            log.warn("Assinatura JWT inválida — possível tentativa de adulteração");
            return false;
        } catch (MalformedJwtException e) {
            log.warn("Token JWT malformado");
            return false;
        } catch (Exception e) {
            log.error("Erro inesperado ao validar token JWT", e);
            return false;
        }
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(cachedKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
