package org.example.view.swing;

import org.example.controller.swing.ProdutoSwingController;

import javax.swing.*;
import java.awt.*;

public class MenuPersonalizacaoSwing extends JFrame {

    private JButton cadastrarProdutoButton;
    private JButton atualizarProdutoButton;
    private JButton deletarButton;
    private JButton sairButton;

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
        painelBotoes.add( sairButton );

        painelPrincipal.add( painelBotoes, BorderLayout.CENTER );

        // Configurações da janela
        setContentPane( painelPrincipal );
        setSize( 400, 300 );
        setTitle( "Menu de Personalização" );
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        setLocationRelativeTo( null ); // Centraliza na tela
        setVisible( true );

        // Ações dos botões
        cadastrarProdutoButton.addActionListener( e -> controller.abrirCadastro() );
        atualizarProdutoButton.addActionListener( e -> controller.abrirAtualizacao() );
        deletarButton.addActionListener( e -> controller.abrirRemocao() );
        sairButton.addActionListener( e -> System.exit( 0 ) );
    }

    private void inicializarBotoes() {

        cadastrarProdutoButton = new JButton( "Cadastrar produto" );
        atualizarProdutoButton = new JButton( "Atualizar produto" );
        deletarButton = new JButton( "Deletar produto" );
        sairButton = new JButton( "Sair" );

    }

}
