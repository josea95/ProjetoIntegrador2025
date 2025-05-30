package org.example.view;

import org.example.model.services.RelatorioService;
import java.time.LocalDate;

public class RelatorioView {

    private final RelatorioService relatorioService;

    public RelatorioView(RelatorioService relatorioService) {
        this.relatorioService = relatorioService;
    }

    public void mostrarRelatorioDeHoje() {
        LocalDate hoje = LocalDate.now();
        relatorioService.gerarRelatorioDoDia(hoje);
    }
}

