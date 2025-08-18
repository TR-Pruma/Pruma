package com.br.pruma.core.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "fornecedor")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class Fornecedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fornecedor_id")
    @EqualsAndHashCode.Include
    private Integer id;

    @Column(name = "nome", length = 255, nullable = false)
    private String nome;

    @Column(name = "cnpj", length = 255, nullable = false, unique = true)
    private String cnpj;

    @Column(name = "contato", length = 255, nullable = false)
    private String contato;
}