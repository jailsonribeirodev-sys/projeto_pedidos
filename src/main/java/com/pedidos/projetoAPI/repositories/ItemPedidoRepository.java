package com.pedidos.projetoAPI.repositories;

import com.pedidos.projetoAPI.entities.ItemPedido;
import com.pedidos.projetoAPI.entities.pk.ItemPedidoPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositório Spring Data JPA para a entidade ItemPedido.
 * Utiliza ItemPedidoPK como tipo do identificador composto.
 */
@Repository
public interface ItemPedidoRepository extends JpaRepository<ItemPedido, ItemPedidoPK> {
}
