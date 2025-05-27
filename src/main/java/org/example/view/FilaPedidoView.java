//package org.example.view;
//
//import org.example.controller.FilaPedidoController;
//import org.example.model.entities.UsuarioEntity;
//import org.example.model.services.FilaPedidoService;
//
//public class FilaPedidoView {
//
//    private final FilaPedidoController controller;
//    private final FilaPedidoService filaService;
//
//    public FilaPedidoView(FilaPedidoController controller, FilaPedidoService filaService) {
//        this.controller = controller;
//        this.filaService = filaService;
//    }
//
//    public void listarPedidos() {
//        controller.listarFilaPedidos();
//    }
//
//    public void pesquisarPedido() {
//        controller.pesquisarPedido();
//    }
//
//    public void cancelarPedido() {
//        controller.cancelarPedido();
//    }
//
//    public void verHistoricoPedidos(UsuarioEntity usuarioLogado) {
//        controller.verHistoricoPedidos( usuarioLogado );
//    }
//}


package org.example.view;

import org.example.controller.FilaPedidoController;
import org.example.model.entities.FilaPedidoEntity;
import org.example.model.entities.ProdutoEntity;
import org.example.model.entities.ProdutoPedidoEntity;
import org.example.model.entities.UsuarioEntity;
import org.example.model.enums.StatusPedido;
import org.example.model.services.FilaPedidoService;

import java.util.List;
import java.util.Scanner;

public class FilaPedidoView {

    private final FilaPedidoService filaService;
    private final Scanner scanner;

    public FilaPedidoView(FilaPedidoService filaService, Scanner scanner) {

        this.filaService = filaService;
        this.scanner = scanner;
    }

    public void listarPedidos() {
        List<FilaPedidoEntity> pedidos = filaService.listarFilaPedidos();

        boolean encontrou = false;
        for (FilaPedidoEntity pedido : pedidos) {
            if (pedido.getStatusPedido() == StatusPedido.FILA || pedido.getStatusPedido() == StatusPedido.PREPARANDO) {
                encontrou = true;
                exibirPedido(pedido);
            }
        }

        if (!encontrou) {
            System.out.println("Nenhum pedido com status FILA ou PREPARANDO encontrado.");
        }
    }

    public void pesquisarPedido() {
        System.out.print("Digite a senha do pedido: ");
        String senha = scanner.nextLine();
        FilaPedidoEntity pedido = filaService.pesquisarPedido(senha);

        if (pedido == null) {
            System.out.println("Pedido não encontrado.");
        } else {
            exibirPedido(pedido);
        }
    }

    public void cancelarPedido() {
        System.out.print("Digite a senha do pedido a cancelar: ");
        String senha = scanner.nextLine();
        boolean sucesso = filaService.cancelarPedido(senha);

        if (sucesso) {
            System.out.println("Pedido cancelado com sucesso.");
        } else {
            System.out.println("Não foi possível cancelar o pedido.");
        }
    }

    public void verHistoricoPedidos(UsuarioEntity usuarioLogado) {
        List<FilaPedidoEntity> pedidos = filaService.verHistoricoPedidos(usuarioLogado);

        boolean encontrou = false;
        for (FilaPedidoEntity pedido : pedidos) {
            if (pedido.getStatusPedido() == StatusPedido.FINALIZADO) {
                encontrou = true;
                exibirPedido(pedido);
            }
        }

        if (!encontrou) {
            System.out.println("Nenhum pedido FINALIZADO encontrado.");
        }
    }

    private void exibirPedido(FilaPedidoEntity pedido) {
        System.out.println("Senha: " + pedido.getSenhaPedido());
        for (ProdutoPedidoEntity produtoPedido : pedido.getProdutos()) {
            ProdutoEntity produto = produtoPedido.getProduto();
            System.out.println("- " + produto.getNome() + " - R$" + produto.getPreco());
        }
        System.out.println("Data: " + pedido.getDataPedido());
        System.out.println("Hora: " + pedido.getHoraPedido());
        System.out.println("Status: " + pedido.getStatusPedido());
        System.out.println("-----------------------------");
    }
}
