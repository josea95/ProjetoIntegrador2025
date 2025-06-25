package org.example.Swing;

import org.example.controller.HistoricoPedidoController;
import org.example.controller.PedidoController;
import org.example.controller.MenuPersonalizacaoProdutoController;

import org.example.controller.ProdutoController;
import org.example.model.entities.UsuarioEntity;
import org.example.model.repository.FilaPedidoRepository;
import org.example.model.repository.HistoricoPedidoRepository;
import org.example.model.repository.ProdutoHistoricoPedidoRepository;

import org.example.model.services.*;
import org.example.model.util.CustomizerFactory;
import org.example.view.TelaDePesquisarPedidosSwing;

import javax.persistence.EntityManager;
import javax.swing.*;
import java.awt.*;

public class MenuPrincipalSwing extends JFrame {
    EntityManager em = CustomizerFactory.getEntityManager();
    private final PedidoService pedidoService;
    private final UsuarioEntity usuarioLogado;
    private final FilaPedidoService filaPedidoService;
    private final HistoricoPedidoService historicoPedidoService;
    private final RelatorioService relatorioService;

    private final MenuPersonalizacaoProdutoController menuPersonalizacaoProdutoController;
    private final HistoricoPedidoController historicoPedidoController;
    private final PedidoController pedidoController;
    private final FilaPedidoRepository filaPedidoRepository = new FilaPedidoRepository( em );

    private HistoricoPedidoRepository historicoPedidoRepository = new HistoricoPedidoRepository( em );
    private ProdutoHistoricoPedidoRepository produtoHistoricoRepository = new ProdutoHistoricoPedidoRepository( em );

    private JButton btnFazerPedido;
    private JButton btnVerFilaPedidos;
    private JButton btnCancelarPedido;
    private JButton btnPersonalizacao;
    private JButton btnPesquisarPedido;
    private JButton btnHistorico;
    private JButton btnRelatorio;
    private JButton btnSair;

    public MenuPrincipalSwing(PedidoService pedidoService, UsuarioEntity usuarioLogado,
                              FilaPedidoService filaPedidoService,
                              HistoricoPedidoService historicoPedidoService,
                              RelatorioService relatorioService) {

        this.pedidoService = pedidoService;
        this.usuarioLogado = usuarioLogado;
        setTitle("Menu Principal - Usuário: " + usuarioLogado.getNome());
        this.filaPedidoService = filaPedidoService;
        this.historicoPedidoService = historicoPedidoService;
        this.relatorioService = relatorioService;

        this.pedidoController = new PedidoController( pedidoService );                      /*Arrumado aqui, não pode ser null pq se for chamar qualquer metodo de pernsonalização e ele estiver como null dará erro
                                                                                              porque ele sempre vai retornar null no produtoService.*/
//        this.menuPersonalizacaoProdutoController = new MenuPersonalizacaoProdutoController( new ProdutoService( new ProdutoRepository( em ) ) );

        //Adptando para o novo controller de MenuPersonalizacaoProdutoController e ProdutoController
        this.menuPersonalizacaoProdutoController = new MenuPersonalizacaoProdutoController( new ProdutoController() );

        this.historicoPedidoController = new HistoricoPedidoController( historicoPedidoService );
        this.historicoPedidoRepository = new HistoricoPedidoRepository( em );
        this.produtoHistoricoRepository = new ProdutoHistoricoPedidoRepository( em );

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
        //Falta implementar o metodo de cancelamento de pedidos e passar os parametros necessários
        btnCancelarPedido.addActionListener( e -> {
            TelaCancelarPedidoSwing telaCancelar = new TelaCancelarPedidoSwing();
            telaCancelar.setVisible( true );
        } );
        getContentPane().add( btnCancelarPedido );
        btnPersonalizacao = new JButton( "Personalização de Produtos" );
        btnPersonalizacao.setFont( new Font( "Verdana", Font.PLAIN, 12 ) );
        btnPersonalizacao.addActionListener( e -> menuPersonalizacaoProdutoController.iniciar() );
        getContentPane().add( btnPersonalizacao );

        btnPesquisarPedido = new JButton( "Pesquisar Pedido" );
        btnPesquisarPedido.setFont( new Font( "Verdana", Font.PLAIN, 12 ) );

        btnPesquisarPedido.addActionListener(e -> {
            TelaDePesquisarPedidosSwing telaPesquisar = new TelaDePesquisarPedidosSwing(filaPedidoService);
            telaPesquisar.setVisible(true);
        });

        getContentPane().add( btnPesquisarPedido );

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