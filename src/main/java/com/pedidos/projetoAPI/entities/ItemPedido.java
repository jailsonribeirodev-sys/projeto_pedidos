package com.pedidos.projetoAPI.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pedidos.projetoAPI.entities.pk.ItemPedidoPK;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * Entidade JPA que representa a tabela tb_item_pedido no banco de dados.
 * Conecta um Pedido a um Produto especificando a quantidade e o preço no
 * momento da compra.
 */
@Entity
@Table(name = "tb_item_pedido")
public class ItemPedido implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    // Chave primária composta contendo Pedido e Produto
    @EmbeddedId
    private ItemPedidoPK id = new ItemPedidoPK();

    private Integer quantidade;
    private BigDecimal preco;

    public ItemPedido() {
    }

    public ItemPedido(Pedido pedido, Produto produto, Integer quantidade, BigDecimal preco) {
        id.setPedido(pedido);
        id.setProduto(produto);
        this.quantidade = quantidade;
        this.preco = preco;

    }

    // Evita referência circular ao serializar o Pedido em JSON
    @JsonIgnore
    public Pedido getPedido() {
        return id.getPedido();
    }

    public void setPedido(Pedido pedido) {
        id.setPedido(pedido);
    }

    public Produto getProduto() {
        return id.getProduto();
    }

    public void setProduto(Produto produto) {
        id.setProduto(produto);
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    /**
     * Calcula o valor subtotal do item (quantidade * preço).
     */
    public BigDecimal getSubTotal() {
        if (preco == null || quantidade == null) {
            return BigDecimal.ZERO;
        }
        return preco.multiply(BigDecimal.valueOf(quantidade));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        ItemPedido that = (ItemPedido) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
