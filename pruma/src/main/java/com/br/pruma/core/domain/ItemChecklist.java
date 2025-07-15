package com.br.pruma.core.domain;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "item_checklist")
@Data
public class ItemChecklist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "checklist_id", referencedColumnName = "checklist_id")
    private Integer checklist;

    @Column(name = "descricao", length = 255)
    private String descricao;

    @Column(name = "status", length = 20)
    private String status;
}
