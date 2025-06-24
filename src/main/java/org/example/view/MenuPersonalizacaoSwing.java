package org.example.view;

import javax.swing.*;
import java.awt.*;

import org.example.controller.MenuPersonalizacaoProdutoController;

public class MenuPersonalizacaoSwing extends JFrame {

    private JButton cadastrarProdutoButton;
    private JButton atualizarProdutoButton;
    private JButton deletarButton;
    private JButton voltarButton;

    private MenuPersonalizacaoProdutoController controller;

    // definindo as cores
    private final Color corFundo = new Color( 224, 224, 224 );
    private final Color corPainelBotoes = new Color( 245, 245, 245 );
    private final Color corBotao = new Color( 250, 250, 250 );
    private final Color corTexto = Color.BLACK;
    private final Font fonte = new Font( "Arial", Font.BOLD, 16 );

    public MenuPersonalizacaoSwing(MenuPersonalizacaoProdutoController controller) {
        this.controller = controller;
        inicializarBotoes();

        JPanel painelPrincipal = new JPanel( new BorderLayout( 20, 20 ) );
        painelPrincipal.setBackground( corFundo );

        JPanel painelBotoes = new JPanel( new GridLayout( 4, 1, 10, 15 ) );
        painelBotoes.setBackground( corPainelBotoes );
        painelBotoes.setBorder( BorderFactory.createEmptyBorder( 50, 150, 50, 150 ) );

        painelBotoes.add( cadastrarProdutoButton );
        painelBotoes.add( atualizarProdutoButton );
        painelBotoes.add( deletarButton );
        painelBotoes.add( voltarButton );

        painelPrincipal.add( painelBotoes, BorderLayout.CENTER );

        setContentPane( painelPrincipal );
        setSize( 700, 500 );
        setTitle( "Menu de Personalização" );
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        setLocationRelativeTo( null );
        setVisible( true );

        cadastrarProdutoButton.addActionListener( e -> controller.abrirCadastro() );
        atualizarProdutoButton.addActionListener( e -> controller.abrirAtualizacao() );
        deletarButton.addActionListener( e -> controller.abrirRemocao() );
        voltarButton.addActionListener( e -> dispose() );
    }

    private void inicializarBotoes() {
        cadastrarProdutoButton = new JButton( "Cadastrar Produto" );
        cadastrarProdutoButton.setFont( fonte );
        cadastrarProdutoButton.setBackground( corBotao );
        cadastrarProdutoButton.setForeground( corTexto );
        cadastrarProdutoButton.setFocusPainted( false );
        cadastrarProdutoButton.setBorder( BorderFactory.createLineBorder( new Color( 180, 180, 180 ) ) );

        atualizarProdutoButton = new JButton( "Atualizar Produto" );
        atualizarProdutoButton.setFont( fonte );
        atualizarProdutoButton.setBackground( corBotao );
        atualizarProdutoButton.setForeground( corTexto );
        atualizarProdutoButton.setFocusPainted( false );
        atualizarProdutoButton.setBorder( BorderFactory.createLineBorder( new Color( 180, 180, 180 ) ) );

        deletarButton = new JButton( "Deletar Produto" );
        deletarButton.setFont( fonte );
        deletarButton.setBackground( corBotao );
        deletarButton.setForeground( corTexto );
        deletarButton.setFocusPainted( false );
        deletarButton.setBorder( BorderFactory.createLineBorder( new Color( 180, 180, 180 ) ) );

        voltarButton = new JButton( "Voltar" );
        voltarButton.setFont( fonte );
        voltarButton.setBackground( corBotao );
        voltarButton.setForeground( corTexto );
        voltarButton.setFocusPainted( false );
        voltarButton.setBorder( BorderFactory.createLineBorder( new Color( 180, 180, 180 ) ) );
    }
}