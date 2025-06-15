package org.example.controller;

import org.example.model.entities.FilaPedidoEntity;
import org.example.model.services.PedidoService;
import org.example.model.entities.UsuarioEntity;

public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    public FilaPedidoEntity iniciarPedido(UsuarioEntity usuarioLogado) {
        return pedidoService.fazerPedido( usuarioLogado );
    }

    public String confirmarPedido(FilaPedidoEntity pedido) {
        if (pedido.getProdutos() == null || pedido.getProdutos().isEmpty()) {
            return "ERRO: O carrinho está vazio. Adicione produtos antes de confirmar.";
        }
        try {
            pedidoService.gerarESetSenhaPedido( pedido );
            boolean salvo = pedidoService.salvarPedido( pedido );
            if (!salvo) {
                return "ERRO: Não foi possível salvar o pedido. O carrinho está vazio.";
            }
            return "Pedido confirmado com sucesso! Sua senha é " + pedido.getSenhaPedido();
        } catch (Exception e) {
            e.printStackTrace();
            return "ERRO: Ocorreu um erro ao salvar o pedido. Tente novamente.";
        }
    }
}