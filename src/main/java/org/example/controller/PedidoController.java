package org.example.controller;

import org.example.model.entities.UsuarioEntity;
import org.example.model.services.PedidoService;
import org.example.view.PedidoView;


public class PedidoController {

    private final PedidoService pedidoService;
    private final PedidoView pedidoView;

    public PedidoController(PedidoService pedidoService, PedidoView pedidoView) {
        this.pedidoService = pedidoService;
        this.pedidoView = pedidoView;
    }

    public void iniciarPedido(UsuarioEntity usuarioLogado) {
        pedidoView.iniciarPedido( usuarioLogado );
    }
}
