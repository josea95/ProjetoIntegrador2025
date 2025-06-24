package org.example.controller;

import org.example.view.AtualizarProdutoSwing;
import org.example.view.CadastrarProdutoSwing;
import org.example.view.DeletarProdutoSwing;
import org.example.view.MenuPersonalizacaoSwing;

public class MenuPersonalizacaoProdutoController {

    private ProdutoController produtoController;

    public MenuPersonalizacaoProdutoController(ProdutoController produtoController) {
        this.produtoController = produtoController;
    }

    public void iniciar() {
        new MenuPersonalizacaoSwing( this );
    }

    public void abrirCadastro() {
        new CadastrarProdutoSwing( produtoController );
    }

    public void abrirAtualizacao() {
        new AtualizarProdutoSwing( produtoController );
    }

    public void abrirRemocao() {
        new DeletarProdutoSwing( produtoController );
    }
}