package org.example.view;

import org.example.controller.*;
import org.example.model.entities.UsuarioEntity;
import org.example.model.repository.ProdutoRepository;
import org.example.model.services.*;
import org.example.model.util.CustomizerFactory;
import org.example.model.repository.ProdutoHistoricoPedidoRepository;


import javax.persistence.EntityManager;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        EntityManager em = CustomizerFactory.getEntityManager();
        Scanner scanner = new Scanner( System.in );

        // Services
        UsuarioService usuarioService = new UsuarioService( em );
        PedidoService pedidoService = new PedidoService( em );
        ProdutoService produtoService = new ProdutoService( new ProdutoRepository( em ) );
        FilaPedidoService filaPedidoService = new FilaPedidoService( em );
        RelatorioService relatorioService = new RelatorioService(new ProdutoHistoricoPedidoRepository(em));

        // Views
        UsuarioView usuarioView = new UsuarioView( scanner );
        PedidoView pedidoView = new PedidoView( scanner, pedidoService );
        ProdutoView produtoView = new ProdutoView( scanner );
        FilaPedidoView filaPedidoView = new FilaPedidoView( filaPedidoService, scanner );
        MenuPrincipalView menuView = new MenuPrincipalView( scanner );
        RelatorioView relatorioView = new RelatorioView(relatorioService);

        // Controllers
        UsuarioController usuarioController = new UsuarioController( usuarioService, usuarioView );
        PedidoController pedidoController = new PedidoController( pedidoService, pedidoView );
        ProdutoController produtoController = new ProdutoController(produtoService, produtoView, scanner);
        FilaPedidoController filaPedidoController = new FilaPedidoController( filaPedidoService, filaPedidoView, scanner );
        RelatorioController relatorioController = new RelatorioController(relatorioView);
        /* Login
         * - Solicita ao controller que execute o login,
         *   validando as informações inseridas pelo usuário.
         * - Se as informações forem válidas, retorna uma entidade do tipo UsuarioEntity
         *   representando o usuário autenticado no sistema.
         */
        UsuarioEntity usuarioLogado = usuarioController.realizarLogin();

        /* Menu Principal
        * - Criando uma instanciação do objeto MenuPrincipalController
        *   passando as dependências necessárias para controlar o menu principal
        */
        MenuPrincipalController menuController = new MenuPrincipalController(
                menuView,
                pedidoController,
                produtoController,
                filaPedidoController,
                usuarioLogado,
                relatorioController
        );
        //Chama o menu principal
        menuController.executar();

        // Encerramento
        scanner.close();
        em.close();
        CustomizerFactory.fechar();
    }
}
