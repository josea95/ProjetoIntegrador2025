//package org.example.Swing;
//
//import org.example.controller.FilaPedidoController;
//import org.example.controller.ProdutoSwingController;
//import org.example.model.entities.UsuarioEntity;
//import org.example.model.repository.ProdutoRepository;
//
//import org.example.controller.HistoricoPedidoController;
//import org.example.model.services.HistoricoPedidoService;
//
//import org.example.model.services.PedidoService;
//import org.example.model.services.ProdutoService;
//import org.example.model.services.FilaPedidoService;
//import org.example.model.util.CustomizerFactory;
//import org.example.view.FilaPedidoView;
//
//import javax.persistence.EntityManager;
//import javax.swing.*;
//import java.awt.*;
//import java.util.Scanner;
//
//public class MenuPrincipalSwing extends JFrame {
//
//    private final PedidoService pedidoService;
//    private final UsuarioEntity usuarioLogado;
//    private FilaPedidoController filaPedidoController;
//    private HistoricoPedidoController historicoPedidoController;
//
//    public MenuPrincipalSwing(PedidoService pedidoService, UsuarioEntity usuarioLogado) {
//        this.pedidoService = pedidoService;
//        this.usuarioLogado = usuarioLogado;
//        inicializarTela();
//    }
//
//    private void inicializarTela() {
//
//        EntityManager em = CustomizerFactory.getEntityManager();
//        Scanner scanner = new Scanner( System.in );
//
//        ProdutoRepository produtoRepository = new ProdutoRepository( em );
//        ProdutoService produtoService = new ProdutoService( produtoRepository );
//        ProdutoSwingController produtoController = new ProdutoSwingController( produtoService );
//
//        HistoricoPedidoService historicoPedidoService = new HistoricoPedidoService( em );
//        historicoPedidoController = new HistoricoPedidoController( historicoPedidoService );
//
//        FilaPedidoService filaPedidoService = new FilaPedidoService( em );
//        filaPedidoController = new FilaPedidoController(
//                filaPedidoService,
//                new FilaPedidoView( filaPedidoService, scanner ),
//                scanner
//        );
//
//        setTitle( "Menu Principal" );
//        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
//        setSize( 700, 500 );
//        setLocationRelativeTo( null );
//        getContentPane().setLayout( new GridLayout( 4, 2, 3, 3 ) );
//        getContentPane().setBackground( Color.WHITE );
//
//        JButton fazerPedidoButton = new JButton( "Fazer Pedido" );
//        fazerPedidoButton.setFont( new Font( "Verdana", Font.PLAIN, 12 ) );
//        fazerPedidoButton.addActionListener( e -> new FazerPedidoSwing( pedidoService, usuarioLogado ) );
//        getContentPane().add( fazerPedidoButton );
//
//        JButton btnCancelarPedido = new JButton( " Cancelar Pedido" );
//        getContentPane().add( btnCancelarPedido );
//
//        JButton btnVerFila = new JButton( " Ver Fila de Pedidos" );
//        getContentPane().add( btnVerFila );
//
//        JButton btnPersonalizacao = new JButton( " Personalização de Produtos" );
//        btnPersonalizacao.setFont( new Font( "Verdana", Font.PLAIN, 12 ) );
//        btnPersonalizacao.addActionListener( e -> produtoController.iniciar() );
//        getContentPane().add( btnPersonalizacao );
//
//        JButton btnPesquisarPedido = new JButton( "Pesquisar Pedido" );
//        getContentPane().add( btnPesquisarPedido );
//
//        JButton btnHistorico = new JButton( " Ver Histórico de Pedidos" );
//        btnHistorico.setFont( new Font( "Verdana", Font.PLAIN, 12 ) );
//        btnHistorico.addActionListener( e -> {
//            historicoPedidoController.verHistoricoPedidos( usuarioLogado );
//        } );
//        getContentPane().add( btnHistorico );
//
//        JButton btnRelatorio = new JButton( "Relatório de Vendas" );
//        getContentPane().add( btnRelatorio );
//
//        JButton btnSair = new JButton( " Sair" );
//        btnSair.addActionListener( e -> System.exit( 0 ) );
//        getContentPane().add( btnSair );
//
//        setVisible( true );
//    }
//
//    public void exibirMenu() {
//        System.out.println( "\n===== MENU PRINCIPAL =====" );
//        System.out.println( "1. Fazer Pedido" );
//        System.out.println( "2. Cancelar Pedido" );
//        System.out.println( "3. Ver Fila de Pedidos" );
//        System.out.println( "4. Personalização de Produtos" );
//        System.out.println( "5. Pesquisar Pedido" );
//        System.out.println( "6. Ver Histórico de Pedidos" );
//        System.out.println( "7. Relatório de Vendas" );
//        System.out.println( "8. Sair" );
//    }
//}

package org.example.Swing;

import org.example.controller.HistoricoPedidoController;
import org.example.controller.PedidoController;
import org.example.controller.MenuPersonalizacaoProdutoController;

import org.example.model.entities.UsuarioEntity;
import org.example.model.services.PedidoService;
import org.example.model.services.FilaPedidoService;
import org.example.model.services.HistoricoPedidoService;
import org.example.model.services.RelatorioService;

import javax.swing.*;
import java.awt.*;

public class MenuPrincipalSwing extends JFrame {

    private final PedidoService pedidoService;
    private final UsuarioEntity usuarioLogado;
    private final FilaPedidoService filaPedidoService;
    private final HistoricoPedidoService historicoPedidoService;
    private final RelatorioService relatorioService;

