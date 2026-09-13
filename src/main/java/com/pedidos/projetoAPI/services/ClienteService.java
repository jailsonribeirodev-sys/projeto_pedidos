package com.pedidos.projetoAPI.services;

import com.pedidos.projetoAPI.dtos.ClienteDTO;
import com.pedidos.projetoAPI.entities.Cliente;
import com.pedidos.projetoAPI.repositories.ClienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    private final ClienteRepository repository;

    public ClienteService(ClienteRepository repository) {
        this.repository = repository;
    }

    public List<ClienteDTO> findAll() {
        return repository.findAll()
                .stream()
                .map(ClienteDTO::new)
                .toList();
    }

    public ClienteDTO findById(Long id) {
        Cliente entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado id: " + id));
        return new ClienteDTO(entity);
    }

    public ClienteDTO insert(ClienteDTO dto) {
        Cliente entity = new Cliente();
        entity.setNome(dto.nome());
        entity.setEmail(dto.email());
        entity.setCpfOuCnpj(dto.cpfOuCnpj());
        entity.setTelefone(dto.telefone());
        entity = repository.save(entity);
        return new ClienteDTO(entity);
    }

    public ClienteDTO update(Long id, ClienteDTO dto) {
        Cliente entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado id: " + id));
        entity.setNome(dto.nome());
        entity.setEmail(dto.email());
        entity.setCpfOuCnpj(dto.cpfOuCnpj());
        entity.setTelefone(dto.telefone());
        entity = repository.save(entity);
        return new ClienteDTO(entity);
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Cliente não encontrado id: " + id);
        }
        repository.deleteById(id);
    }
}
