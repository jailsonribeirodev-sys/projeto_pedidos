package com.pedidos.projetoAPI.repositories;

import com.pedidos.projetoAPI.entities.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositório Spring Data JPA para a entidade Categoria.
 * Herda métodos prontos como findAll(), findById(), save() e deleteById().
 */
@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
}
