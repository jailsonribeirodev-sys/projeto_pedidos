package com.pedidos.projetoAPI.dtos;

import com.pedidos.projetoAPI.entities.ItemPedido;

import java.math.BigDecimal;

/**
 * CADA LINHA DO COMPROVANTE DE SAÍDA (O que o usuário vê na resposta JSON).
 * Veja que aqui NÃO tem o campo "pedido", quebrando qualquer possibilidade de loop infinito!
 */
public record ItemPedidoDTO(
        Long produtoId,       // O ID do produto comprado
        String produtoNome,   // O Nome do produto para exibir na tela (ex: "Mouse Gamer")
        BigDecimal preco,     // O Preço unitário que foi cobrado no momento da compra
        Integer quantidade,   // Quantas unidades foram compradas
        BigDecimal subTotal   // O cálculo: (preco * quantidade)
) {
    /**
     * Construtor auxiliar: Recebe a Entidade ItemPedido (cheia de amarras do banco)
     * e extrai apenas os dados puros para preencher este DTO leve.
     */
    public ItemPedidoDTO(ItemPedido entity) {
        this(
                entity.getProduto().getId(),
                entity.getProduto().getNome(),
                entity.getPreco(),
                entity.getQuantidade(),
                entity.getSubTotal());
    }
}

