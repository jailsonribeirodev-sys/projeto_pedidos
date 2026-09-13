package com.pedidos.projetoAPI.repositories;

import com.pedidos.projetoAPI.entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositório Spring Data JPA para a entidade Cliente.
 */
@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    
    /**
     * Consulta customizada gerada automaticamente pelo Spring Data JPA para buscar cliente pelo e-mail.
     */
    Optional<Cliente> findByEmail(String email);
}
