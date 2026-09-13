package com.pedidos.projetoAPI.repositories;

import com.pedidos.projetoAPI.entities.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositório Spring Data JPA para a entidade Pagamento.
 */
@Repository
public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {
}
