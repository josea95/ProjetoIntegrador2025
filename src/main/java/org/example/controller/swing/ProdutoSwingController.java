package org.example.controller.swing;

import org.example.model.services.ProdutoService;
import org.example.view.swing.AtualizarProdutoSwing;
import org.example.view.swing.CadastrarProdutoSwing;
import org.example.view.swing.DeletarProdutoSwing;
import org.example.view.swing.MenuPersonalizacaoSwing;

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
