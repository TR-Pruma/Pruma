package com.br.pruma.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Slf4j
@Component
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    private SecretKey getKey() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8)); // fix #4
    }

    // fix #1 — usa ID interno, nunca CPF (LGPD)
    public String gerarToken(Integer usuarioId, String role) {
        return Jwts.builder()
                .subject(String.valueOf(usuarioId))
                .claim("role", role)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getKey())   // fix API deprecated
                .compact();
    }

    public Integer extrairUsuarioId(String token) {
        return Integer.valueOf(getClaims(token).getSubject());
    }

    public String extrairRole(String token) {
        return getClaims(token).get("role", String.class);
    }

    // fix #3 — distingue tipos de exceção para observabilidade
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
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