    private final MenuPersonalizacaoProdutoController menuPersonalizacaoProdutoController;
    private final HistoricoPedidoController historicoPedidoController;
    private final PedidoController pedidoController;

    private JButton btnFazerPedido;
    private JButton btnVerFilaPedidos;
    private JButton btnCancelarPedido;
    private JButton btnPersonalizacao;
    private JButton btnPesquisarPedido;
    private JButton btnHistorico;
    private JButton btnRelatorio;
    private JButton btnSair;

    public MenuPrincipalSwing(PedidoService pedidoService, UsuarioEntity usuarioEntity,
                              FilaPedidoService filaPedidoService,
                              HistoricoPedidoService historicoPedidoService,
                              RelatorioService relatorioService) {

        this.pedidoService = pedidoService;
        this.usuarioLogado = usuarioEntity;
        this.filaPedidoService = filaPedidoService;
        this.historicoPedidoService = historicoPedidoService;
        this.relatorioService = relatorioService;

        this.pedidoController = new PedidoController( pedidoService );
        this.menuPersonalizacaoProdutoController = new MenuPersonalizacaoProdutoController( null );
        this.historicoPedidoController = new HistoricoPedidoController( historicoPedidoService );

        inicializarTela();
    }

    public void inicializarTela() {
        setTitle( "Menu Principal" );
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        setSize( 700, 500 );
        setLocationRelativeTo( null );
        getContentPane().setLayout( new GridLayout( 4, 2, 3, 3 ) );

        btnFazerPedido = new JButton( "Fazer Novo Pedido" );
        btnFazerPedido.setFont( new Font( "Verdana", Font.PLAIN, 12 ) );
        btnFazerPedido.addActionListener( e -> new FazerPedidoSwing( pedidoService, usuarioLogado ) );
        getContentPane().add( btnFazerPedido );

        btnVerFilaPedidos = new JButton( "Ver Fila de Pedidos" );
        btnVerFilaPedidos.setFont( new Font( "Verdana", Font.PLAIN, 12 ) );
        btnVerFilaPedidos.addActionListener( e -> new FilaPedidosSwing( filaPedidoService ) );
        getContentPane().add( btnVerFilaPedidos );


        btnCancelarPedido = new JButton( "Cancelar Pedido" );
        btnCancelarPedido.setFont( new Font( "Verdana", Font.PLAIN, 12 ) );
        btnCancelarPedido.addActionListener( e -> {
            String idPedidoStr = JOptionPane.showInputDialog( MenuPrincipalSwing.this,
                    "Digite o ID do pedido a cancelar:" );
            if (idPedidoStr != null && !idPedidoStr.trim().isEmpty()) {
                try {
                    boolean canceladoComSucesso = filaPedidoService.cancelarPedido( idPedidoStr );
                    if (canceladoComSucesso) {
                        JOptionPane.showMessageDialog( MenuPrincipalSwing.this,
                                "Pedido " + idPedidoStr + " cancelado com sucesso." );
                    } else {
                        JOptionPane.showMessageDialog( MenuPrincipalSwing.this,
                                "Não foi possível cancelar o pedido " + idPedidoStr +
                                        ". Verifique se o ID está correto ou o status do pedido.",
                                "Erro de Cancelamento", JOptionPane.WARNING_MESSAGE );
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog( MenuPrincipalSwing.this,
                            "Erro ao cancelar pedido: " + ex.getMessage(),
                            "Erro", JOptionPane.ERROR_MESSAGE );
                    ex.printStackTrace();
                }
            } else {
                JOptionPane.showMessageDialog( MenuPrincipalSwing.this,
                        "Operação de cancelamento abortada." );
            }
        } );
        getContentPane().add( btnCancelarPedido );

        btnPersonalizacao = new JButton( "Personalização de Produtos" );
        btnPersonalizacao.setFont( new Font( "Verdana", Font.PLAIN, 12 ) );
        btnPersonalizacao.addActionListener( e -> menuPersonalizacaoProdutoController.iniciar() );
        getContentPane().add( btnPersonalizacao );

        btnPesquisarPedido = new JButton("Pesquisar Pedido");
        btnPesquisarPedido.setFont(new Font("Verdana", Font.PLAIN, 12));

        btnPesquisarPedido.addActionListener(e -> {
            TelaDePesquisarPedidosSwing telaPesquisar = new TelaDePesquisarPedidosSwing();
            telaPesquisar.setVisible(true);
        });
        getContentPane().add(btnPesquisarPedido);

        btnHistorico = new JButton( "Ver Histórico de Pedidos" );
        btnHistorico.setFont( new Font( "Verdana", Font.PLAIN, 12 ) );
        btnHistorico.addActionListener( e -> historicoPedidoController.verHistoricoPedidos( usuarioLogado ) );
        getContentPane().add( btnHistorico );

        btnRelatorio = new JButton( "Relatório de Vendas" );
        btnRelatorio.setFont( new Font( "Verdana", Font.PLAIN, 12 ) );
        btnRelatorio.addActionListener( e -> new RelatorioSwing( relatorioService ) );
        getContentPane().add( btnRelatorio );

        btnSair = new JButton( "Sair" );
        btnSair.setFont( new Font( "Verdana", Font.PLAIN, 12 ) );
        btnSair.addActionListener( e -> {
            JOptionPane.showMessageDialog( MenuPrincipalSwing.this, "Encerrando o sistema..." );
            System.exit( 0 );
        } );
        getContentPane().add( btnSair );
        setVisible( true );
    }
}

