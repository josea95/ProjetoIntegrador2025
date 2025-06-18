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
        JPanel panel = new JPanel( new GridLayout( 5, 2, 10, 10 ) );

        panel.add( new JLabel( "Categoria:" ) );
        String[] categorias = {"Marmitas", "Bebidas", "Porções"};
        categoriaCombo = new JComboBox<>( categorias );
        panel.add( categoriaCombo );

        panel.add( new JLabel( "Nome:" ) );
        nomeField = new JTextField();
        panel.add( nomeField );

        panel.add( new JLabel( "Preço:" ) );
        precoField = new JTextField();
        panel.add( precoField );

        panel.add( new JLabel( "Descrição: " ) );
        descricaoField = new JTextField();
        panel.add( descricaoField );

        cadastrarButton = new JButton( "Cadastrar" );
        cadastrarButton.addActionListener( e -> cadastrarProduto() );
        panel.add( cadastrarButton );

        voltarButton = new JButton( "Voltar" );
        voltarButton.addActionListener( e -> dispose() );
        panel.add( voltarButton );

        setContentPane( panel );
    }

    private void cadastrarProduto() {
        try {
            String nome = nomeField.getText().trim();
            String categoria = (String) categoriaCombo.getSelectedItem();
            String descricao = descricaoField.getText().trim();
            String precoText = precoField.getText().trim();

            double preco = Double.parseDouble( precoText );

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