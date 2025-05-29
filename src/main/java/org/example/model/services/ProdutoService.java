//package org.example.model.services;
//
//import org.example.model.entities.ProdutoEntity;
//import org.example.model.repository.ProdutoRepository;
//
//import java.time.LocalDate;
//import java.util.List;
//
//public class ProdutoService {
//    private final ProdutoRepository produtoRepository;
//
//    public ProdutoService(ProdutoRepository produtoRepository) {
//        this.produtoRepository = produtoRepository;
//    }
//
//    public void cadastrarProduto(ProdutoEntity produto) {
//        if (!validarDataCriacao(produto.getDataCriacao())) {
//            throw new IllegalArgumentException("Data de criação inválida.");
//        }
//        produtoRepository.salvar(produto);
//    }
//
//    public boolean validarDataCriacao(LocalDate dataCriacao) {
//        return dataCriacao != null;
//    }
//
//    // Consulta os produtos por categoria sem exibição
//    public List<ProdutoEntity> buscarProdutosPorCategoria(String categoria) {
//        return produtoRepository.buscarPorCategoria(categoria);
//    }
//
//    public ProdutoRepository getProdutoRepository() {
//        return produtoRepository;
//    }
//}

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
        if (!validarDataCriacao(produto.getDataCriacao())) {
            throw new IllegalArgumentException("Data de criação inválida.");
        }
        produtoRepository.salvar(produto);
    }

    public void atualizarProduto(ProdutoEntity produto) {
        if (!validarDataCriacao(produto.getDataCriacao())) {
            throw new IllegalArgumentException("Data de criação inválida.");
        }
        produtoRepository.atualizar(produto);
    }

    public void deletarProduto(Long id) {
        produtoRepository.deletar(id);
    }

    public boolean validarDataCriacao(LocalDate dataCriacao) {
        return dataCriacao != null;
    }

    public List<ProdutoEntity> buscarProdutosPorCategoria(String categoria) {
        return produtoRepository.buscarPorCategoria(categoria);
    }

    public ProdutoRepository getProdutoRepository() {
        return produtoRepository;
    }
}