package org.example.controller;

import org.example.model.entities.ProdutoEntity;
import org.example.model.repository.ProdutoRepository;
import org.example.model.services.ProdutoService;
import org.example.model.util.CustomizerFactory;

import javax.persistence.EntityManager;
import java.util.List;

public class ProdutoController {

    private ProdutoService produtoService;
    private final EntityManager em = CustomizerFactory.getEntityManager();

    public ProdutoController() {
        this.produtoService = new ProdutoService( new ProdutoRepository( em ) );
    }

    public void cadastrarProduto(ProdutoEntity produto) {
        produtoService.cadastrarProduto( produto );
    }

    public void atualizarProduto(ProdutoEntity produto) {
        produtoService.atualizarProduto( produto );
    }

    public void deletarProduto(Long id) {
        produtoService.deletarProduto( id );
    }

    public List<ProdutoEntity> buscarPorCategoria(String categoria) {
        return produtoService.buscarProdutosPorCategoria( categoria );
    }
}