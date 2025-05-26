package org.example.model.services;

import org.example.model.entities.*;

import org.example.model.enums.StatusPedido;

import org.example.model.repository.FilaPedidoRepository;
import org.example.model.repository.HistoricoPedidoRepository;
import org.example.model.repository.ProdutoHistoricoPedidoRepository;
import org.example.model.repository.ProdutoRepository;

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
    private final HistoricoPedidoRepository historicoRepo;
    private final ProdutoHistoricoPedidoRepository produtoHistoricoRepo;


    // Construtor da classe PedidoService , recebe o EntityManager e o Scanner
    public PedidoService(EntityManager em, Scanner scanner) {
        this.pedidoRepo = new FilaPedidoRepository( em ); //inicializa o repositorio de Fila pedidos
        this.produtoRepo = new ProdutoRepository( em ); //inicializa o repositorio de produtos
        this.scanner = scanner; // inicializa o scanner para ler as entradas do usuario
        this.historicoRepo = new HistoricoPedidoRepository( em ); //inicializa o repositorio de historico de pedidos
        this.produtoHistoricoRepo = new ProdutoHistoricoPedidoRepository( em ); //inicializa o repositorio de produtos do historico de pedidos

    }

    //metodo para fazer pedido
    public void fazerPedido(UsuarioEntity usuarioLogado) {
        System.out.println( "Fazendo novo pedido..." );
        //instanciando o objeto pedido
        FilaPedidoEntity pedido = new FilaPedidoEntity();
        pedido.setDataPedido( LocalDate.now() ); // pega a data atual
        pedido.setHoraPedido( LocalTime.now() ); // pega a hora atual

        pedido.setStatusPedido( StatusPedido.FILA ); //status inical do pedido

        pedido.setUsuario( usuarioLogado ); //associando o usuario ao pedido
        pedido.setSenhaPedido( gerarSenha() ); //gerando a senha do pedido

        boolean adicionandoProdutos = true;
        boolean pedidoCancelado = false;

        while (adicionandoProdutos) {
            System.out.println( "\nEscolha a categoria:" );
            System.out.println( "1. Marmitas" );
            System.out.println( "2. Bebidas" );
            System.out.println( "3. Porções" );
            System.out.println( "4. Finalizar pedido" );
            System.out.println( "0. Cancelar pedido" );
            System.out.print( "Digite o número da categoria: " );
            String opcao = scanner.nextLine();
            if (opcao.equals( "0" )) {
                pedidoCancelado = true;
                break;
            }

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
                    System.out.println( "Opção inválida. Tente novamente." );
                    continue;
            }

            if (categoria != null) { // verifica se a categoria não é nula

                List<ProdutoEntity> produtos = produtoRepo.buscarPorCategoria( categoria );// Busca os produtos da categoria selecionada

                if (produtos.isEmpty()) { // verifica se a lista de produtos está vazia
                    System.out.println( "Nenhum produto encontrado nesta categoria." );
                } else { // se não estiver vazia, imprime os detalhes de cada produto
                    System.out.println( "Produtos disponíveis na categoria " + categoria + ":" );
                    for (int i = 0; i < produtos.size(); i++) { // itera sobre a lista de produtos
                        ProdutoEntity produto = produtos.get( i ); // pega o produto da lista
                        System.out.println( (i + 1) + ". " + produto.getNome() + " - R$" + produto.getPreco() ); // imprime o nome e o preço do produto
                    }

                    System.out.print( "Digite o número do produto que deseja adicionar ao pedido (ou 0 para voltar ao menu de categorias) : " );
                    int escolha = scanner.nextInt(); // lê a escolha do usurio
                    scanner.nextLine(); // Consumir a quebra de linha

                    if (escolha == 0) {
                        // Volta para o menu de categorias sem cancelar o pedido
                        continue;
                    }

                    if (escolha < 1 || escolha > produtos.size()) { // verifica se a escolha do usuario é válida
                        System.out.println( "Opção inválida." );
                    } else {
                        ProdutoEntity produtoEscolhido = produtos.get( escolha - 1 ); // pega o produto escolhido
                        ProdutoPedidoEntity produtoPedido = new ProdutoPedidoEntity(); // cria um novo produtoPedido

                        produtoPedido.setPedido( pedido ); // associa o pedido
                        produtoPedido.setProduto( produtoEscolhido ); // associa o produto ao produtoPedido
                        pedido.getProdutos().add( produtoPedido ); // adiciona o produtoPedido na lista de produtos do pedido

                        System.out.println( "Produto adicionado: " + produtoEscolhido.getNome() ); // imprime o nome do produto adicionado
                    }
                }
            }
        }


        if (pedidoCancelado) {
            System.out.println( "Pedido cancelado. Nenhum pedido foi armazenado." );
            return;
        }

        System.out.print( "Deseja adicionar uma observação ao pedido? (s/n): " );
        String op = scanner.nextLine(); // lê a opção do usuario
        if (op.equalsIgnoreCase( "s" )) { // ignora se for maiúscula ou minúscula, se o usuario quiser adicionar uma observacao
            System.out.print( "Digite a observação: " );
            pedido.setObservacao( scanner.nextLine() ); // adiciona a observacao no pedido
        }

        System.out.println( "\nPedido concluído com sucesso? 1 - sim | 2 - nao" );
        String confirmacao = scanner.nextLine(); // lê a confirmação do usuario
        if (confirmacao.equals( "1" )) { // se o usuario confirmar o pedido
            pedido.setStatusPedido( StatusPedido.FILA );
            pedidoRepo.salvar( pedido );
            System.out.println( "Pedido com status 'FILA' salvo. Histórico atualizado." );

            // Mudança automática de status após alguns minutos
            new Thread( () -> {
                try {
                    Thread.sleep( 1 * 60 * 100 ); // Espera 1 minuto

                    FilaPedidoEntity pedidoAtual = pedidoRepo.buscarPorSenha( pedido.getSenhaPedido() );
                    if (pedidoAtual != null && pedidoAtual.getStatusPedido() == StatusPedido.FILA) {
                        pedidoAtual.setStatusPedido( StatusPedido.PREPARANDO );
                        pedidoRepo.atualizar( pedidoAtual );
                        System.out.println( "Pedido " + pedidoAtual.getSenhaPedido() + " agora está PREPARANDO." );
                    } else {
                        return; // Sai da thread se foi cancelado ou já alterado
                    }

                    Thread.sleep( 1 * 60 * 100 ); // Espera mais tempo

                    pedidoAtual = pedidoRepo.buscarPorSenha( pedido.getSenhaPedido() );
                    if (pedidoAtual != null && pedidoAtual.getStatusPedido() == StatusPedido.PREPARANDO) {
                        pedidoAtual.setStatusPedido( StatusPedido.FINALIZADO );
                        pedidoRepo.atualizar( pedidoAtual );
                        System.out.println( "Pedido " + pedidoAtual.getSenhaPedido() + " agora está FINALIZADO." );

                        // Cria e salva o histórico
                        HistoricoPedidoEntity historico = new HistoricoPedidoEntity();
                        historico.setSenhaPedido( pedidoAtual.getSenhaPedido() );
                        historico.setDataPedido( pedidoAtual.getDataPedido() );
                        historico.setHoraPedido( pedidoAtual.getHoraPedido() );
                        historico.setStatusPedido( StatusPedido.FINALIZADO );
                        historico.setObservacao( pedidoAtual.getObservacao() );
                        historico.setUsuario( pedidoAtual.getUsuario() );

                        historicoRepo.salvar( historico );

                        // Salva os produtos do pedido no histórico
                        if (pedidoAtual.getProdutos() != null) {
                            for (ProdutoPedidoEntity produtoPedido : pedidoAtual.getProdutos()) {
                                ProdutoHistoricoPedidoEntity prodHist = new ProdutoHistoricoPedidoEntity();
                                prodHist.setHistoricoPedido( historico );
                                prodHist.setProduto( produtoPedido.getProduto() );
                                produtoHistoricoRepo.salvar( prodHist );
                            }
                        }
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt(); // marcar a thread como interrompida/não "ignorar" a interrupção.
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace(); //captura qualquer outra exceção/erro
                }
            } ).start();


        } else {
            System.out.println( "Pedido cancelado. Nenhum pedido foi armazenado." );
        }

    }

    //Gerador de senha aleatorio pra pedido
    private String gerarSenha() {
        Random rand = new Random(); // cria um objeto Random -> um gerador de números aleatórios
        int numero = rand.nextInt( 900 ) + 100; // gera número de 100 a 999
        return String.valueOf( numero ); // converte o número para String
    }

}
