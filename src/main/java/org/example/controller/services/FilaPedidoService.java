package org.example.controller.services;

import org.example.model.entities.FilaPedidoEntity;
import org.example.model.entities.ProdutoEntity;
import org.example.model.entities.ProdutoPedidoEntity;
import org.example.model.entities.UsuarioEntity;

import org.example.model.repository.FilaPedidoRepository;

import org.example.model.enums.StatusPedido;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Scanner;

public class FilaPedidoService {

    private final FilaPedidoRepository filaRepo;
    private final Scanner scanner;
    private final StringBuilder produtosComprados;

    // Construtor que inicializa o repositório, o Scanner e o StringBuilder para os produtos
    public FilaPedidoService(EntityManager em, Scanner scanner) {
        this.filaRepo = new FilaPedidoRepository( em );
        this.scanner = scanner;
        this.produtosComprados = new StringBuilder();
    }

// Metodo atualizado para listar apenas pedidos com status FILA ou PREPARANDO
public void listarFilaPedidos() {
    List<FilaPedidoEntity> pedidos = filaRepo.listarTodos();
    boolean encontrou = false;

    if (pedidos.isEmpty()) {
        System.out.println("Nenhum pedido na fila.");
    } else {
        for (FilaPedidoEntity pedido : pedidos) {
            if (pedido.getStatusPedido() == StatusPedido.FILA || pedido.getStatusPedido() == StatusPedido.PREPARANDO) {
                encontrou = true;
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
        if (!encontrou) {
            System.out.println("Nenhum pedido com status FILA ou PREPARANDO encontrado.");
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
        System.out.print( "Digite a senha do pedido a cancelar: " );
        String senha = scanner.nextLine();

        // Busca o pedido pelo número da senha
        FilaPedidoEntity pedido = filaRepo.buscarPorSenha( senha );

        if (pedido == null) {
            // Se o pedido não for encontrado, exibe uma mensagem
            System.out.println( "Pedido não encontrado." );
        } else if (pedido.getStatusPedido() != StatusPedido.FILA) {
            System.out.println( "O pedido já está em preparo ou finalizado e não pode ser cancelado." );
        } else {
            filaRepo.deletar( pedido );// Se o pedido não estiver com status 'na fila', não permite o cancelamento
            System.out.println( "Pedido cancelado com sucesso." );
        }
    }

    //metodo para ver o historico de pedidos do usuario
    public void verHistoricoPedidos(UsuarioEntity usuarioLogado) {
        List<FilaPedidoEntity> pedidos = filaRepo.listarPorUsuario( usuarioLogado );
        boolean encontrouFinalizados = false;

        if (pedidos.isEmpty()) {
            System.out.println( "Nenhum pedido encontrado." );
        } else {
            for (FilaPedidoEntity pedido : pedidos) {
                if (pedido.getStatusPedido() == StatusPedido.FINALIZADO) {
                    encontrouFinalizados = true;
                    System.out.println( "Senha: " + pedido.getSenhaPedido() );
                    StringBuilder produtosComprados = new StringBuilder();

                    for (ProdutoPedidoEntity produtoPedido : pedido.getProdutos()) {
                        ProdutoEntity produto = produtoPedido.getProduto();
                        produtosComprados.append( " - " )
                                .append( produto.getNome() )
                                .append( " - R$" )
                                .append( produto.getPreco() )
                                .append( "\n" );
                    }

                    System.out.println( "Produto(s):\n" + produtosComprados.toString() );
                    System.out.println( "Data: " + pedido.getDataPedido() );
                    System.out.println( "Status: " + pedido.getStatusPedido() );
                    System.out.println( "-----------------------------" );
                }
            }
            if (!encontrouFinalizados) {
                System.out.println( "Nenhum pedido FINALIZADO encontrado." );
            }
        }
    }
}
