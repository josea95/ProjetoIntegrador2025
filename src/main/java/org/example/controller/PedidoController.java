package org.example.controller;

import org.example.model.entities.FilaPedidoEntity;
import org.example.model.services.PedidoService;
import org.example.model.entities.UsuarioEntity;
import org.example.view.PedidoView;

public class PedidoController {

    private final PedidoService pedidoService;
    private final PedidoView pedidoView;

    public PedidoController(PedidoService pedidoService, PedidoView pedidoView) {
        this.pedidoService = pedidoService;
        this.pedidoView = pedidoView;
    }

    public void iniciarPedido(UsuarioEntity usuarioLogado) {
        // Cria o pedido
        FilaPedidoEntity pedido = pedidoService.fazerPedido(usuarioLogado);

        // Passa o pedido para a view conduzir o processo (adicionar produtos, confirmar, etc.)
        pedidoView.iniciarPedido(pedido.getUsuario());
    }

    public String confirmarPedido(FilaPedidoEntity pedido) {
        if (pedido.getProdutos() == null || pedido.getProdutos().isEmpty()) {
            return "ERRO: O carrinho está vazio. Adicione produtos antes de confirmar.";
        }

        try {
            pedidoService.salvarPedido(pedido);
            return "Pedido confirmado com sucesso!";
        } catch (Exception e) {
            e.printStackTrace();
            return "ERRO: Ocorreu um erro ao salvar o pedido. Tente novamente.";
        }
    }
}
