package org.example.Swing;

import org.example.controller.ProdutoSwingController;
import org.example.model.entities.UsuarioEntity;
import org.example.model.repository.ProdutoRepository;
import org.example.model.services.PedidoService;
import org.example.model.services.ProdutoService;
import org.example.model.util.CustomizerFactory;

import javax.persistence.EntityManager;
import javax.swing.*;
import java.awt.*;

public class MenuPrincipalSwing extends JFrame {

    private final PedidoService pedidoService;
    private final UsuarioEntity usuarioLogado;

    public MenuPrincipalSwing(PedidoService pedidoService, UsuarioEntity usuarioLogado) {
        this.pedidoService = pedidoService;
        this.usuarioLogado = usuarioLogado;

        inicializarTela();
    }

    private void inicializarTela() {
        setTitle("Menu Principal");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 500);
        setLocationRelativeTo(null);
        getContentPane().setLayout(new GridLayout(4, 2, 3, 3));
        getContentPane().setBackground(Color.WHITE);

        EntityManager em = CustomizerFactory.getEntityManager();
        ProdutoRepository produtoRepository = new ProdutoRepository(em);
        ProdutoService produtoService = new ProdutoService(produtoRepository);
        ProdutoSwingController produtoController = new ProdutoSwingController(produtoService);

        JButton fazerPedidoButton = new JButton("1. Fazer Pedido");
        fazerPedidoButton.setFont(new Font("Verdana", Font.PLAIN, 12));
        fazerPedidoButton.addActionListener(e -> new FazerPedidoSwing(pedidoService, usuarioLogado));
        getContentPane().add(fazerPedidoButton);

        JButton btnCancelarPedido = new JButton("2. Cancelar Pedido");
        getContentPane().add(btnCancelarPedido);

        JButton btnVerFila = new JButton("3. Ver Fila de Pedidos");
        getContentPane().add(btnVerFila);

        JButton btnPersonalizacao = new JButton("4. Personalização de Produtos");
        btnPersonalizacao.setFont(new Font("Verdana", Font.PLAIN, 12));
        btnPersonalizacao.addActionListener(e -> produtoController.iniciar());
        getContentPane().add(btnPersonalizacao);

        JButton btnPesquisarPedido = new JButton("5. Pesquisar Pedido");
        getContentPane().add(btnPesquisarPedido);

        JButton btnHistorico = new JButton("6. Ver Histórico de Pedidos");
        getContentPane().add(btnHistorico);

        JButton btnRelatorio = new JButton("7. Relatório de Vendas");
        getContentPane().add(btnRelatorio);

        JButton btnSair = new JButton("8. Sair");
        btnSair.addActionListener(e -> System.exit(0));
        getContentPane().add(btnSair);

        setVisible(true);
    }
    public void exibirMenu() {
        System.out.println("\n===== MENU PRINCIPAL =====");
        System.out.println("1. Fazer Pedido");
        System.out.println("2. Cancelar Pedido");
        System.out.println("3. Ver Fila de Pedidos");
        System.out.println("4. Personalização de Produtos");
        System.out.println("5. Pesquisar Pedido");
        System.out.println("6. Ver Histórico de Pedidos");
        System.out.println("7. Relatório de Vendas");
        System.out.println("8. Sair");
    }
}
