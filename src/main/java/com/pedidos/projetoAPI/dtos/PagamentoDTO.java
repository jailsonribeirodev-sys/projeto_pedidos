package com.pedidos.projetoAPI.dtos;

import com.pedidos.projetoAPI.entities.Pagamento;
import com.pedidos.projetoAPI.entities.enums.StatusPagamento;

import java.time.Instant;

public record PagamentoDTO(
        Long id,
        Instant instante,
        StatusPagamento status
) {
    public PagamentoDTO(Pagamento entity) {
        this(
                entity.getId(),
                entity.getInstante(),
                entity.getStatus()
        );
    }
}
