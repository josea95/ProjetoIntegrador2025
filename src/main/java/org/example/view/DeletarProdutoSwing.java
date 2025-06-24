package org.example.view;

import org.example.controller.ProdutoController;
import org.example.model.entities.ProdutoEntity;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class DeletarProdutoSwing extends JFrame {

    // Definindo a fonte e cor de fundo
    private final Font fonte = new Font( "Arial", Font.BOLD, 14 );
    private final Color fundo = new Color( 220, 220, 220 );

    private JComboBox<String> categoriaCombo;
    private JButton buscarButton;
    private DefaultListModel<ProdutoEntity> listModel;
    private JList<ProdutoEntity> produtosList;
    private JButton deletarButton;
    private JButton voltarButton;

    private ProdutoController produtoController;

    public DeletarProdutoSwing(ProdutoController produtoController) {
        this.produtoController = produtoController;
        setTitle( "Deletar Produto" );
        setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
        setSize( 700, 500 );
        setLocationRelativeTo( null );
        initComponents();
        setVisible( true );
    }

    private void initComponents() {
        JPanel panel = new JPanel( new BorderLayout( 5, 5 ) );
        panel.setBackground( fundo );

        // Painel de seleção de categoria
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

        // Listagem dos produtos
        listModel = new DefaultListModel<>();
        produtosList = new JList<>( listModel );
        produtosList.setSelectionMode( ListSelectionModel.SINGLE_SELECTION );
        produtosList.setFont( fonte );
        panel.add( new JScrollPane( produtosList ), BorderLayout.CENTER );

        // Painel de botões
        JPanel botoesPanel = new JPanel( new FlowLayout() );

        botoesPanel.setBackground( fundo );
        deletarButton = new JButton( "Deletar Produto" );
        deletarButton.setFont( fonte );
        deletarButton.setBackground( fundo );
        deletarButton.addActionListener( e -> deletarProduto() );
        botoesPanel.add( deletarButton );

        voltarButton = new JButton( "Voltar" );
        voltarButton.setFont( fonte );
        voltarButton.setBackground( fundo );
        voltarButton.addActionListener( e -> dispose() );
        botoesPanel.add( voltarButton );
        panel.add( botoesPanel, BorderLayout.SOUTH );

        setContentPane( panel );
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

    private void deletarProduto() {
        ProdutoEntity produtoSelecionado = produtosList.getSelectedValue();
        if (produtoSelecionado == null) {
            JOptionPane.showMessageDialog( this, "Selecione um produto para deletar." );
            return;
        }
        int resposta = JOptionPane.showConfirmDialog(
                this,
                "Tem certeza que deseja deletar o produto: " + produtoSelecionado.getNome() + "?",
                "Confirmação",
                JOptionPane.YES_NO_OPTION
        );
        if (resposta == JOptionPane.YES_OPTION) {
            try {
                produtoController.deletarProduto( produtoSelecionado.getId() );
                JOptionPane.showMessageDialog( this, "Produto deletado com sucesso!" );
                buscarProdutosPorCategoria();
            } catch (IllegalStateException | IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(
                        this,
                        ex.getMessage(),
                        "Erro ao deletar",
                        JOptionPane.WARNING_MESSAGE
                );
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(
                        this,
                        "Erro inesperado ao deletar o produto. Por favor, tente novamente.",
                        "Erro",
                        JOptionPane.ERROR_MESSAGE
                );
                // ex.printStackTrace(); para aparecer no terminal
            }
        }
    }
}