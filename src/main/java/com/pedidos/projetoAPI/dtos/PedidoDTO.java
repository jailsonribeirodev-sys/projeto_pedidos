package com.pedidos.projetoAPI.dtos;

import com.pedidos.projetoAPI.entities.Pedido;
import com.pedidos.projetoAPI.entities.enums.StatusPedido;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * O COMPROVANTE COMPLETO DO PEDIDO (A resposta final que vai para o cliente via JSON).
 * Reúne todas as informações consolidadas: quem comprou, o que comprou, quanto deu a conta.
 */
public record PedidoDTO(
        Long id,                  // Número oficial do pedido gerado pelo banco
        Instant instante,         // Data e hora em que a compra aconteceu
        StatusPedido status,      // Situação atual (AGUARDANDO_PAGAMENTO, PAGO, CANCELADO)
        ClienteDTO cliente,       // Os dados limpos do comprador (sem loop infinito!)
        PagamentoDTO pagamento,   // Dados do pagamento (se já foi pago, senão vem null)
        Set<ItemPedidoDTO> itens, // A lista dos produtos com preços e subtotais
        BigDecimal total          // O valor total final da compra calculado
) {
    /**
     * Construtor que recebe a Entidade Pedido (vinda do banco) e "desempacota" tudo
     * para dentro deste DTO leve para ser enviado pela internet em formato JSON.
     */
    public PedidoDTO(Pedido entity) {
        this(
                entity.getId(),
                entity.getInstante(),
                entity.getStatus(),
                // Se o pedido tiver cliente associado, converte o Cliente em ClienteDTO
                entity.getCliente() != null ? new ClienteDTO(entity.getCliente()) : null,
                // Se o pedido já tiver pagamento associado, converte em PagamentoDTO, senão deixa null
                entity.getPagamento() != null ? new PagamentoDTO(entity.getPagamento()) : null,
                // Pega cada ItemPedido da entidade e transforma em ItemPedidoDTO usando Stream
                entity.getItens() != null
                        ? entity.getItens().stream().map(ItemPedidoDTO::new).collect(Collectors.toSet())
                        : Set.of(),
                // Pede para a entidade calcular a soma de todos os subtotais dos itens
                entity.getTotal());
    }
}

