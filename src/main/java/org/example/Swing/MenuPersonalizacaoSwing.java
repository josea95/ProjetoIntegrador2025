package org.example.Swing;

import javax.swing.*;
import java.awt.*;
import javax.persistence.EntityManager;

import org.example.model.util.CustomizerFactory;
import org.example.model.services.PedidoService;
import org.example.model.entities.UsuarioEntity;
import org.example.controller.ProdutoSwingController;

public class MenuPersonalizacaoSwing extends JFrame {

    private JButton cadastrarProdutoButton;
    private JButton atualizarProdutoButton;
    private JButton deletarButton;
    private JButton voltarButton;

    private ProdutoSwingController controller;

    public MenuPersonalizacaoSwing(ProdutoSwingController controller) {
        this.controller = controller;

        inicializarBotoes();

        // Painel principal com BorderLayout
        JPanel painelPrincipal = new JPanel( new BorderLayout( 20, 20 ) );

        JLabel titulo = new JLabel( "Menu de Personalização", SwingConstants.CENTER );
        painelPrincipal.add( titulo, BorderLayout.NORTH );

        JPanel painelBotoes = new JPanel( new GridLayout( 4, 1, 10, 10 ) );
        painelBotoes.add( cadastrarProdutoButton );
        painelBotoes.add( atualizarProdutoButton );
        painelBotoes.add( deletarButton );
        painelBotoes.add( voltarButton );

        painelPrincipal.add( painelBotoes, BorderLayout.CENTER );

        // Configurações da janela
        setContentPane( painelPrincipal );
        setSize( 700, 500 );
        setTitle( "Menu de Personalização" );
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        setLocationRelativeTo( null );
        setVisible( true );

        // Ações dos botões
        cadastrarProdutoButton.addActionListener( e -> controller.abrirCadastro() );
        atualizarProdutoButton.addActionListener( e -> controller.abrirAtualizacao() );
        deletarButton.addActionListener( e -> controller.abrirRemocao() );
        voltarButton.addActionListener( e -> {
            dispose();
            EntityManager em = CustomizerFactory.getEntityManager();
            PedidoService pedidoService = new PedidoService( em );
            UsuarioEntity usuarioLogado = new UsuarioEntity();
            //new MenuPrincipalSwing( pedidoService, usuarioLogado );
        } );
    }

    private void inicializarBotoes() {
        cadastrarProdutoButton = new JButton( "Cadastrar produto" );
        atualizarProdutoButton = new JButton( "Atualizar produto" );
        deletarButton = new JButton( "Deletar produto" );
        voltarButton = new JButton( "Voltar" );
    }
}