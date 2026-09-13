package com.pedidos.projetoAPI.controllers;

import com.pedidos.projetoAPI.dtos.PagamentoDTO;
import com.pedidos.projetoAPI.services.PagamentoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pagamentos")
public class PagamentoController {

    private final PagamentoService service;

    public PagamentoController(PagamentoService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<PagamentoDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping("/pedido/{pedidoId}")
    public ResponseEntity<PagamentoDTO> efetuarPagamento(@PathVariable Long pedidoId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.efetuarPagamento(pedidoId));
    }
}
