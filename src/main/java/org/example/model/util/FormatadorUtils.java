package org.example.model.util;

import org.example.model.entities.ProdutoHistoricoPedidoEntity;
import org.example.model.entities.ProdutoPedidoEntity;

import java.util.List;

public class FormatadorUtils {

    public static String formatarProdutos(List<ProdutoPedidoEntity> produtos) {
        if (produtos == null || produtos.isEmpty()) return "";

        StringBuilder sb = new StringBuilder();
        for (ProdutoPedidoEntity prod : produtos) {
            sb.append(prod.getProduto().getNome())
                    .append(" x").append(prod.getQuantidade())
                    .append(", ");
        }

        if (sb.length() > 2) sb.setLength(sb.length() - 2);
        return sb.toString();
    }

    public static String formatarProdutosHistorico(List<ProdutoHistoricoPedidoEntity> produtos) {
        if (produtos == null || produtos.isEmpty()) return "";

        StringBuilder sb = new StringBuilder();
        for (ProdutoHistoricoPedidoEntity produto : produtos) {
            sb.append(produto.getProduto().getNome())
                    .append(" x").append(produto.getQuantidade())
                    .append(", ");
        }

        if (sb.length() > 2) sb.setLength(sb.length() - 2); // remove última vírgula
        return sb.toString();
    }

}

