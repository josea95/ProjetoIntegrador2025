package org.example.Swing;

import org.example.controller.ProdutoController;
import org.example.model.entities.ProdutoEntity;

import javax.swing.*;
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
        JPanel categoriaPanel = new JPanel( new FlowLayout() );
        categoriaPanel.add( new JLabel( "Selecione a Categoria:" ) );
        // Array de categorias disponíveis
        String[] categorias = {"Marmitas", "Bebidas", "Porções"};

        categoriaCombo = new JComboBox<>( categorias );
        categoriaPanel.add( categoriaCombo );

        buscarButton = new JButton( "Buscar Produtos" );
        // Associa um evento ao botão que chama o buscarProdutosPorCategoria
        buscarButton.addActionListener( e -> buscarProdutosPorCategoria() );
        categoriaPanel.add( buscarButton );

        panel.add( categoriaPanel, BorderLayout.NORTH );

        // Inicializa o modelo da lista e o componente JList para exibir os produtos
        listModel = new DefaultListModel<>();
        produtosList = new JList<>( listModel );
        produtosList.setSelectionMode( ListSelectionModel.SINGLE_SELECTION ); // Permite seleção de apenas um item por vez
        // Adiciona a lista dentro do JScrollPane para rolagem
        panel.add( new JScrollPane( produtosList ), BorderLayout.CENTER );


        JPanel botoesPanel = new JPanel( new FlowLayout() );
        // Botão para selecionar um produto da lista
        selecionarButton = new JButton( "Selecionar Produto" );
        // Evento do botão que verifica se algum produto foi selecionado, se sim chama o painel de atualização
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
        voltarButton.addActionListener( e -> dispose() );
        botoesPanel.add( voltarButton );
        panel.add( botoesPanel, BorderLayout.SOUTH );

        setContentPane( panel );
        revalidate();
        repaint();
    }


    private void initAtualizacaoPanel(ProdutoEntity produto) {
        JPanel panel = new JPanel( new GridLayout( 8, 2, 10, 10 ) );

        panel.add( new JLabel( "ID do Produto:" ) );
        idField = new JTextField( String.valueOf( produto.getId() ) );
        idField.setEditable( false );// (campo não editável)
        panel.add( idField );


        panel.add( new JLabel( "Data de Criação:" ) );
        dataCriacaoField = new JTextField( produto.getDataCriacao().format( DateTimeFormatter.ISO_LOCAL_DATE ) );
        dataCriacaoField.setEditable( false );// (campo não editável)
        panel.add( dataCriacaoField );

        // Exibe a categoria do produto
        panel.add( new JLabel( "Categoria:" ) );
        categoriaField = new JTextField( produto.getCategoria() );
        categoriaField.setEditable( false );// (campo não editável)
        panel.add( categoriaField );

        // Campo para atualizar o nome do produto
        panel.add( new JLabel( "Novo Nome:" ) );
        nomeField = new JTextField( produto.getNome() );
        panel.add( nomeField );

        // Campo para atualizar o preço do produto
        panel.add( new JLabel( "Novo Preço:" ) );
        precoField = new JTextField( String.valueOf( produto.getPreco() ) );
        panel.add( precoField );

        // Campo para atualizar a descrição do produto (aceita descrição vazia)
        panel.add( new JLabel( "Nova Descrição:" ) );
        descricaoField = new JTextField( produto.getDescricao() == null ? "" : produto.getDescricao() );
        panel.add( descricaoField );

        // Botão para confirmar a atualização
        atualizarButton = new JButton( "Atualizar" );
        atualizarButton.addActionListener( this::atualizarProduto );
        panel.add( atualizarButton );

        voltarButton = new JButton( "Voltar" );
        voltarButton.addActionListener( e -> initCategoriaPanel() );
        panel.add( voltarButton );

        setContentPane( panel );
        revalidate();
        repaint();
    }

    // Metodo para buscar os produtos pela categoria selecionada no JComboBox
    private void buscarProdutosPorCategoria() {
        String categoriaSelecionada = (String) categoriaCombo.getSelectedItem();
        // Chama o controller para buscar os produtos da categoria
        List<ProdutoEntity> filtrados = produtoController.buscarPorCategoria( categoriaSelecionada );
        listModel.clear(); // Limpa a lista atual
        if (filtrados == null || filtrados.isEmpty()) {
            JOptionPane.showMessageDialog( this, "Nenhum produto encontrado para a categoria " + categoriaSelecionada );
        } else {
            // Adiciona cada produto encontrado ao modelo da lista
            filtrados.forEach( listModel::addElement );
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
            initCategoriaPanel();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog( this, "Preço inválido. Informe um número válido." );
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog( this, ex.getMessage() );
        } catch (Exception ex) {
            JOptionPane.showMessageDialog( this, "Erro ao atualizar: " + ex.getMessage() );
            ex.printStackTrace();
        }
    }
}