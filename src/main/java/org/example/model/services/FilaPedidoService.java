package org.example.model.services;

import org.example.model.entities.FilaPedidoEntity;
import org.example.model.entities.UsuarioEntity;
import org.example.model.enums.StatusPedido;
import org.example.model.repository.FilaPedidoRepository;

import javax.persistence.EntityManager;
import java.util.List;

public class FilaPedidoService {

    private final FilaPedidoRepository filaRepo;

    public FilaPedidoService(EntityManager em) {
        this.filaRepo = new FilaPedidoRepository(em);

    }

    public List<FilaPedidoEntity> listarFilaPedidos() {
        return filaRepo.listarTodos();
    }

    public FilaPedidoEntity pesquisarPedido(String senha) {
        return filaRepo.buscarPorSenha(senha);
    }

    public boolean cancelarPedido(String senha) {
        FilaPedidoEntity pedido = filaRepo.buscarPorSenha(senha);

        if (pedido == null) {
            return false;
        }

        if (pedido.getStatusPedido() != StatusPedido.FILA) {
            return false;
        }

        filaRepo.deletar(pedido);
        return true;
    }

    public List<FilaPedidoEntity> verHistoricoPedidos(UsuarioEntity usuarioLogado) {
        return filaRepo.listarPorUsuario(usuarioLogado);
    }
}

