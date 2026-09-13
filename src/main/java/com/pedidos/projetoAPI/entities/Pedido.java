package com.pedidos.projetoAPI.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pedidos.projetoAPI.entities.enums.StatusPedido;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Entidade JPA principal que representa a tabela tb_pedido no banco de dados.
 * Conecta o Cliente, os Itens do Pedido e o Pagamento.
 */
@Entity
@Table(name = "tb_pedido")
public class Pedido implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Formatação ISO-8601 UTC para a data e hora do pedido
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
    private Instant instante;

    private Integer status;

    // Relacionamento N:1 - Vários pedidos pertencem a um único cliente
    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    // Relacionamento 1:1 com o Pagamento correspondente
    @OneToOne(mappedBy = "pedido", cascade = CascadeType.ALL)
    private Pagamento pagamento;

    // Coleção de itens do pedido (produtos + quantidades + preços)
    @OneToMany(mappedBy = "id.pedido", cascade = CascadeType.ALL)
    private Set<ItemPedido> itens = new HashSet<>();

    public Pedido() {
    }

    public Pedido(Long id, Instant instante, StatusPedido status, Cliente cliente) {
        this.id = id;
        this.instante = instante;
        setStatus(status);
        this.cliente = cliente;
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

    public StatusPedido getStatus() {
        return status != null ? StatusPedido.valueOf(status) : null;
    }

    public void setStatus(StatusPedido status) {
        if (status != null) {
            this.status = status.getCode();
        }
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Pagamento getPagamento() {
        return pagamento;
    }

    public void setPagamento(Pagamento pagamento) {
        this.pagamento = pagamento;
    }

    public Set<ItemPedido> getItens() {
        return itens;
    }

    /**
     * Calcula o valor total acumulado do pedido somando o subtotal de todos os
     * itens.
     */
    public BigDecimal getTotal() {
        BigDecimal sum = BigDecimal.ZERO;
        for (ItemPedido item : itens) {
            sum = sum.add(item.getSubTotal());
        }
        return sum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Pedido pedido = (Pedido) o;
        return Objects.equals(id, pedido.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
