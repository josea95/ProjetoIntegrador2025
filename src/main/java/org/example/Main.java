package org.example;

import org.example.entities.UsuarioEntity;
import org.example.repository.ProdutoRepository;
import org.example.services.ProdutoService;
import org.example.services.UsuarioService;
import org.example.util.CustomizerFactory;

import javax.persistence.EntityManager;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        EntityManager em = CustomizerFactory.getEntityManager();
        Scanner scanner = new Scanner(System.in);

        UsuarioService usuarioService = new UsuarioService(em); // ✅ Correto, só uma vez
        ProdutoService produtoService = new ProdutoService(new ProdutoRepository(em));

        UsuarioEntity usuarioLogado = null; // ✅ Só uma vez

        // Solicita login antes de exibir o menu
        while (usuarioLogado == null) {
            System.out.print("Login: ");
            String login = scanner.nextLine();
            System.out.print("Senha: ");
            String senha = scanner.nextLine();

            usuarioLogado = usuarioService.login(login, senha);
            if (usuarioLogado != null) {
                System.out.println("✅ Login realizado com sucesso! Bem-vindo, " + usuarioLogado.getNome());
            } else {
                System.out.println("❌ Login ou senha incorretos. Tente novamente.");
            }
        }

        boolean executando = true;

        while (executando) {
            System.out.println("\n===== MENU PRINCIPAL =====");
            System.out.println("1. Fazer Pedido");
            System.out.println("2. Cancelar Pedido");
            System.out.println("3. Ver Fila de Pedidos");
            System.out.println("4. Cadastrar Produto");
            System.out.println("5. Pesquisar Pedido");
            System.out.println("6. Ver Histórico de Pedidos");
            System.out.println("7. Sair");

            System.out.print("Escolha uma opção: ");
            String opcao = scanner.nextLine();

            switch (opcao) {
                case "1":
                    // fazerPedido();
                    break;
                case "2":
                    // cancelarPedido();
                    break;
                case "3":
                    // listarFilaPedidos();
                    break;
                case "4":
                    produtoService.cadastrarProduto(scanner, new ProdutoRepository(em));
                    break;
                case "5":
                    // pesquisarPedido();
                    break;
                case "6":
                    // pedidoService.verHistoricoPedidos(usuarioLogado);
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
