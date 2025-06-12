package org.example.model.services;

import org.example.model.entities.FilaPedidoEntity;
import org.example.model.entities.UsuarioEntity;
import org.example.model.enums.StatusPedido;
import org.example.model.repository.FilaPedidoRepository;

import javax.persistence.EntityManager;
import javax.swing.*;
import java.util.List;

public class FilaPedidoService {

    private final FilaPedidoRepository filaRepo;

    public FilaPedidoService(EntityManager em) {
        this.filaRepo = new FilaPedidoRepository( em );
    }

    public List<FilaPedidoEntity> listarFilaPedidos() {
        return filaRepo.listarTodos();
    }

    public void pesquisarPedido(String senha) {
        FilaPedidoEntity pedido = filaRepo.buscarPorSenha(senha);
        if (pedido == null) {
            JOptionPane.showMessageDialog(null, "Pedido não encontrado.");
        } else {
            String msg = "Senha: " + pedido.getSenhaPedido() +
                    "\nData: " + pedido.getDataPedido() +
                    "\nHora: " + pedido.getHoraPedido() +
                    "\nStatus: " + pedido.getStatusPedido().toString() +
                    "\nUsuário: " + pedido.getUsuario().getNome();
            JOptionPane.showMessageDialog(null, msg);
        }
    }

    public boolean cancelarPedido(String senha) {
        FilaPedidoEntity pedido = filaRepo.buscarPorSenha( senha );

        if (pedido == null) {
            JOptionPane.showMessageDialog( null, "Pedido não encontrado." );
            return false;
        }
        if (pedido.getStatusPedido() != StatusPedido.FILA) {
            JOptionPane.showMessageDialog( null, "Pedido não pode ser cancelado. Status inválido: " + pedido.getStatusPedido() );
            return false;
        }
        JOptionPane.showMessageDialog( null, "Pedido cancelado com sucesso!" );
        filaRepo.deletar( pedido );
        return true;
    }

    public List<FilaPedidoEntity> verHistoricoPedidos(UsuarioEntity usuarioLogado) {
        return filaRepo.listarPorUsuario( usuarioLogado );
    }
}

