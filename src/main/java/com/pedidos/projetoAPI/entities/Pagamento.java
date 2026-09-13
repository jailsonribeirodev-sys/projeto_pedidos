package com.pedidos.projetoAPI.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pedidos.projetoAPI.entities.enums.StatusPagamento;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * Entidade JPA que representa a tabela tb_pagamento no banco de dados.
 * Possui relacionamento 1 para 1 com o Pedido correspondente.
 */
@Entity
@Table(name = "tb_pagamento")
public class Pagamento implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Instant instante;
    private Integer status;

    // Relacionamento 1:1 onde a chave primária de Pagamento é o próprio id de Pedido
    @JsonIgnore
    @OneToOne
    @MapsId
    private Pedido pedido;

    public Pagamento() {
    }

    public Pagamento(Long id, Instant instante, StatusPagamento status, Pedido pedido) {
        this.id = id;
        this.instante = instante;
        setStatus(status);
        this.pedido = pedido;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getInstante() {
        return instante;
    }

    public void setInstante(Instant instante) {
        this.instante = instante;
    }

    public StatusPagamento getStatus() {
        return status != null ? StatusPagamento.valueOf(status) : null;
    }

    public void setStatus(StatusPagamento status) {
        if (status != null) {
            this.status = status.getCode();
        }
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pagamento pagamento = (Pagamento) o;
        return Objects.equals(id, pagamento.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
