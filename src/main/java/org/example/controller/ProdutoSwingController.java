package org.example.controller;

import org.example.model.services.ProdutoService;
import org.example.Swing.AtualizarProdutoSwing;
import org.example.Swing.CadastrarProdutoSwing;
import org.example.Swing.DeletarProdutoSwing;
import org.example.Swing.MenuPersonalizacaoSwing;

public class ProdutoSwingController {

    private ProdutoService produtoService;

    public ProdutoSwingController(ProdutoService produtoService) {
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
