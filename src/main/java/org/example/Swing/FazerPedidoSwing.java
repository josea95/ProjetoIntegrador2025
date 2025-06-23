package org.example.Swing;

import org.example.model.entities.FilaPedidoEntity;
import org.example.model.entities.ProdutoEntity;
import org.example.model.entities.UsuarioEntity;

import org.example.controller.PedidoController;

import org.example.model.services.PedidoService;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class FazerPedidoSwing extends JFrame {

    private JComboBox<String> categoriaCombo;
    private DefaultListModel<ProdutoEntity> produtosModel;
    private DefaultListModel<ProdutoEntity> carrinhoModel;
    private JList<ProdutoEntity> produtosList;
    private JList<ProdutoEntity> carrinhoList;

    private JButton buscarButton;
    private JButton adicionarButton;
    private JButton removerButton;
    private JButton confirmarButton;
    private JButton voltarButton;

    private UsuarioEntity usuarioLogado;
    private FilaPedidoEntity filaPedidoEntityPedido;
    private PedidoController pedidoController;

    private final Font fonte = new Font( "Arial", Font.BOLD, 14 );
    private final Color fundo = new Color( 220, 220, 220 );
    private final Color corBotoes = new Color( 245, 245, 245 );

    public FazerPedidoSwing(PedidoService pedidoService, UsuarioEntity usuarioLogado) {
        this.usuarioLogado = usuarioLogado;
        this.pedidoController = new PedidoController( pedidoService );
        this.filaPedidoEntityPedido = pedidoController.iniciarPedido( usuarioLogado );
        setTitle( "Fazer Pedido" );
        setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
        setSize( 700, 500 );
        setLocationRelativeTo( null );
        initComponents();
        setVisible( true );
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel( new BorderLayout( 10, 10 ) );
        mainPanel.setBackground( fundo );

        // Painel superior
        JPanel topPanel = new JPanel( new FlowLayout() );
        JLabel labelCategoria = new JLabel( "Categoria:" );
        labelCategoria.setFont( fonte );
        topPanel.add( labelCategoria );
        String[] categorias = {"Marmitas", "Bebidas", "Porções"};
        categoriaCombo = new JComboBox<>( categorias );
        topPanel.add( categoriaCombo );

        buscarButton = new JButton( "Buscar Produtos" );
        buscarButton.setFont( fonte );
        buscarButton.setBackground( corBotoes );

        buscarButton.addActionListener( e -> {
            String categoriaSelecionada = (String) categoriaCombo.getSelectedItem();
            List<ProdutoEntity> produtos = pedidoController.buscarProdutosPorCategoria( categoriaSelecionada );
            produtosModel.clear();
            for (ProdutoEntity produto : produtos) {
                produtosModel.addElement( produto );
            }
        } );
        topPanel.add( buscarButton );
        //Painel principal -> onde os produtos e o carrinho serão exibidos
        mainPanel.add( topPanel, BorderLayout.NORTH );
        produtosModel = new DefaultListModel<>();
        produtosList = new JList<>( produtosModel );

        carrinhoModel = new DefaultListModel<>();
        carrinhoList = new JList<>( carrinhoModel );

        JPanel centerPanel = new JPanel( new GridLayout( 1, 2, 10, 10 ) );
        centerPanel.add( new JScrollPane( produtosList ) );
        centerPanel.add( new JScrollPane( carrinhoList ) );
        mainPanel.add( centerPanel, BorderLayout.CENTER );

        //Painel inferior -> onde os botões serão exibidos
        JPanel bottomPanel = new JPanel( new FlowLayout() );
        bottomPanel.setBackground( fundo );

        adicionarButton = new JButton( "Adicionar ao Carrinho" );
        adicionarButton.setFont( fonte );
        adicionarButton.setBackground( corBotoes );
        adicionarButton.addActionListener( e -> {
            ProdutoEntity produtoEscolhido = produtosList.getSelectedValue();
            if (produtoEscolhido != null) {
                carrinhoModel.addElement( produtoEscolhido );
                pedidoController.adicionarProdutoAoPedido( filaPedidoEntityPedido, produtoEscolhido );
            } else {
                JOptionPane.showMessageDialog( this, "Selecione um produto para adicionar." );
            }
        } );
        bottomPanel.add( adicionarButton );

        removerButton = new JButton( "Remover do Carrinho" );
        removerButton.setFont( fonte );
        removerButton.setBackground( corBotoes );
        removerButton.addActionListener( e -> {
            ProdutoEntity produtoSelecionado = carrinhoList.getSelectedValue();
            if (produtoSelecionado != null) {
                carrinhoModel.removeElement( produtoSelecionado );
            } else {
                JOptionPane.showMessageDialog( this, "Selecione um item para remover." );
            }
        } );
        bottomPanel.add( removerButton );

        confirmarButton = new JButton( "Confirmar Pedido" );
        confirmarButton.setFont( fonte );
        confirmarButton.setBackground( corBotoes );

        confirmarButton.addActionListener( e -> {
            if (filaPedidoEntityPedido == null || filaPedidoEntityPedido.getProdutos().isEmpty()) {
                JOptionPane.showMessageDialog( this,
                        "O carrinho está vazio. Adicione produtos antes de confirmar.",
                        "Erro",
                        JOptionPane.ERROR_MESSAGE );
                return;
            }
            String descricao = JOptionPane.showInputDialog( this,
                    "Digite uma descrição para o pedido (opcional):" );
            if (descricao != null && !descricao.trim().isEmpty()) {
                filaPedidoEntityPedido.setObservacao( descricao );
            }


            boolean confirmado = pedidoController.confirmarPedido( filaPedidoEntityPedido );
            if (!confirmado) {
                JOptionPane.showMessageDialog( this,
                        "Erro ao confirmar pedido. Verifique se há produtos no carrinho.",
                        "Erro",
                        JOptionPane.ERROR_MESSAGE );
            } else {
                JOptionPane.showMessageDialog( this,
                        "Pedido confirmado com sucesso! Sua senha é " + filaPedidoEntityPedido.getSenhaPedido(),
                        "Pedido confirmado",
                        JOptionPane.INFORMATION_MESSAGE );
                carrinhoModel.clear();
                filaPedidoEntityPedido = pedidoController.iniciarPedido( usuarioLogado );
            }
        } );
        bottomPanel.add( confirmarButton );

        voltarButton = new JButton( "Voltar" );
        voltarButton.setFont( fonte );
        voltarButton.setBackground( corBotoes );
        voltarButton.addActionListener( e -> dispose() );
        bottomPanel.add( voltarButton );
        mainPanel.add( bottomPanel, BorderLayout.SOUTH );
        setContentPane( mainPanel );
    }
}