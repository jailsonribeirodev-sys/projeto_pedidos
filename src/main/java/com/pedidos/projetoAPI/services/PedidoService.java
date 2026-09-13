package com.pedidos.projetoAPI.services;

import com.pedidos.projetoAPI.dtos.ItemPedidoCreateDTO;
import com.pedidos.projetoAPI.dtos.PedidoCreateDTO;
import com.pedidos.projetoAPI.dtos.PedidoDTO;
import com.pedidos.projetoAPI.entities.Cliente;
import com.pedidos.projetoAPI.entities.ItemPedido;
import com.pedidos.projetoAPI.entities.Pedido;
import com.pedidos.projetoAPI.entities.Produto;
import com.pedidos.projetoAPI.entities.enums.StatusPedido;
import com.pedidos.projetoAPI.repositories.ClienteRepository;
import com.pedidos.projetoAPI.repositories.ItemPedidoRepository;
import com.pedidos.projetoAPI.repositories.PedidoRepository;
import com.pedidos.projetoAPI.repositories.ProdutoRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
public class PedidoService {

    private final PedidoRepository repository;
    private final ClienteRepository clienteRepository;
    private final ProdutoRepository produtoRepository;
    private final ItemPedidoRepository itemPedidoRepository;

    public PedidoService(PedidoRepository repository,
            ClienteRepository clienteRepository,
            ProdutoRepository produtoRepository,
            ItemPedidoRepository itemPedidoRepository) {
        this.repository = repository;
        this.clienteRepository = clienteRepository;
        this.produtoRepository = produtoRepository;
        this.itemPedidoRepository = itemPedidoRepository;
    }

    public List<PedidoDTO> findAll() {
        return repository.findAll()
                .stream()
                .map(PedidoDTO::new)
                .toList();
    }

    public PedidoDTO findById(Long id) {
        Pedido entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado id: " + id));
        return new PedidoDTO(entity);
    }

    /**
     * MÉTODO DE CRIAÇÃO DO PEDIDO (O Coração da Loja)
     * Transforma o bilhete enviado pelo cliente (DTO) em registros reais no banco de dados (Entidades).
     */
    @Transactional // Garante o "Tudo ou Nada": se qualquer produto der erro, desfaz tudo no banco (Rollback).
    public PedidoDTO insert(PedidoCreateDTO dto) {
        // PASSO 1: Buscar o cliente no banco pelo ID que veio no JSON
        // Se o cliente com esse ID não existir no PostgreSQL, lança erro e interrompe a requisição na hora.
        Cliente cliente = clienteRepository.findById(dto.clienteId())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado id: " + dto.clienteId()));

        // PASSO 2: Criar a folha em branco do Pedido na memória RAM
        Pedido entity = new Pedido();
        
        // Carimba a data e hora exata de agora no fuso UTC
        entity.setInstante(Instant.now());
        
        // Define o status inicial do pedido: recém-criado, ainda aguardando o pagamento ser feito
        entity.setStatus(StatusPedido.AGUARDANDO_PAGAMENTO);
        
        // Amarra o cliente encontrado neste pedido (o "dono" do pedido)
        entity.setCliente(cliente);

        // PASSO 3: Processar a lista de produtos que o cliente colocou no carrinho
        if (dto.itens() != null) {
            // Percorre cada item (cada produto e quantidade) que veio no JSON de compra
            for (ItemPedidoCreateDTO itemDto : dto.itens()) {
                // Vai ao banco de dados e busca o produto real para garantir que existe e pegar o preço oficial
                Produto produto = produtoRepository.findById(itemDto.produtoId())
                        .orElseThrow(() -> new RuntimeException("Produto não encontrado id: " + itemDto.produtoId()));
                
                // Cria o Item do Pedido amarrando:
                // 1. entity: o pedido pai ao qual este item pertence
                // 2. produto: o produto cadastrado no estoque
                // 3. itemDto.quantidade(): a quantidade que o cliente escolheu comprar
                // 4. produto.getPreco(): O PREÇO ATUAL DO BANCO (congela o preço histórico e evita que o cliente fraude o valor)
                ItemPedido item = new ItemPedido(entity, produto, itemDto.quantidade(), produto.getPreco());

                // Adiciona este item dentro do conjunto (Set) de itens da folha do pedido
                entity.getItens().add(item);
            }
        }

        // PASSO 4: Salvar a folha do pedido no PostgreSQL
        // Graças ao CascadeType.ALL configurado na entidade Pedido, este save() grava
        // tanto o Pedido (tb_pedido) quanto todos os seus itens (tb_item_pedido) de uma só vez!
        entity = repository.save(entity);

        // PASSO 5: Converter a Entidade que agora está salva no banco para o DTO de Saída (PedidoDTO)
        // Isso devolve um comprovante limpo em formato JSON para o usuário, com o total calculado e sem loops.
        return new PedidoDTO(entity);
    }

    public PedidoDTO updateStatus(Long id, StatusPedido status) {
        Pedido entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado id: " + id));
        entity.setStatus(status);
        entity = repository.save(entity);
        return new PedidoDTO(entity);
    }
}
