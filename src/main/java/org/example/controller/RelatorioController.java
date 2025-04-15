package com.marmita.controller;

import com.marmita.service.RelatorioService;
import com.marmita.service.RelatorioService.RelatorioDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/relatorios")
public class RelatorioController {

    @Autowired
    private RelatorioService service;

    @GetMapping("/diario")
    public RelatorioDTO relatorioDiario(@RequestParam("data") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data) {
        return service.gerarRelatorio(data);
    }
}
