package com.pedidos.projetoAPI.services;

import com.pedidos.projetoAPI.dtos.PagamentoDTO;
import com.pedidos.projetoAPI.entities.Pagamento;
import com.pedidos.projetoAPI.entities.Pedido;
import com.pedidos.projetoAPI.entities.enums.StatusPagamento;
import com.pedidos.projetoAPI.entities.enums.StatusPedido;
import com.pedidos.projetoAPI.repositories.PagamentoRepository;
import com.pedidos.projetoAPI.repositories.PedidoRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class PagamentoService {

    private final PagamentoRepository repository;
    private final PedidoRepository pedidoRepository;

    public PagamentoService(PagamentoRepository repository, PedidoRepository pedidoRepository) {
        this.repository = repository;
        this.pedidoRepository = pedidoRepository;
    }

    public PagamentoDTO findById(Long id) {
        Pagamento entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pagamento não encontrado id: " + id));
        return new PagamentoDTO(entity);
    }

    public PagamentoDTO efetuarPagamento(Long pedidoId) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado id: " + pedidoId));

        Pagamento pagamento = new Pagamento(null, Instant.now(), StatusPagamento.QUITADO, pedido);
        pedido.setPagamento(pagamento);
        pedido.setStatus(StatusPedido.PAGO);

        pagamento = repository.save(pagamento);
        pedidoRepository.save(pedido);

        return new PagamentoDTO(pagamento);
    }
}
