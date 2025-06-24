package org.example.controller;

import org.example.model.services.RelatorioService;

public class RelatorioController {
    private final RelatorioService relatorioService;

    public RelatorioController(RelatorioService relatorioService) {
        this.relatorioService = relatorioService;
    }
}