package org.example.view.swing;

import org.example.controller.swing.ProdutoSwingController;
import org.example.model.repository.ProdutoRepository;
import org.example.model.services.ProdutoService;
import org.example.model.util.CustomizerFactory;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        ProdutoService produtoService = new ProdutoService(
                new ProdutoRepository( CustomizerFactory.getEntityManager() )
        );

        ProdutoSwingController controller = new ProdutoSwingController( produtoService );

        SwingUtilities.invokeLater( () -> new MenuPersonalizacaoSwing( controller ) );
    }

}

