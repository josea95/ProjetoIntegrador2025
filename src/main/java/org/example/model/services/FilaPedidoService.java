package org.example.model.services;

import org.example.model.entities.FilaPedidoEntity;
import org.example.model.entities.UsuarioEntity;
import org.example.model.enums.StatusPedido;
import org.example.model.repository.FilaPedidoRepository;
import org.example.model.util.CustomizerFactory;

import javax.persistence.EntityManager;
import java.util.List;

public class FilaPedidoService {

    private final FilaPedidoRepository filaRepo;

    public FilaPedidoService(EntityManager em) {
        this.filaRepo = new FilaPedidoRepository(em);
    }

    public List<FilaPedidoEntity> listarFilaPedidos() {
        EntityManager em = CustomizerFactory.getEntityManager();
        try {
            return em.createQuery(
                            "SELECT DISTINCT f FROM FilaPedidoEntity f LEFT JOIN FETCH f.produtos",
                            FilaPedidoEntity.class)
                    .getResultList();
        } finally {
            em.close();
        }
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

    public void atualizarStatusPedido(Long idPedido, StatusPedido novoStatus) {
        EntityManager em = CustomizerFactory.getEntityManager();
        try {
            em.getTransaction().begin();
            FilaPedidoEntity pedido = em.find(FilaPedidoEntity.class, idPedido);
            if (pedido != null) {
                pedido.setStatusPedido(novoStatus);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }


}

