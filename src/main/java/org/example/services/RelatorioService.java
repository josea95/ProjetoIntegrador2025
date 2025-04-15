package com.marmita.service;

import com.marmita.entity.Marmita;
import com.marmita.repository.MarmitaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RelatorioService {

    @Autowired
    private MarmitaRepository marmitaRepo;

    public RelatorioDTO gerarRelatorio(LocalDate data) {
        LocalDateTime inicio = data.atStartOfDay();
        LocalDateTime fim = data.atTime(LocalTime.MAX);
        List<Marmita> pedidos = marmitaRepo.findByPeriodo(inicio, fim);

        DoubleSummaryStatistics valorStats = pedidos.stream()
                .collect(Collectors.summarizingDouble(Marmita::getValor));

        DoubleSummaryStatistics pesoStats = pedidos.stream()
                .collect(Collectors.summarizingDouble(Marmita::getPeso));

        double tempoMedio = pedidos.stream()
                .mapToLong(m -> m.getTempoPreparo().toMinutes())
                .average()
                .orElse(0);

        return new RelatorioDTO(
                pedidos.size(),
                valorStats.getSum(),
                pesoStats.getSum(),
                tempoMedio,
                data
        );
    }

    public static class RelatorioDTO {
        public int quantidadePedidos;
        public double valorTotal;
        public double pesoTotal;
        public double tempoMedioMinutos;
        public LocalDate data;

        public RelatorioDTO(int quantidadePedidos, double valorTotal, double pesoTotal, double tempoMedioMinutos, LocalDate data) {
            this.quantidadePedidos = quantidadePedidos;
            this.valorTotal = valorTotal;
            this.pesoTotal = pesoTotal;
            this.tempoMedioMinutos = tempoMedioMinutos;
            this.data = data;
        }
    }
}
