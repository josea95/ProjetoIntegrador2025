package org.example.controller;

import org.example.model.entities.FilaPedidoEntity;
import org.example.model.services.PedidoService;
import org.example.model.entities.UsuarioEntity;

public class PedidoController {

    private final PedidoService pedidoService;

    //Construtor padrao
    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    public void iniciarPedido(UsuarioEntity usuarioLogado) {
        FilaPedidoEntity pedido = pedidoService.fazerPedido( usuarioLogado );
    }

    public String confirmarPedido(FilaPedidoEntity pedido) {
        if (pedido.getProdutos() == null || pedido.getProdutos().isEmpty()) {
            return "ERRO: O carrinho está vazio. Adicione produtos antes de confirmar.";
        }

        try {
            pedidoService.salvarPedido( pedido );
            return "Pedido confirmado com sucesso!";
        } catch (Exception e) {
            e.printStackTrace();
            return "ERRO: Ocorreu um erro ao salvar o pedido. Tente novamente.";
        }
    }
}