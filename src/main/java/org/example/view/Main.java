package org.example.view;

import org.example.controller.*;
import org.example.model.entities.UsuarioEntity;
import org.example.model.repository.ProdutoRepository;
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

        // Views
        UsuarioView usuarioView = new UsuarioView(scanner);
        PedidoView pedidoView = new PedidoView(scanner, pedidoService);
        ProdutoView produtoView = new ProdutoView(scanner, produtoService);
        FilaPedidoView filaPedidoView = new FilaPedidoView(filaPedidoService, scanner);
        MenuPrincipalView menuView = new MenuPrincipalView(scanner);

        // Controllers
        UsuarioController usuarioController = new UsuarioController(usuarioService, usuarioView);
        PedidoController pedidoController = new PedidoController(pedidoService, pedidoView);
        ProdutoController produtoController = new ProdutoController(produtoService, produtoView);
        FilaPedidoController filaPedidoController = new FilaPedidoController(filaPedidoService, filaPedidoView, scanner);

        // Login
        UsuarioEntity usuarioLogado = usuarioController.realizarLogin();

        // Menu Principal
        MenuPrincipalController menuController = new MenuPrincipalController(
                menuView,
                pedidoController,
                produtoController,
                filaPedidoController,
                usuarioLogado
        );
        //Chama o menu principal
        menuController.executar();

        // Encerramento
        scanner.close();
        em.close();
        CustomizerFactory.fechar();
    }
}
