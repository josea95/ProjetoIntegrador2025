package org.example.view.swing;

import org.example.model.entities.ProdutoEntity;
import org.example.model.services.ProdutoService;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class CadastrarProdutoSwing extends JFrame {

    private JTextField nomeField;
    private JComboBox<String> categoriaCombo;
    private JTextField precoField;
    private JTextField dataCriacaoField;
    private JButton cadastrarButton;
    private JButton voltarButton;
    private ProdutoService produtoService;

    public CadastrarProdutoSwing(ProdutoService produtoService) {
        this.produtoService = produtoService;
        setTitle( "Cadastro de Produto" );
        setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
        setSize( 500, 400 );
        setLocationRelativeTo( null );
        initComponents();
        setVisible( true );
    }

    private void initComponents() {
        JPanel panel = new JPanel( new GridLayout( 5, 2, 10, 10 ) );

        panel.add( new JLabel( "Nome:" ) );
        nomeField = new JTextField();
        panel.add( nomeField );

        panel.add( new JLabel( "Categoria:" ) );
        String[] categorias = {"Marmitas", "Bebidas", "Porções"};
        categoriaCombo = new JComboBox<>( categorias );
        panel.add( categoriaCombo );

        panel.add( new JLabel( "Preço:" ) );
        precoField = new JTextField();
        panel.add( precoField );

        panel.add( new JLabel( "Data (yyyy-MM-dd):" ) );
        dataCriacaoField = new JTextField();
        panel.add( dataCriacaoField );

        cadastrarButton = new JButton( "Cadastrar" );
        cadastrarButton.addActionListener( e -> cadastrarProduto() );
        panel.add( cadastrarButton );

        voltarButton = new JButton( "Voltar" );
        voltarButton.addActionListener( e -> dispose() );
        panel.add( voltarButton );

        setContentPane( panel );
    }

    private void cadastrarProduto() {
        String nome = nomeField.getText().trim();
        String categoria = (String) categoriaCombo.getSelectedItem();
        double preco;
        LocalDate dataCriacao;

        try{
            if (nome.isEmpty()) {
                JOptionPane.showMessageDialog( this, "Nome não pode ser vazio." );
                return;
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog( this, "Erro ao validar nome: " + ex.getMessage() );
            return;
        }

        try {
            preco = Double.parseDouble( precoField.getText().trim() );
            if (preco <= 0) {
                JOptionPane.showMessageDialog( this, "Preço deve ser maior que zero." );
                return;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog( this, "Preço inválido. Informe um número." );
            return;
        }

        try {
            dataCriacao = LocalDate.parse( dataCriacaoField.getText().trim() );
        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog( this, "Data inválida. Use o formato yyyy-MM-dd." );
            return;
        }

        ProdutoEntity produto = new ProdutoEntity();
        produto.setNome( nome );
        produto.setCategoria( categoria );
        produto.setPreco( preco );
        produto.setDataCriacao( dataCriacao );

        // Salva o produto no banco, através da camada service
        produtoService.cadastrarProduto( produto );

        JOptionPane.showMessageDialog( this, "Produto cadastrado com sucesso!" );

        // Limpa os campos após o cadastro
        nomeField.setText( "" );
        precoField.setText( "" );
        dataCriacaoField.setText( "" );
    }
}