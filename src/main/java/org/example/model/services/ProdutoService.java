package org.example.model.services;

import org.example.model.entities.ProdutoEntity;
import org.example.model.repository.ProdutoRepository;

import java.util.List;

public class ProdutoService{
    private final ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public void cadastrarProduto(ProdutoEntity produto) {
        produtoRepository.salvar( produto );
    }

    public void atualizarProduto(ProdutoEntity produto) {
       produtoRepository.atualizar( produto );
    }

    public void deletarProduto(Long id) {
        produtoRepository.deletar( id );
    }

    public List<ProdutoEntity> buscarProdutosPorCategoria(String categoria) {
        return produtoRepository.buscarPorCategoria( categoria );
    }
}
