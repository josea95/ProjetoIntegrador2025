package org.example.view;

import org.example.controller.PedidoController;
import org.example.controller.ProdutoController;
import org.example.controller.UsuarioController;
import org.example.model.entities.UsuarioEntity;
import org.example.model.repository.ProdutoRepository;
import org.example.model.services.FilaPedidoService;
import org.example.model.services.PedidoService;
import org.example.model.services.ProdutoService;
import org.example.model.services.UsuarioService;
import org.example.model.util.CustomizerFactory;

import javax.persistence.EntityManager;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        EntityManager em = CustomizerFactory.getEntityManager();
        Scanner scanner = new Scanner( System.in );

        UsuarioService usuarioService = new UsuarioService(em);
        UsuarioView usuarioView = new UsuarioView(scanner);
        UsuarioController usuarioController = new UsuarioController(usuarioService, usuarioView);



        PedidoService pedidoService = new PedidoService( em );
        FilaPedidoService filaPedidoService = new FilaPedidoService( em, scanner );

        PedidoView pedidoView = new PedidoView( scanner, pedidoService );
        PedidoController pedidoController = new PedidoController( pedidoService, pedidoView );


        ProdutoService produtoService = new ProdutoService(new ProdutoRepository(em));
        ProdutoView produtoView = new ProdutoView(scanner, produtoService);
        ProdutoController produtoController = new ProdutoController(produtoService, produtoView);


        UsuarioEntity usuarioLogado = usuarioController.realizarLogin();
        boolean executando = true;

        while (executando) {
            System.out.println( "\n===== MENU PRINCIPAL =====" );
            System.out.println( "1. Fazer Pedido" );
            System.out.println( "2. Cancelar Pedido" );
            System.out.println( "3. Ver Fila de Pedidos" );
            System.out.println( "4. Cadastrar Produto" );
            System.out.println( "5. Pesquisar Pedido" );
            System.out.println( "6. Ver Histórico de Pedidos" );
            System.out.println( "7. Sair" );

            System.out.print( "Escolha uma opção: " );
            String opcao = scanner.nextLine();

            switch (opcao) {
                case "1":
                    pedidoController.iniciarPedido( usuarioLogado );
                    break;
                case "2":
                    filaPedidoService.cancelarPedido();
                    break;
                case "3":
                    filaPedidoService.listarFilaPedidos();
                    break;
                case "4":
                    produtoController.iniciarCadastro();
                    break;
                case "5":
                    filaPedidoService.pesquisarPedido();
                    break;
                case "6":
                    filaPedidoService.verHistoricoPedidos( usuarioLogado );
                    break;
                case "7":
                    executando = false;
                    System.out.println( "Encerrando o sistema..." );
                    break;
                default:
                    System.out.println( "Opção inválida. Tente novamente." );
            }
        }

        scanner.close();
        em.close();
        CustomizerFactory.fechar();
    }
}
