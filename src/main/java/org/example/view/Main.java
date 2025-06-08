package org.example.view;

import org.example.controller.*;
import org.example.model.entities.UsuarioEntity;
import org.example.model.repository.ProdutoRepository;
import org.example.model.repository.ProdutoHistoricoPedidoRepository;
import org.example.model.services.*;
import org.example.model.util.CustomizerFactory;

import javax.persistence.EntityManager;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        EntityManager em = CustomizerFactory.getEntityManager();
        Scanner scanner = new Scanner(System.in);

        // Services
        UsuarioService usuarioService = new UsuarioService(em);
        PedidoService pedidoService = new PedidoService(em);
        ProdutoService produtoService = new ProdutoService(new ProdutoRepository(em));
        FilaPedidoService filaPedidoService = new FilaPedidoService(em);
        RelatorioService relatorioService = new RelatorioService(new ProdutoHistoricoPedidoRepository(em));

        // Views
        UsuarioView usuarioView = new UsuarioView(scanner);
        PedidoView pedidoView = new PedidoView(scanner, pedidoService);
        ProdutoView produtoView = new ProdutoView(scanner);
        FilaPedidoView filaPedidoView = new FilaPedidoView(filaPedidoService, scanner);
        MenuPrincipalView menuView = new MenuPrincipalView(scanner);
        RelatorioView relatorioView = new RelatorioView(relatorioService);

        // Inicia tela de login Swing
        TelaLoginSwing tela = new TelaLoginSwing(usuarioService);
        tela.setLocationRelativeTo(null);
        tela.setVisible(true);

        // Aguarda o usuário fechar a tela de login
        while (tela.isVisible()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Recupera o usuário logado da tela de login
        UsuarioEntity usuarioLogado = tela.getUsuarioLogado();

        if (usuarioLogado == null) {
            System.out.println("Login cancelado ou falhou.");
            scanner.close();
            em.close();
            CustomizerFactory.fechar();
            return;
        }

        // Controllers
        UsuarioController usuarioController = new UsuarioController(usuarioService, usuarioView);
        PedidoController pedidoController = new PedidoController(pedidoService, pedidoView);
        ProdutoController produtoController = new ProdutoController(produtoService, produtoView, scanner);
        FilaPedidoController filaPedidoController = new FilaPedidoController(filaPedidoService, filaPedidoView, scanner);
        RelatorioController relatorioController = new RelatorioController(relatorioView);

        // Menu Principal
        MenuPrincipalController menuController = new MenuPrincipalController(
                menuView,
                pedidoController,
                produtoController,
                filaPedidoController,
                usuarioLogado,
                relatorioController
        );
        menuController.executar();

        // Encerramento
        scanner.close();
        em.close();
        CustomizerFactory.fechar();
    }
}
