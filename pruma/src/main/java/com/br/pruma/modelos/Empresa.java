package com.br.pruma.modelos;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "empresa")
public class Empresa {

    @Id
    @Column(name = "empresa_cnpj")
    private Long cnpj;

    @Column(name = "razao_social", length = 50, nullable = false)
    private String razaoSocial;

    @Column(name = "nome_fantasia", length = 50, nullable = false)
    private String nomeFantasia;
}
