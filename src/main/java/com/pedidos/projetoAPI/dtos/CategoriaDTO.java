package com.pedidos.projetoAPI.dtos;

import com.pedidos.projetoAPI.entities.Categoria;

public record CategoriaDTO(Long id, String nome) {

    public CategoriaDTO(Categoria entity) {
        this(entity.getId(), entity.getNome());
    }
}
