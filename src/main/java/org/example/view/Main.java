package org.example.view;

import org.example.Swing.MenuPrincipalSwing;
import org.example.Swing.TelaLoginSwing;
import org.example.controller.*;
import org.example.model.entities.UsuarioEntity;
import org.example.model.repository.ProdutoRepository;
import org.example.model.services.*;
import org.example.model.util.CustomizerFactory;
import org.example.model.repository.ProdutoHistoricoPedidoRepository;

import javax.persistence.EntityManager;
import javax.swing.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new TelaLoginSwing().setVisible(true);
        });


        EntityManager em = CustomizerFactory.getEntityManager();
        Scanner scanner = new Scanner( System.in );

        // Services
        UsuarioService usuarioService = new UsuarioService( em );
        PedidoService pedidoService = new PedidoService( em );
        ProdutoService produtoService = new ProdutoService( new ProdutoRepository( em ) );
        FilaPedidoService filaPedidoService = new FilaPedidoService( em );
        RelatorioService relatorioService = new RelatorioService( new ProdutoHistoricoPedidoRepository( em ) );
        HistoricoPedidoService historicoPedidoService = new HistoricoPedidoService( em );//Adicionando o serviço de histórico de pedidos

        // Views
        UsuarioView usuarioView = new UsuarioView( scanner );
        PedidoView pedidoView = new PedidoView( scanner, pedidoService );
        ProdutoView produtoView = new ProdutoView( scanner );
        FilaPedidoView filaPedidoView = new FilaPedidoView( filaPedidoService, scanner );
        MenuPrincipalView menuView = new MenuPrincipalView( scanner );
        RelatorioView relatorioView = new RelatorioView( relatorioService );

        // Controllers
        UsuarioController usuarioController = new UsuarioController( usuarioService, usuarioView );
        PedidoController pedidoController = new PedidoController(pedidoService);
        ProdutoController produtoController = new ProdutoController(produtoService, produtoView, scanner);
        FilaPedidoController filaPedidoController = new FilaPedidoController( filaPedidoService, filaPedidoView, scanner );
        RelatorioController relatorioController = new RelatorioController( relatorioView );
        //Criei o controller de personalização de produtos
        MenuPersonalizacaoProdutoController menuPersonalizacaoProdutoController = new MenuPersonalizacaoProdutoController( produtoService );
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

        //Adicionando mais parametros ao construtor do MenuPrincipalSwing
        MenuPrincipalSwing menuSwing = new MenuPrincipalSwing( pedidoService, usuarioLogado,
                filaPedidoService, historicoPedidoService, relatorioService );

        MenuPrincipalController menuController = new MenuPrincipalController(
                menuSwing,
                menuView,
                pedidoController,
                produtoController,
                filaPedidoController,
                usuarioLogado,
                relatorioController,
                pedidoService,
                filaPedidoService,
                relatorioService,
                menuPersonalizacaoProdutoController//Adiconando o controller de personalização de produtos

        );

        menuController.executar();

        // Encerramento
        scanner.close();
        em.close();
        CustomizerFactory.fechar();
    }
}

// //ESTOU TENSTANDO POR ENQUANTO, para chamar a tela de login Swing, esta funcionando mais não sei se é a melhor forma de fazer isso, depois posso tentar melhorar
//package org.example.view;
//
//import org.example.Swing.TelaLoginSwing;
//
//
//public class Main {
//    public static void main(String[] args) {
//        TelaLoginSwing.main(args);
//    }
//}
//
