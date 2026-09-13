package com.pedidos.projetoAPI.dtos;

/**
 * CADA LINHA DO BILHETE DE COMPRA (O que o cliente colocou na sacola).
 * O cliente só envia qual produto quer e a quantidade. 
 * Note que NÃO tem preço nem subtotal aqui (evita fraudes!).
 */
public record ItemPedidoCreateDTO(
        // ID do produto que o cliente escolheu (ex: Mouse Gamer id = 10)
        Long produtoId,

        // Quantas unidades desse produto ele quer comprar (ex: 2)
        Integer quantidade
) {
}

