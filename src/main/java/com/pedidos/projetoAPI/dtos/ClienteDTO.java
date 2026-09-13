package com.pedidos.projetoAPI.dtos;

import com.pedidos.projetoAPI.entities.Cliente;

public record ClienteDTO(
        Long id,
        String nome,
        String email,
        String cpfOuCnpj,
        String telefone
) {
    public ClienteDTO(Cliente entity) {
        this(
                entity.getId(),
                entity.getNome(),
                entity.getEmail(),
                entity.getCpfOuCnpj(),
                entity.getTelefone()
        );
    }
}
