package org.example.model.services;

import org.example.model.repository.ProdutoHistoricoPedidoRepository;

import java.time.LocalDate;
import java.util.List;

public class RelatorioService {

    private final ProdutoHistoricoPedidoRepository produtoHistoricoPedidoRepo;

    public RelatorioService(ProdutoHistoricoPedidoRepository produtoHistoricoPedidoRepo) {
        this.produtoHistoricoPedidoRepo = produtoHistoricoPedidoRepo;
    }

    public void gerarRelatorioDoDia(LocalDate data) {
        List<Object[]> resultados = produtoHistoricoPedidoRepo.gerarRelatorioVendasDoDia(data);

        int totalProdutosVendidos = 0;

        System.out.println("📅 Relatório de Vendas - " + data);
        for (Object[] linha : resultados) {
            String nomeProduto = (String) linha[0];
            Long quantidadeVendida = (Long) linha[1];
            totalProdutosVendidos += quantidadeVendida;

            System.out.println("🍱 " + nomeProduto + ": " + quantidadeVendida + " unidade(s)");
        }

        System.out.println("📦 Total de marmitas vendidas no dia: " + totalProdutosVendidos);
    }
}

