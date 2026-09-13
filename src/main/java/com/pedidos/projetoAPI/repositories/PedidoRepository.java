package com.pedidos.projetoAPI.repositories;

import com.pedidos.projetoAPI.entities.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositório Spring Data JPA para a entidade Pedido.
 */
@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
}
