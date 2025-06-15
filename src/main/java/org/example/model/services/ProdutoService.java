package org.example.model.services;

import org.example.model.entities.ProdutoEntity;
import org.example.model.repository.ProdutoRepository;

import java.time.LocalDate;
import java.util.List;

public class ProdutoService {
    private final ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public void cadastrarProduto(ProdutoEntity produto) {
        if (produto.getNome() == null || produto.getNome().isBlank()) {
            throw new IllegalArgumentException( "O nome não pode ser vazio." );
        }
        if (produto.getPreco() == null || produto.getPreco() <= 0) {
            throw new IllegalArgumentException( "O preço deve ser maior que zero." );
        }
        if (produto.getDescricao() == null || produto.getDescricao().isBlank()) {
            throw new IllegalArgumentException( "A descrição não pode ser vazia." );
        }
        if (produto.getCategoria() == null || produto.getCategoria().isBlank()) {
            throw new IllegalArgumentException( "A categoria não pode ser vazia." );
        }

        produto.setDataCriacao( LocalDate.now() );
        produtoRepository.salvar( produto );
    }

    public void atualizarProduto(ProdutoEntity produto) {
        if (produto.getNome() == null || produto.getNome().isBlank()) {
            throw new IllegalArgumentException( "O nome do produto não pode ser vazio." );
        }
        if (produto.getPreco() <= 0) {
            throw new IllegalArgumentException( "O preço deve ser maior que zero." );
        }
        ProdutoEntity existente = produtoRepository.buscarPorId( produto.getId() );
        if (existente == null) {
            throw new IllegalArgumentException( "Produto não encontrado para atualização." );
        }
        produto.setDataAtualizacao( LocalDate.now() );
        produtoRepository.atualizar( produto );
    }

    public void deletarProduto(Long id) {
        ProdutoEntity produto = produtoRepository.buscarPorId( id );
        if (produto == null) {
            throw new IllegalArgumentException( "Produto não encontrado para deletar." );
        }
        produtoRepository.deletar( id );
    }

    // Buscar produtos por categoria
    public List<ProdutoEntity> buscarProdutosPorCategoria(String categoria) {
        if (categoria == null || categoria.trim().isEmpty()) {
            throw new IllegalArgumentException( "Categoria não pode ser vazia." );
        }
        return produtoRepository.buscarPorCategoria( categoria );
    }
}
