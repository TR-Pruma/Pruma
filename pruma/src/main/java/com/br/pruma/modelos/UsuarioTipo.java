package com.br.pruma.modelos;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Builder
@SuperBuilder
@AllArgsConstructor
@ToString
@RequiredArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class UsuarioTipo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer tipoUsuario;

    private String descricaoUsuario;
}
