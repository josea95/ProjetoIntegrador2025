package org.example;

import org.example.repository.ProdutoRepository;

import org.example.services.ProdutoService;

import org.example.util.CustomizerFactory;

import javax.persistence.EntityManager;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        EntityManager em = CustomizerFactory.getEntityManager();
        Scanner scanner = new Scanner(System.in);

        ProdutoService produtoService = new ProdutoService(new ProdutoRepository(em));


        boolean executando = true;

        while (executando) {
            System.out.println("\n===== MENU PRINCIPAL =====");
            System.out.println("1. Fazer Pedido");
            System.out.println("2. Cancelar Pedido ");
            System.out.println("3. Ver Fila de Pedidos ");
            System.out.println("4. Cadastrar Produto");
            System.out.println("5. Pesquisar Pedido");
            System.out.println("6. Ver Histórico de Pedidos");
            System.out.println("7. Sair");
            System.out.print("Escolha uma opção: ");
            String opcao = scanner.nextLine();

            switch (opcao) {
                case "1":
//                   //fazerPedido();
                    break;
                case "2":
                    //cancelarPedido();
                    break;
                case "3":
                    //listarFilaPedidos();
                    break;
                case "4":
                    produtoService.cadastrarProduto(scanner, new ProdutoRepository(em));
                    break;
                case "5":
                    //pesquisarPedido();
                    break;
                case "6":
                 //  pedidoService.verHistoricoPedidos( usuario  );
                    break;
                case "7":
                    executando = false;
                    System.out.println("Encerrando o sistema...");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }

        scanner.close();
        em.close();
        CustomizerFactory.fechar();
    }
}