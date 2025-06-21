package org.example.Swing;

import org.example.controller.ProdutoController;
import org.example.model.entities.ProdutoEntity;
import org.example.model.services.ProdutoService;

import javax.swing.*;
import java.awt.*;

public class CadastrarProdutoSwing extends JFrame {

    private JTextField nomeField;
    private JComboBox<String> categoriaCombo;
    private JTextField precoField;
    private JTextField descricaoField;
    private JButton cadastrarButton;
    private JButton voltarButton;

    private ProdutoController produtoController;
    private ProdutoService produtoService;

    private final Font fonte = new Font( "Arial", Font.BOLD, 14 );
    private final Color fundo = new Color( 220, 220, 220 );

    public CadastrarProdutoSwing(ProdutoController produtoController) {
        this.produtoController = produtoController;
        setTitle( "Cadastro de Produto" );
        setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
        setSize( 700, 500 );
        setLocationRelativeTo( null );
        initComponents();
        setVisible( true );
    }

    private void initComponents() {
        JPanel contentPanel = new JPanel( new BorderLayout() );
        contentPanel.setBackground( fundo );

        JPanel formPanel = new JPanel( new GridLayout( 4, 2, 10, 10 ) );
        formPanel.setBackground( fundo );

        JLabel labelCategoria = new JLabel( "Categoria:" );
        labelCategoria.setFont( fonte );
        labelCategoria.setBackground( fundo );
        formPanel.add( labelCategoria );

        String[] categorias = {"Marmitas", "Bebidas", "Porções"};
        categoriaCombo = new JComboBox<>( categorias );
        categoriaCombo.setFont( fonte );
        categoriaCombo.setBackground( fundo );
        formPanel.add( categoriaCombo );

        JLabel labelNome = new JLabel( "Nome:" );
        labelNome.setFont( fonte );
        labelNome.setBackground( fundo );
        formPanel.add( labelNome );

        nomeField = new JTextField( 20 );
        nomeField.setFont( fonte );
        formPanel.add( nomeField );

        JLabel labelPreco = new JLabel( "Preço:" );
        labelPreco.setFont( fonte );
        labelPreco.setBackground( fundo );
        formPanel.add( labelPreco );

        precoField = new JTextField( 20 );
        precoField.setFont( fonte );
        formPanel.add( precoField );

        JLabel labelDescricao = new JLabel( "Descrição:" );
        labelDescricao.setFont( fonte );
        labelDescricao.setBackground( fundo );
        formPanel.add( labelDescricao );

        descricaoField = new JTextField( 20 );
        descricaoField.setFont( fonte );
        formPanel.add( descricaoField );

        contentPanel.add( formPanel, BorderLayout.CENTER );

        // Painel inferior com botões
        JPanel buttonPanel = new JPanel( new FlowLayout( FlowLayout.CENTER, 10, 10 ) );
        buttonPanel.setBackground( fundo );

        cadastrarButton = new JButton( "Cadastrar" );
        cadastrarButton.setFont( fonte );
        cadastrarButton.setBackground( fundo );
        cadastrarButton.addActionListener( e -> cadastrarProduto() );
        buttonPanel.add( cadastrarButton );

        voltarButton = new JButton( "Voltar" );
        voltarButton.setFont( fonte );
        voltarButton.setBackground( fundo );
        voltarButton.addActionListener( e -> dispose() );
        buttonPanel.add( voltarButton );

        contentPanel.add( buttonPanel, BorderLayout.SOUTH );
        setContentPane( contentPanel );
    }

    private void cadastrarProduto() {
        try {
            String nome = nomeField.getText().trim();
            String categoria = (String) categoriaCombo.getSelectedItem();
            String descricao = descricaoField.getText().trim();
            double preco = Double.parseDouble( precoField.getText().trim() );

            ProdutoEntity produto = new ProdutoEntity();
            produto.setNome( nome );
            produto.setCategoria( categoria );
            produto.setDescricao( descricao );
            produto.setPreco( preco );

            produtoController.cadastrarProduto( produto );
            JOptionPane.showMessageDialog( this, "Produto cadastrado com sucesso!" );
            limparCampos();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog( this, "Preço inválido. Informe um número válido." );
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog( this, e.getMessage() );
        } catch (Exception e) {
            JOptionPane.showMessageDialog( this, "Erro inesperado: " + e.getMessage() );
        }
    }

    private void limparCampos() {
        nomeField.setText( "" );
        precoField.setText( "" );
        descricaoField.setText( "" );
    }
}