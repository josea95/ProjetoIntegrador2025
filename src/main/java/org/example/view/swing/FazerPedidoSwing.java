package org.example.view.swing;

import org.example.controller.PedidoController;

import org.example.model.entities.FilaPedidoEntity;
import org.example.model.entities.ProdutoEntity;
import org.example.model.entities.UsuarioEntity;
import org.example.model.repository.UsuarioRepository;

import org.example.model.services.PedidoService;
import org.example.model.util.CustomizerFactory;

import javax.persistence.EntityManager;
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

    private PedidoService pedidoService;
    private PedidoController pedidoController;
    private UsuarioEntity usuarioLogado;
    private FilaPedidoEntity pedido;

    public FazerPedidoSwing(PedidoService pedidoService, UsuarioEntity usuarioLogado) {
        this.pedidoService = pedidoService;
        this.usuarioLogado = usuarioLogado;
        this.pedido = pedidoService.fazerPedido( usuarioLogado );
        this.pedidoController = new PedidoController( pedidoService );

        setTitle( "Fazer Pedido" );
        setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
        setSize( 700, 500 );
        setLocationRelativeTo( null );
        initComponents();
        setVisible( true );
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel( new BorderLayout( 10, 10 ) );

        // Painel superior
        JPanel topPanel = new JPanel( new FlowLayout() );
        topPanel.add( new JLabel( "Categoria:" ) );
        String[] categorias = {"Marmitas", "Bebidas", "Porções"};
        categoriaCombo = new JComboBox<>( categorias );
        topPanel.add( categoriaCombo );

        buscarButton = new JButton( "Buscar Produtos" );
        buscarButton.setFont( new Font( "Verdana", Font.PLAIN, 11 ) );
        buscarButton.addActionListener( e -> {
            String categoriaSelecionada = (String) categoriaCombo.getSelectedItem();
            List<ProdutoEntity> produtos = pedidoService.buscarProdutosPorCategoria( categoriaSelecionada );
            produtosModel.clear();
            for (ProdutoEntity produto : produtos) {
                produtosModel.addElement( produto );
            }
        } );
        topPanel.add( buscarButton );
        mainPanel.add( topPanel, BorderLayout.NORTH );

        // Painel central
        produtosModel = new DefaultListModel<>();
        produtosList = new JList<>( produtosModel );

        carrinhoModel = new DefaultListModel<>();
        carrinhoList = new JList<>( carrinhoModel );

        JPanel centerPanel = new JPanel( new GridLayout( 1, 2, 10, 10 ) );
        centerPanel.add( new JScrollPane( produtosList ) );
        centerPanel.add( new JScrollPane( carrinhoList ) );
        mainPanel.add( centerPanel, BorderLayout.CENTER );

        // Painel inferior
        JPanel bottomPanel = new JPanel( new FlowLayout() );

        adicionarButton = new JButton( "Adicionar ao Carrinho" );
        adicionarButton.setFont( new Font( "Verdana", Font.PLAIN, 11 ) );
        adicionarButton.addActionListener( e -> {
            ProdutoEntity produtoEscolhido = produtosList.getSelectedValue();
            if (produtoEscolhido != null) {
                carrinhoModel.addElement( produtoEscolhido );
                pedidoService.adicionarProdutoAoPedido( pedido, produtoEscolhido );
            } else {
                JOptionPane.showMessageDialog( this, "Selecione um produto para adicionar." );
            }
        } );
        bottomPanel.add( adicionarButton );

        removerButton = new JButton( "Remover do Carrinho" );
        removerButton.setFont( new Font( "Verdana", Font.PLAIN, 11 ) );
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
        confirmarButton.setFont( new Font( "Verdana", Font.PLAIN, 11 ) );
        confirmarButton.addActionListener( e -> {
            String resultado = pedidoController.confirmarPedido( pedido );
            if (resultado.startsWith( "ERRO" )) {
                JOptionPane.showMessageDialog( this, resultado, "Erro", JOptionPane.ERROR_MESSAGE );
            } else {
                JOptionPane.showMessageDialog( this, resultado, "Pedido confirmado", JOptionPane.INFORMATION_MESSAGE );
                dispose();
            }
        } );
        bottomPanel.add( confirmarButton );

        voltarButton = new JButton( "Voltar" );
        voltarButton.setFont( new Font( "Verdana", Font.PLAIN, 11 ) );
        voltarButton.addActionListener( e -> dispose() );
        bottomPanel.add( voltarButton );

        mainPanel.add( bottomPanel, BorderLayout.SOUTH );
        setContentPane( mainPanel );
    }

    public static void main(String[] args) {
        EntityManager em = CustomizerFactory.getEntityManager();
        //Pasando informacoes de teste para o usuario, para rodar o programa e nao ficar dando erro na hora de finalizar um pedido
        UsuarioRepository usuarioRepo = new UsuarioRepository( em );
        String email = "exemplo@exemplo.com";
        UsuarioEntity usuarioTeste = usuarioRepo.buscarPorLoginESenha( "admin Teste", "admin123" );

        if (usuarioTeste == null) {
            usuarioTeste = new UsuarioEntity();
            usuarioTeste.setNome( "Usuário Teste" );
            usuarioTeste.setLogin( "admin Teste" );
            usuarioTeste.setSenha( "admin123" );
            usuarioTeste.setEmail( email );

            em.getTransaction().begin();
            em.persist( usuarioTeste );
            em.getTransaction().commit();
        }

        PedidoService pedidoService = new PedidoService( em );

        final UsuarioEntity usuarioFinal = usuarioTeste;
        final PedidoService pedidoFinal = pedidoService;

        SwingUtilities.invokeLater( () -> new FazerPedidoSwing( pedidoFinal, usuarioFinal ) );
    }
}
