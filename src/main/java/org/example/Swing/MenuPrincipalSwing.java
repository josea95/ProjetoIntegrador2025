package org.example.Swing;

import org.example.controller.ProdutoSwingController;
import org.example.model.entities.UsuarioEntity;
import org.example.model.repository.ProdutoRepository;
import org.example.model.services.PedidoService;
import org.example.model.services.ProdutoService;
import org.example.model.util.CustomizerFactory;

import javax.persistence.EntityManager;
import javax.swing.*;
import java.awt.*;

public class MenuPrincipalSwing {


    private final PedidoService pedidoService;
    private final UsuarioEntity usuarioLogado;

    public MenuPrincipalSwing(PedidoService pedidoService, UsuarioEntity usuarioLogado) {
        this.pedidoService = pedidoService;
        this.usuarioLogado = usuarioLogado;
        teste();
    }

    private void teste() {
        JFrame frmMenuPrincipal = new JFrame();
        frmMenuPrincipal.setBackground( new Color( 255, 255, 255 ) );
        frmMenuPrincipal.setTitle( "Menu Principal" );
        frmMenuPrincipal.getContentPane().setLayout( new GridLayout( 4, 2, 3, 3 ) );
        frmMenuPrincipal.setSize( 700, 500 );
        frmMenuPrincipal.setLocationRelativeTo( null );

        EntityManager em = CustomizerFactory.getEntityManager();
        ProdutoRepository produtoRepository = new ProdutoRepository( em );
        ProdutoService produtoService = new ProdutoService( produtoRepository );
        ProdutoSwingController produtoController = new ProdutoSwingController( produtoService );

        JButton fazerPedidoButton = new JButton( "1. Fazer Pedido" );
        fazerPedidoButton.setFont( new Font( "Verdana", Font.PLAIN, 12 ) );
        fazerPedidoButton.addActionListener( e -> new FazerPedidoSwing( pedidoService, usuarioLogado ) );
        frmMenuPrincipal.getContentPane().add( fazerPedidoButton );

        JButton btnNewButton_2 = new JButton( "2. Cancelar Pedido" );
        frmMenuPrincipal.getContentPane().add( btnNewButton_2 );

        JButton btnNewButton_3 = new JButton( "3. Ver Fila de Pedidos" );
        frmMenuPrincipal.getContentPane().add( btnNewButton_3 );

        JButton personalizacaoProdutoButton = new JButton( "4. Personalização de Produtos" );
        personalizacaoProdutoButton.setFont( new Font( "Verdana", Font.PLAIN, 12 ) );
        personalizacaoProdutoButton.addActionListener( e -> produtoController.iniciar() );
        frmMenuPrincipal.getContentPane().add( personalizacaoProdutoButton );

        JButton btnNewButton_5 = new JButton( "5. Pesquisar Pedido" );
        frmMenuPrincipal.getContentPane().add( btnNewButton_5 );

        JButton btnNewButton_6 = new JButton( "6. Ver Histórico de Pedidos" );
        frmMenuPrincipal.getContentPane().add( btnNewButton_6 );

        JButton btnNewButton_7 = new JButton( "7. Relatório de Vendas" );
        frmMenuPrincipal.getContentPane().add( btnNewButton_7 );

        JButton btnNewButton_8 = new JButton( "8. Sair" );
        btnNewButton_8.addActionListener( e -> System.exit( 0 ) );
        frmMenuPrincipal.getContentPane().add( btnNewButton_8 );

        frmMenuPrincipal.setVisible( true );
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater( () -> {
            EntityManager em = CustomizerFactory.getEntityManager();
            PedidoService pedidoService = new PedidoService( em );
            UsuarioEntity usuarioLogado = new UsuarioEntity(); //simulando para nao dar erro na hora de "finalizar" o pedido
            new MenuPrincipalSwing( pedidoService, usuarioLogado );
        } );
    }
}
