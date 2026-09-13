package com.pedidos.projetoAPI.services;

import com.pedidos.projetoAPI.dtos.CategoriaDTO;
import com.pedidos.projetoAPI.dtos.ProdutoDTO;
import com.pedidos.projetoAPI.entities.Categoria;
import com.pedidos.projetoAPI.entities.Produto;
import com.pedidos.projetoAPI.repositories.CategoriaRepository;
import com.pedidos.projetoAPI.repositories.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService {

    private final ProdutoRepository repository;
    private final CategoriaRepository categoriaRepository;

    public ProdutoService(ProdutoRepository repository, CategoriaRepository categoriaRepository) {
        this.repository = repository;
        this.categoriaRepository = categoriaRepository;
    }

    public List<ProdutoDTO> findAll() {
        return repository.findAll()
                .stream()
                .map(ProdutoDTO::new)
                .toList();
    }

    public ProdutoDTO findById(Long id) {
        Produto entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado id: " + id));
        return new ProdutoDTO(entity);
    }

    public ProdutoDTO insert(ProdutoDTO dto) {
        Produto entity = new Produto();
        copyDtoToEntity(dto, entity);
        entity = repository.save(entity);
        return new ProdutoDTO(entity);
    }

    public ProdutoDTO update(Long id, ProdutoDTO dto) {
        Produto entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado id: " + id));
        copyDtoToEntity(dto, entity);
        entity = repository.save(entity);
        return new ProdutoDTO(entity);
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Produto não encontrado id: " + id);
        }
        repository.deleteById(id);
    }

    private void copyDtoToEntity(ProdutoDTO dto, Produto entity) {
        entity.setNome(dto.nome());
        entity.setDescricao(dto.descricao());
        entity.setPreco(dto.preco());
        entity.setEstoque(dto.estoque());

        entity.getCategorias().clear();
        if (dto.categorias() != null) {
            for (CategoriaDTO catDto : dto.categorias()) {
                Categoria cat = categoriaRepository.findById(catDto.id())
                        .orElseThrow(() -> new RuntimeException("Categoria não encontrada id: " + catDto.id()));
                entity.getCategorias().add(cat);
            }
        }
    }
}
