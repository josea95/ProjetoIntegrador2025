package org.example.services;

import org.example.entities.FilaPedidoEntity;
import org.example.repository.FilaPedidoRepository;

import java.util.List;

public class FilaPedidoService {
    private final FilaPedidoRepository filaRepo = new FilaPedidoRepository();


    public void adicionarPedido(FilaPedidoEntity pedido) {
        // Gerar senha automática
        FilaPedidoEntity ultimoPedido = filaRepo.buscarUltimoPedido();
        String novaSenha;

        if (ultimoPedido == null) {
            novaSenha = "M001";
        } else {
            String ultimaSenha = ultimoPedido.getSenhaPedido(); // Ex: M007
            int numero = Integer.parseInt(ultimaSenha.substring(1));
            novaSenha = String.format("M%03d", numero + 1); // gera tipo "M008"
        }

        pedido.setSenhaPedido(novaSenha);
        filaRepo.salvar(pedido);
    }
}
