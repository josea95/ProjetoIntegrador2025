package org.example.services;

import org.example.entities.FilaPedidoEntity;
import org.example.repository.FilaPedidoRepository;

import javax.persistence.EntityManager;
import java.util.List;

public class FilaPedidoService {

    private final FilaPedidoRepository filaRepo;

    // Construtor que recebe o EntityManager
    public FilaPedidoService(EntityManager em) {
        this.filaRepo = new FilaPedidoRepository(em);
    }

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


