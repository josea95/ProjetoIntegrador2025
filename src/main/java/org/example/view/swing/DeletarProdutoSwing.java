package org.example.view.swing;

import org.example.model.entities.ProdutoEntity;
import org.example.model.services.ProdutoService;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class DeletarProdutoSwing extends JFrame {

    private JComboBox<String> categoriaCombo;
    private JButton buscarButton;
    private DefaultListModel<ProdutoEntity> listModel;
    private JList<ProdutoEntity> produtosList;
    private JButton deletarButton;
    private JButton voltarButton;
    private ProdutoService produtoService;

    public DeletarProdutoSwing(ProdutoService produtoService) {
        this.produtoService = produtoService;
        setTitle( "Deletar Produto" );
        setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
        setSize( 500, 400 );
        setLocationRelativeTo( null );
        initComponents();
        setVisible( true );
    }

    private void initComponents() {
        JPanel panel = new JPanel( new BorderLayout( 5, 5 ) );

        // Painel de seleção de categoria
        JPanel categoriaPanel = new JPanel( new FlowLayout() );
        categoriaPanel.add( new JLabel( "Selecione a Categoria:" ) );
        String[] categorias = {"Marmitas", "Bebidas", "Porções"};
        categoriaCombo = new JComboBox<>( categorias );
        categoriaPanel.add( categoriaCombo );
        buscarButton = new JButton( "Buscar Produtos" );
        buscarButton.addActionListener( e -> buscarProdutosPorCategoria() );
        categoriaPanel.add( buscarButton );
        panel.add( categoriaPanel, BorderLayout.NORTH );

        // Listagem dos produtos
        listModel = new DefaultListModel<>();
        produtosList = new JList<>( listModel );
        produtosList.setSelectionMode( ListSelectionModel.SINGLE_SELECTION );

        panel.add( new JScrollPane( produtosList ), BorderLayout.CENTER );

        // Painel de botões
        JPanel botoesPanel = new JPanel( new FlowLayout() );
        deletarButton = new JButton( "Deletar Produto" );
        deletarButton.addActionListener( e -> deletarProduto() );
        botoesPanel.add( deletarButton );

        voltarButton = new JButton( "Voltar" );
        voltarButton.addActionListener( e -> dispose() );
        botoesPanel.add( voltarButton );
        panel.add( botoesPanel, BorderLayout.SOUTH );

        setContentPane( panel );
    }

    private void buscarProdutosPorCategoria() {
        String categoriaSelecionada = (String) categoriaCombo.getSelectedItem();
        List<ProdutoEntity> produtos = produtoService.buscarProdutosPorCategoria( categoriaSelecionada );
        listModel.clear();
        if (produtos == null || produtos.isEmpty()) {
            JOptionPane.showMessageDialog( this, "Nenhum produto encontrado para a categoria " + categoriaSelecionada );
        } else {
            produtos.forEach( listModel::addElement );
        }
    }

    private void deletarProduto() {
        ProdutoEntity produtoSelecionado = produtosList.getSelectedValue();
        if (produtoSelecionado == null) {
            JOptionPane.showMessageDialog( this, "Selecione um produto para deletar." );
            return;
        }
        int resposta = JOptionPane.showConfirmDialog( this,
                "Tem certeza que deseja deletar o produto: " + produtoSelecionado.getNome() + "?",
                "Confirmação", JOptionPane.YES_NO_OPTION );
        if (resposta == JOptionPane.YES_OPTION) {
            produtoService.deletarProduto( produtoSelecionado.getId() );
            JOptionPane.showMessageDialog( this, "Produto deletado com sucesso!" );
            listModel.removeElement( produtoSelecionado );
        }
    }
}