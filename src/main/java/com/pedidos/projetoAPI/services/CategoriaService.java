package com.pedidos.projetoAPI.services;

import com.pedidos.projetoAPI.dtos.CategoriaDTO;
import com.pedidos.projetoAPI.entities.Categoria;
import com.pedidos.projetoAPI.repositories.CategoriaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService {

    private final CategoriaRepository repository;

    public CategoriaService(CategoriaRepository repository) {
        this.repository = repository;
    }

    public List<CategoriaDTO> findAll() {
        return repository.findAll()
                .stream()
                .map(CategoriaDTO::new)
                .toList();
    }

    public CategoriaDTO findById(Long id) {
        Categoria entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada id: " + id));
        return new CategoriaDTO(entity);
    }

    public CategoriaDTO insert(CategoriaDTO dto) {
        Categoria entity = new Categoria();
        entity.setNome(dto.nome());
        entity = repository.save(entity);
        return new CategoriaDTO(entity);
    }

    public CategoriaDTO update(Long id, CategoriaDTO dto) {
        Categoria entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada id: " + id));
        entity.setNome(dto.nome());
        entity = repository.save(entity);
        return new CategoriaDTO(entity);
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Categoria não encontrada id: " + id);
        }
        repository.deleteById(id);
    }
}
