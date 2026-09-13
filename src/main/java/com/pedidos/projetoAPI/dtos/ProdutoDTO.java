package com.pedidos.projetoAPI.dtos;

import com.pedidos.projetoAPI.entities.Produto;

import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;

public record ProdutoDTO(
        Long id,
        String nome,
        String descricao,
        BigDecimal preco,
        Integer estoque,
        Set<CategoriaDTO> categorias
) {
    public ProdutoDTO(Produto entity) {
        this(
                entity.getId(),
                entity.getNome(),
                entity.getDescricao(),
                entity.getPreco(),
                entity.getEstoque(),
                entity.getCategorias() != null
                        ? entity.getCategorias().stream().map(CategoriaDTO::new).collect(Collectors.toSet())
                        : Set.of()
        );
    }
}
