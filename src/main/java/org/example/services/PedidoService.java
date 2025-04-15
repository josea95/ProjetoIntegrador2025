package org.example.services;

import org.example.entities.FilaPedidoEntity;
import org.example.entities.ProdutoEntity;
import org.example.entities.ProdutoPedidoEntity;
import org.example.entities.UsuarioEntity;

import org.example.repository.FilaPedidoRepository;
import org.example.repository.ProdutoRepository;

import javax.persistence.EntityManager;

import java.time.LocalDate;
import java.time.LocalTime;

import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class PedidoService {

    private final FilaPedidoRepository pedidoRepo;
    private final ProdutoRepository produtoRepo;
    private final Scanner scanner;

    // Construtor da classe PedidoService , recebe o EntityManager e o Scanner
    public PedidoService(EntityManager em, Scanner scanner) {
        this.pedidoRepo = new FilaPedidoRepository(em); //inicializa o repositorio de Fila pedidos
        this.produtoRepo = new ProdutoRepository(em); //inicializa o repositorio de produtos
        this.scanner = scanner; // inicializa o scanner para ler as entradas do usuario
    }

    //metodo para fazer pedido
    public void fazerPedido(UsuarioEntity usuarioLogado) {
        System.out.println("Fazendo novo pedido...");
        //instanciando o objeto pedido
        FilaPedidoEntity pedido = new FilaPedidoEntity();
        pedido.setDataPedido(LocalDate.now()); // pega a data atual
        pedido.setHoraPedido(LocalTime.now()); // pega a hora atual

        pedido.setStatusPedido("Preparando..."); //status inical do pedido

        pedido.setUsuario(usuarioLogado); //associando o usuario ao pedido
        pedido.setSenhaPedido(gerarSenha()); //gerando a senha do pedido

        boolean adicionandoProdutos = true;
        while (adicionandoProdutos) {
            System.out.println("\nEscolha a categoria:");
            System.out.println("1. Marmitas");
            System.out.println("2. Bebidas");
            System.out.println("3. Porções");
            System.out.println("4. Finalizar pedido");
            System.out.print("Digite o número da categoria: ");
            String opcao = scanner.nextLine();

            String categoria;
            switch (opcao) {
                case "1":
                    categoria = "Marmitas";// define a categoria
                    break;
                case "2":
                    categoria = "Bebidas"; // define a categoria
                    break;
                case "3":
                    categoria = "Porções"; // define a categoria
                    break;
                case "4":
                    adicionandoProdutos = false; // finaliza o loop, finaliza a selecao de produtos
                    continue;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
                    continue;
            }

            if (categoria != null) { // verifica se a categoria não é nula

                List<ProdutoEntity> produtos = produtoRepo.buscarPorCategoria(categoria);// Busca os produtos da categoria selecionada

                if (produtos.isEmpty()) { // verifica se a lista de produtos está vazia
                    System.out.println("Nenhum produto encontrado nesta categoria.");
                } else { // se não estiver vazia, imprime os detalhes de cada produto
                    System.out.println("Produtos disponíveis na categoria " + categoria + ":");
                    for (int i = 0; i < produtos.size(); i++) { // itera sobre a lista de produtos
                        ProdutoEntity produto = produtos.get(i); // pega o produto da lista
                        System.out.println((i + 1) + ". " + produto.getNome() + " - R$" + produto.getPreco()); // imprime o nome e o preço do produto
                    }

                    System.out.print("Digite o número do produto que deseja adicionar ao pedido: ");
                    int escolha = scanner.nextInt(); // lê a escolha do usurio
                    scanner.nextLine(); // Consumir a quebra de linha

                    if (escolha < 1 || escolha > produtos.size()) { // verifica se a escolha do usuario é válida
                        System.out.println("Opção inválida.");
                    } else {
                        ProdutoEntity produtoEscolhido = produtos.get(escolha - 1); // pega o produto escolhido
                        ProdutoPedidoEntity produtoPedido = new ProdutoPedidoEntity(); // cria um novo produtoPedido

                        produtoPedido.setPedido(pedido); // associa o pedido
                        produtoPedido.setProduto(produtoEscolhido); // associa o produto ao produtoPedido
                        pedido.getProdutos().add(produtoPedido); // adiciona o produtoPedido na lista de produtos do pedido

                        System.out.println("Produto adicionado: " + produtoEscolhido.getNome()); // imprime o nome do produto adicionado
                    }
                }
            }
        }

        System.out.print("Deseja adicionar uma observação ao pedido? (s/n): ");
        String op = scanner.nextLine(); // lê a opção do usuario
        if (op.equalsIgnoreCase("s")) { // ignora se for maiúscula ou minúscula, se o usuario quiser adicionar uma observacao
            System.out.print("Digite a observação: ");
            pedido.setObservacao(scanner.nextLine()); // adiciona a observacao no pedido
        }

        // Confirmação final do pedido
        System.out.println("\nPedido concluído com sucesso? 1 - sim | 2 - nao");
        String confirmacao = scanner.nextLine(); // lê a confirmação do usuario
        if (confirmacao.equals("1")) { // se o usuario confirmar o pedido
            pedido.setStatusPedido("Preparando...");

            pedidoRepo.salvar(pedido); //salva o pedido no banco de dados
            System.out.println("Pedido concluído com sucesso! Histórico atualizado.");
        } else { // se o usuário não confirmar o pedido
            System.out.println("Pedido cancelado. Nenhum pedido foi armazenado.");
        }
    }

    //metodo para cancelar pedido
    public void cancelarPedido() {
        System.out.print("Digite a senha do pedido a cancelar: ");
        String senha = scanner.nextLine();

        // Busca o pedido pelo número da senha
        FilaPedidoEntity pedido = pedidoRepo.buscarPorSenha(senha);

        if (pedido == null) { // verifica se o pedido existe
            System.out.println("Pedido não encontrado.");
        } else if (!pedido.getStatusPedido().equalsIgnoreCase("na fila")) { //verifica se o pedido está na fila, ou outro status *
            System.out.println("O pedido já está em preparo ou finalizado e não pode ser cancelado.");
        } else {
            pedidoRepo.deletar(pedido); // remove o pedido do banco de dados
            System.out.println("Pedido cancelado com sucesso.");
        }
    }

    public void pesquisarPedido() {
        System.out.print("Digite a senha do pedido: ");
        String senha = scanner.nextLine();

        FilaPedidoEntity pedido = pedidoRepo.buscarPorSenha(senha); // Busca o pedido pelo número da senha

        if (pedido == null) { // verifica se o pedido existe
            System.out.println("Pedido não encontrado.");
        } else {
            System.out.println("Senha: " + pedido.getSenhaPedido()); // exibe a senha do pedido
            System.out.println("Produtos:");
            for (ProdutoPedidoEntity produtoPedido : pedido.getProdutos()) { // itera sobre a lista de produtos do pedido
                ProdutoEntity produto = produtoPedido.getProduto(); // pega o produto do produtoPedido
                System.out.println(" - " + produto.getNome() + " - R$" + produto.getPreco());
            }
            System.out.println("Data: " + pedido.getDataPedido());
            System.out.println("Status: " + pedido.getStatusPedido());
            System.out.println("-----------------------------");
        }
    }
    
    //Gerador de senha aleatorio pra pedido
    private String gerarSenha() {
        Random rand = new Random(); // cria um objeto Random -> um gerador de números aleatórios
        int numero = rand.nextInt(900) + 100; // gera número de 100 a 999
        return String.valueOf(numero); // converte o número para String
    }

}





