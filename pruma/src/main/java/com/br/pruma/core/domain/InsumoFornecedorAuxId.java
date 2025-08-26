package com.br.pruma.core.domain;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class InsumoFornecedorAuxId implements Serializable {

    private Integer insumoId;
    private Integer fornecedorId;

    public InsumoFornecedorAuxId() {
    }

    public InsumoFornecedorAuxId(Integer insumoId, Integer fornecedorId) {
        this.insumoId = insumoId;
        this.fornecedorId = fornecedorId;
    }

    public Integer getInsumoId() {
        return insumoId;
    }

    public void setInsumoId(Integer insumoId) {
        this.insumoId = insumoId;
    }

    public Integer getFornecedorId() {
        return fornecedorId;
    }

    public void setFornecedorId(Integer fornecedorId) {
        this.fornecedorId = fornecedorId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof InsumoFornecedorAuxId that)) return false;
        return Objects.equals(insumoId, that.insumoId) &&
                Objects.equals(fornecedorId, that.fornecedorId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(insumoId, fornecedorId);
    }
}
