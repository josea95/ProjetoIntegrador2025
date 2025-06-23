package org.example.Swing;

import org.example.controller.ProdutoController;
import org.example.model.entities.ProdutoEntity;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class AtualizarProdutoSwing extends JFrame {

    private JComboBox<String> categoriaCombo;                  // Dropdown para selecionar categoria
    private DefaultListModel<ProdutoEntity> listModel;         // Modelo da lista que armazena produtos
    private JList<ProdutoEntity> produtosList;                 // Lista visual de produtos

    private JTextField idField;
    private JTextField nomeField;
    private JTextField precoField;
    private JTextField descricaoField;
    private JTextField dataCriacaoField;
    private JTextField categoriaField;

    private JButton buscarButton;
    private JButton selecionarButton;
    private JButton atualizarButton;
    private JButton voltarButton;

    private ProdutoController produtoController;

    // Definindo a fonte e cor de fundo
    private final Font fonte = new Font( "Arial", Font.BOLD, 14 );
    private final Color fundo = new Color( 220, 220, 220 );

    public AtualizarProdutoSwing(ProdutoController produtoController) {
        this.produtoController = produtoController;
        setTitle( "Atualizar Produto" );
        setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
        setSize( 700, 500 );
        setLocationRelativeTo( null );
        initCategoriaPanel();
        setVisible( true );
    }

    private void initCategoriaPanel() {
        JPanel panel = new JPanel( new BorderLayout( 5, 5 ) );
        panel.setBackground( fundo );
        panel.setBorder( new EmptyBorder( 10, 10, 10, 10 ) );

        JPanel categoriaPanel = new JPanel( new FlowLayout() );
        categoriaPanel.setBackground( fundo );
        JLabel categoriaLabel = new JLabel( "Selecione a Categoria:" );
        categoriaLabel.setFont( fonte );
        categoriaPanel.add( categoriaLabel );

        String[] categorias = {"Marmitas", "Bebidas", "Porções"};
        categoriaCombo = new JComboBox<>( categorias );
        categoriaCombo.setFont( fonte );
        categoriaPanel.add( categoriaCombo );

        buscarButton = new JButton( "Buscar Produtos" );
        buscarButton.setFont( fonte );
        buscarButton.setBackground( fundo );
        buscarButton.addActionListener( e -> buscarProdutosPorCategoria() );
        categoriaPanel.add( buscarButton );

        panel.add( categoriaPanel, BorderLayout.NORTH );

        listModel = new DefaultListModel<>();
        produtosList = new JList<>( listModel );
        produtosList.setSelectionMode( ListSelectionModel.SINGLE_SELECTION );
        produtosList.setFont( fonte );
        panel.add( new JScrollPane( produtosList ), BorderLayout.CENTER );

        JPanel botoesPanel = new JPanel( new FlowLayout() );
        botoesPanel.setBackground( fundo );
        selecionarButton = new JButton( "Selecionar Produto" );
        selecionarButton.setFont( fonte );
        selecionarButton.setBackground( fundo );
        selecionarButton.addActionListener( e -> {
            ProdutoEntity produtoSelecionado = produtosList.getSelectedValue();
            if (produtoSelecionado != null) {
                initAtualizacaoPanel( produtoSelecionado );
            } else {
                JOptionPane.showMessageDialog( this, "Selecione um produto da lista." );
            }
        } );
        botoesPanel.add( selecionarButton );
        voltarButton = new JButton( "Voltar" );
        voltarButton.setFont( fonte );
        voltarButton.setBackground( fundo );
        voltarButton.addActionListener( e -> dispose() );
        botoesPanel.add( voltarButton );
        panel.add( botoesPanel, BorderLayout.SOUTH );

        setContentPane( panel );
        revalidate();
        repaint();
    }

    private void initAtualizacaoPanel(ProdutoEntity produto) {
        JPanel panel = new JPanel( new GridLayout( 8, 2, 10, 10 ) );
        panel.setBorder( new EmptyBorder( 10, 10, 10, 10 ) );
        panel.setBackground( fundo );

        panel.add( new JLabel( "ID do Produto:" ) );
        JLabel idLabel = new JLabel( String.valueOf( produto.getId() ) );
        idLabel.setFont( fonte );
        panel.add( idLabel );
        idField = new JTextField( String.valueOf( produto.getId() ) );
        idField.setEditable( false );

        panel.add( new JLabel( "Data de Criação:" ) );
        dataCriacaoField = new JTextField( produto.getDataCriacao().format( DateTimeFormatter.ISO_LOCAL_DATE ) );
        dataCriacaoField.setEditable( false );
        panel.add( dataCriacaoField );

        panel.add( new JLabel( "Categoria:" ) );
        categoriaField = new JTextField( produto.getCategoria() );
        categoriaField.setEditable( false );
        panel.add( categoriaField );

        panel.add( new JLabel( "Novo Nome:" ) );
        nomeField = new JTextField( produto.getNome() );
        panel.add( nomeField );

        panel.add( new JLabel( "Novo Preço:" ) );
        precoField = new JTextField( String.valueOf( produto.getPreco() ) );
        panel.add( precoField );

        // Campo para atualizar a descrição do produto (aceita descrição vazia)
        panel.add( new JLabel( "Nova Descrição:" ) );
        descricaoField = new JTextField( produto.getDescricao() == null ? "" : produto.getDescricao() );
        panel.add( descricaoField );

        atualizarButton = new JButton( "Atualizar" );
        atualizarButton.setFont( fonte );
        atualizarButton.setBackground( new Color( 220, 220, 220 ) );
        atualizarButton.addActionListener( this::atualizarProduto );
        panel.add( atualizarButton );

        voltarButton = new JButton( "Voltar" );
        voltarButton.setFont( fonte );
        voltarButton.setBackground( new Color( 220, 220, 220 ) );
        voltarButton.addActionListener( e -> initCategoriaPanel() );
        panel.add( voltarButton );

        setContentPane( panel );
        revalidate();
        repaint();
    }

    private void buscarProdutosPorCategoria() {
        try {
            String categoriaSelecionada = (String) categoriaCombo.getSelectedItem();
            List<ProdutoEntity> produtos = produtoController.buscarPorCategoria( categoriaSelecionada );
            listModel.clear();
            if (produtos.isEmpty()) {
                JOptionPane.showMessageDialog( this, "Nenhum produto encontrado para a categoria " + categoriaSelecionada );
            } else {
                produtos.forEach( listModel::addElement );
            }
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog( this, ex.getMessage() );
        } catch (Exception ex) {
            JOptionPane.showMessageDialog( this, "Erro ao buscar produtos: " + ex.getMessage() );
            ex.printStackTrace();
        }
    }

    private void atualizarProduto(ActionEvent e) {
        try {
            Long id = Long.parseLong( idField.getText().trim() );
            String nome = nomeField.getText().trim();
            String precoText = precoField.getText().trim();
            String descricao = descricaoField.getText().trim();
            double preco = Double.parseDouble( precoText );

            ProdutoEntity produtoAtualizado = new ProdutoEntity();
            produtoAtualizado.setId( id );
            produtoAtualizado.setNome( nome );
            produtoAtualizado.setPreco( preco );
            produtoAtualizado.setDescricao( descricao );
            produtoAtualizado.setDataCriacao(
                    java.time.LocalDate.parse( dataCriacaoField.getText(), DateTimeFormatter.ISO_LOCAL_DATE )
            );
            produtoAtualizado.setCategoria( categoriaField.getText() );
            produtoController.atualizarProduto( produtoAtualizado );

            JOptionPane.showMessageDialog( this, "Produto atualizado com sucesso!" );
            atualizarTabelaProdutos( categoriaField.getText() );
            initCategoriaPanel();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog( this, "Preço inválido. Informe um número válido." );
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog( this, ex.getMessage() );
        } catch (Exception ex) {
            JOptionPane.showMessageDialog( this, "Erro ao atualizar: " + ex.getMessage() );
        }
    }

    // Metodo que atualiza a lista de produtos depois de atualizar um produto
    private void atualizarTabelaProdutos(String categoriaSelecionada) {
        List<ProdutoEntity> produtos = produtoController.buscarPorCategoria( categoriaSelecionada );
        listModel.clear();
        produtos.forEach(listModel::addElement);
    }
}