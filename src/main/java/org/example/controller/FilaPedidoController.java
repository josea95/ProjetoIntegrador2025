package org.example.controller;

import org.example.model.services.FilaPedidoService;

public class FilaPedidoController {

    private final FilaPedidoService service;

    public FilaPedidoController(FilaPedidoService service) {
        this.service = service;
    }

    public void cancelarPedido(String senha) {
        service.cancelarPedido( senha );
    }

    public void pesquisarPedido(String senha) {
        service.pesquisarPedido( senha );
    }
}