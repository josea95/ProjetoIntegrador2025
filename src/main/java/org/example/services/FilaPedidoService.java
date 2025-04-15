package org.example.services;

import org.example.entities.FilaPedidoEntity;
import org.example.repository.FilaPedidoRepository;

import java.util.List;

public class FilaPedidoService {
    private final FilaPedidoRepository filaRepo = new FilaPedidoRepository();

    public void adicionarPedido(FilaPedidoEntity pedido) {

        filaRepo.salvar(pedido);
    }

    public List<FilaPedidoEntity> listarPedidos() {
        return filaRepo.listarTodos();
    }

    public void removerPedido(FilaPedidoEntity pedido) {
        filaRepo.deletar(pedido);
    }

}


