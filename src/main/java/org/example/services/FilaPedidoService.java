package org.example.services;

import org.example.entities.FilaPedidoEntity;
import org.example.entities.ProdutoEntity;
import org.example.entities.ProdutoPedidoEntity;
import org.example.entities.UsuarioEntity;
import org.example.repository.FilaPedidoRepository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Scanner;

public class FilaPedidoService {

    private final FilaPedidoRepository filaRepo;
    private final Scanner scanner;
    private final StringBuilder produtosComprados;

    // Construtor que inicializa o repositório, o Scanner e o StringBuilder para os produtos
    public FilaPedidoService(EntityManager em, Scanner scanner) {
        this.filaRepo = new FilaPedidoRepository(em);
        this.scanner = scanner;
        this.produtosComprados = new StringBuilder();
    }

    public void listarFilaPedidos() {
        List<FilaPedidoEntity> pedidos = filaRepo.listarTodos();

        if (pedidos.isEmpty()) {
            System.out.println("Nenhum pedido na fila.");
        } else {
            for (FilaPedidoEntity pedido : pedidos) {
                System.out.println("Senha: " + pedido.getSenhaPedido());
                System.out.println("Produto(s):");
                if (pedido.getProdutos().isEmpty()) {
                    System.out.println("Nenhum produto encontrado.");
                } else {
                    for (ProdutoPedidoEntity produtoPedido : pedido.getProdutos()) {
                        ProdutoEntity produto = produtoPedido.getProduto();
                        System.out.println(" - " + produto.getNome() + " - R$" + produto.getPreco());
                    }
                }
                System.out.println("Data: " + pedido.getDataPedido());
                System.out.println("Status: " + pedido.getStatusPedido());
                System.out.println("-----------------------------");
            }
        }
    }

    // Metodo para pesquisar um pedido por senha e exibir seus detalhes
    public void pesquisarPedido() {
        System.out.print( "Digite a senha do pedido: " );
        String senha = scanner.nextLine();

        // Busca o pedido pelo número da senha
        FilaPedidoEntity pedido = filaRepo.buscarPorSenha( senha );

        if (pedido == null) {
            // Se o pedido não for encontrado, exibe uma mensagem
            System.out.println( "Pedido não encontrado." );
        } else {
            // Exibe os detalhes do pedido encontrado
            System.out.println( "Senha: " + pedido.getSenhaPedido() );
            System.out.println( "Produtos:" );
            // Percorre a lista de produtos do pedido e exibe os detalhes de cada um
            for (ProdutoPedidoEntity produtoPedido : pedido.getProdutos()) {
                ProdutoEntity produto = produtoPedido.getProduto();
                System.out.println( " - " + produto.getNome() + " - R$" + produto.getPreco() );
            }
            System.out.println( "Data: " + pedido.getDataPedido() );
            System.out.println( "Status: " + pedido.getStatusPedido() );
            System.out.println( "-----------------------------" );
        }
    }

    // Metodo para cancelar um pedido usando a senha informada pelo usuário
    public void cancelarPedido() {
        System.out.print("Digite a senha do pedido a cancelar: ");
        String senha = scanner.nextLine();

        // Busca o pedido pelo número da senha
        FilaPedidoEntity pedido = filaRepo.buscarPorSenha(senha);

        if (pedido == null) {
            // Se o pedido não for encontrado, exibe uma mensagem
            System.out.println("Pedido não encontrado.");     //o que tiver aqui sera deletado conforme o argumento
            // Se o pedido não estiver com status 'na fila', não permite o cancelamento
            System.out.println("O pedido já está em preparo ou finalizado e não pode ser cancelado.");
        } else {
            // Remove o pedido do banco de dados e informa o sucesso do cancelamento
            filaRepo.deletar(pedido);
            System.out.println("Pedido cancelado com sucesso.");

        }
    }
}