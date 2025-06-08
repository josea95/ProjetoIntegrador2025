package org.example.view;

import org.example.controller.*;
import org.example.model.entities.UsuarioEntity;
import org.example.model.repository.ProdutoHistoricoPedidoRepository;
import org.example.model.repository.ProdutoRepository;
import org.example.model.services.*;
import org.example.model.util.CustomizerFactory;

import javax.persistence.EntityManager;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Inicializa os recursos
        EntityManager em = CustomizerFactory.getEntityManager();
        Scanner scanner = new Scanner(System.in);

        // === SERVICES ===
        UsuarioService usuarioService = new UsuarioService(em);
        PedidoService pedidoService = new PedidoService(em);
        ProdutoService produtoService = new ProdutoService(new ProdutoRepository(em));
        FilaPedidoService filaPedidoService = new FilaPedidoService(em);
        RelatorioService relatorioService = new RelatorioService(new ProdutoHistoricoPedidoRepository(em));

        // === VIEWS ===
        UsuarioView usuarioView = new UsuarioView(scanner);
        PedidoView pedidoView = new PedidoView(scanner, pedidoService);
        ProdutoView produtoView = new ProdutoView(scanner);
        FilaPedidoView filaPedidoView = new FilaPedidoView(filaPedidoService, scanner);
        MenuPrincipalView menuView = new MenuPrincipalView(scanner);
        RelatorioView relatorioView = new RelatorioView(relatorioService);

        // === CONTROLLERS ===
        UsuarioController usuarioController = new UsuarioController(usuarioService, usuarioView);
        PedidoController pedidoController = new PedidoController(pedidoService, pedidoView);
        ProdutoController produtoController = new ProdutoController(produtoService, produtoView, scanner);
        FilaPedidoController filaPedidoController = new FilaPedidoController(filaPedidoService, filaPedidoView, scanner);
        RelatorioController relatorioController = new RelatorioController(relatorioView);

        // === LOGIN ===
        UsuarioEntity usuarioLogado = usuarioController.realizarLogin();

        // === MENU PRINCIPAL ===
        MenuPrincipalController menuController = new MenuPrincipalController(
                menuView,
                pedidoController,
                produtoController,
                filaPedidoController,
                usuarioLogado,
                relatorioController,
                filaPedidoService,
                relatorioService
        );
        menuController.executar();

        // === FINALIZAÇÃO ===
        scanner.close();
        em.close();
        CustomizerFactory.fechar();
    }
}
