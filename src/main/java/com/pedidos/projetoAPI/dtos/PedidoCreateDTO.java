package com.pedidos.projetoAPI.dtos;

import java.util.List;

/**
 * DTO DE ENTRADA (O "Bilhete" que o celular da Maria envia para a API).
 * Contém apenas o mínimo necessário para abrir uma compra: QUEM compra e O QUE compra.
 */
public record PedidoCreateDTO(
        // O identificador do cliente que está comprando (ex: Maria id = 1)
        Long clienteId,

        // A lista de itens/produtos que estão dentro do carrinho de compras
        List<ItemPedidoCreateDTO> itens
) {
}

