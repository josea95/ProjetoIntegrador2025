package org.example.controller;

import org.example.model.services.ProdutoService;
import org.example.Swing.AtualizarProdutoSwing;
import org.example.Swing.CadastrarProdutoSwing;
import org.example.Swing.DeletarProdutoSwing;
import org.example.Swing.MenuPersonalizacaoSwing;

//Alterado nome do Controller para MenuPersonalizacaoProdutoController
public class MenuPersonalizacaoProdutoController {

    private ProdutoService produtoService;

    public MenuPersonalizacaoProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    public void iniciar() {
        new MenuPersonalizacaoSwing( this );
    }


    public void abrirCadastro() {
        new CadastrarProdutoSwing( produtoService );
    }

    public void abrirAtualizacao() {
        new AtualizarProdutoSwing( produtoService );
    }

    public void abrirRemocao() {
        new DeletarProdutoSwing( produtoService );
    }
}
