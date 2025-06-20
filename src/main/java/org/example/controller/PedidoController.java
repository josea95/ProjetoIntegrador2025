package org.example.controller;

import org.example.model.entities.FilaPedidoEntity;
import org.example.model.entities.ProdutoEntity;
import org.example.model.services.PedidoService;
import org.example.model.entities.UsuarioEntity;

import java.util.List;
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    public FilaPedidoEntity iniciarPedido(UsuarioEntity usuarioLogado) {
        return pedidoService.fazerPedido( usuarioLogado );
    }

    public boolean confirmarPedido(FilaPedidoEntity pedido) {
        if (pedido.getProdutos() == null || pedido.getProdutos().isEmpty()) {
            return false;
        }
        try {
            pedidoService.gerarESetSenhaPedido( pedido );
            return pedidoService.salvarPedido( pedido );
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<ProdutoEntity> buscarProdutosPorCategoria(String categoria) {
        return pedidoService.buscarProdutosPorCategoria( categoria );
    }

    public void adicionarProdutoAoPedido(FilaPedidoEntity pedido, ProdutoEntity produtoEscolhido) {
        pedidoService.adicionarProdutoAoPedido( pedido, produtoEscolhido );
    }
}