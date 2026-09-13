package com.pedidos.projetoAPI.controllers;

import com.pedidos.projetoAPI.dtos.PedidoCreateDTO;
import com.pedidos.projetoAPI.dtos.PedidoDTO;
import com.pedidos.projetoAPI.entities.enums.StatusPedido;
import com.pedidos.projetoAPI.services.PedidoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private final PedidoService service;

    public PedidoController(PedidoService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<PedidoDTO>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<PedidoDTO> insert(@RequestBody PedidoCreateDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.insert(dto));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<PedidoDTO> updateStatus(@PathVariable Long id, @RequestParam StatusPedido status) {
        return ResponseEntity.ok(service.updateStatus(id, status));
    }
}
