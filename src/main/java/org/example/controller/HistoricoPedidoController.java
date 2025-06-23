package org.example.controller;

import org.example.Swing.HistoricoPedidosSwing;
import org.example.model.entities.UsuarioEntity;
import org.example.model.services.HistoricoPedidoService;

public class HistoricoPedidoController {

    private final HistoricoPedidoService historicoService;
    private HistoricoPedidosSwing historicoView;  // não precisa criar no construtor fixo

    public HistoricoPedidoController(HistoricoPedidoService historicoService) {
        this.historicoService = historicoService;
    }

    public void verHistoricoPedidos(UsuarioEntity usuarioLogado) {
        historicoView = new HistoricoPedidosSwing(historicoService, usuarioLogado);
    }
}
