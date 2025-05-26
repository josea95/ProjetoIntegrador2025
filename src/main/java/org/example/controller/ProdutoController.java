package org.example.controller;

import org.example.model.services.ProdutoService;
import org.example.view.ProdutoView;

public class ProdutoController {
    private final ProdutoService produtoService;
    private final ProdutoView produtoView;

    public ProdutoController(ProdutoService produtoService, ProdutoView produtoView) {
        this.produtoService = produtoService;
        this.produtoView = produtoView;
    }
    
    public void iniciarCadastro() {
        produtoView.cadastro();
    }
}
