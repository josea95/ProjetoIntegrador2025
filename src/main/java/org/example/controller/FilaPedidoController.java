package org.example.controller;

import org.example.model.entities.UsuarioEntity;
import org.example.model.services.FilaPedidoService;
import org.example.view.FilaPedidoView;

import java.util.Scanner;

public class FilaPedidoController {

    private final FilaPedidoService service;
    private final Scanner scanner;
    private final FilaPedidoView pedidoView;

    public FilaPedidoController(FilaPedidoService service, FilaPedidoView pedidoView, Scanner scanner) {
        this.service = service;
        this.pedidoView = pedidoView;
        this.scanner = scanner;
    }

    public void cancelarPedido() {
        System.out.print("Digite a senha do pedido a cancelar: ");
        String senha = scanner.nextLine();
        pedidoView.cancelarPedido();
    }

    public void pesquisarPedido() {
        System.out.print("Digite a senha do pedido: ");
        String senha = scanner.nextLine();
        pedidoView.pesquisarPedido();
    }

    public void listarPedidos() {
        pedidoView.listarPedidos();
    }

    public void verHistoricoPedidos(UsuarioEntity usuarioLogado) {
        pedidoView.verHistoricoPedidos(usuarioLogado);
    }
}