package org.example.model.services;

import org.example.model.repository.ProdutoHistoricoPedidoRepository;

import java.time.LocalDate;
import java.util.List;

public class RelatorioService {

    private final ProdutoHistoricoPedidoRepository produtoHistoricoPedidoRepo;

    public RelatorioService(ProdutoHistoricoPedidoRepository produtoHistoricoPedidoRepo) {
        this.produtoHistoricoPedidoRepo = produtoHistoricoPedidoRepo;
    }

    public void gerarResumoVendasPorCategoria(LocalDate data) {
        List<Object[]> resultados = produtoHistoricoPedidoRepo.gerarRelatorioVendasPorCategoria(data);

        int totalProdutos = 0;
        int totalMarmitas = 0;

        System.out.println("📋 RESUMO DE VENDAS - " + data);
        for (Object[] linha : resultados) {
            String categoria = (String) linha[0];
            Long pedidos = (Long) linha[1];
            Long quantidade = (Long) linha[2];

            System.out.printf("Categoria: %s | Pedidos: %d | Quantidade Total: %d\n",
                    categoria, pedidos, quantidade);

            totalProdutos += quantidade;

            if (categoria != null && categoria.trim().toLowerCase().contains("marmita")) {
                totalMarmitas += quantidade;
            }
        }

        System.out.println("✅ Total de produtos vendidos: " + totalProdutos);
        System.out.println("🥗 Total de **marmitas** vendidas: " + totalMarmitas);
    }
}
