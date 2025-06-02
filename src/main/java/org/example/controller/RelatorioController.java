package org.example.controller;

import org.example.view.RelatorioView;

public class RelatorioController {
    private final RelatorioView relatorioView;

    public RelatorioController(RelatorioView relatorioView) {
        this.relatorioView = relatorioView;
    }

    public void exibirRelatorioDeHoje() {
        relatorioView.mostrarRelatorioDeHoje();
    }
}
