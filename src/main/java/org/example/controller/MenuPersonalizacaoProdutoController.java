package org.example.controller;

import org.example.Swing.AtualizarProdutoSwing;
import org.example.Swing.CadastrarProdutoSwing;
import org.example.Swing.DeletarProdutoSwing;
import org.example.Swing.MenuPersonalizacaoSwing;

//Alterado nome do Controller para MenuPersonalizacaoProdutoController
public class MenuPersonalizacaoProdutoController {

    private ProdutoController produtoController;

    //Chamando pela controller ao invés da service
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
