package org.example.model.repository;

import org.example.model.entities.HistoricoPedidoEntity;

import javax.persistence.EntityManager;
import java.util.List;

public class HistoricoPedidoRepository {

    private final EntityManager em;

    public HistoricoPedidoRepository(EntityManager em) {
        this.em = em;
    }

    public void salvar(HistoricoPedidoEntity pedido) {
        em.getTransaction().begin();
        em.persist(pedido);
        em.getTransaction().commit();
    }

    public HistoricoPedidoEntity buscarPorId(int id) {
        return em.find(HistoricoPedidoEntity.class, id);
    }

    public List<HistoricoPedidoEntity> listarTodos() {
        return em.createQuery("FROM HistoricoPedidoEntity", HistoricoPedidoEntity.class).getResultList();
    }

    public void deletar(HistoricoPedidoEntity pedido) {
        em.getTransaction().begin();
        em.remove(em.contains(pedido) ? pedido : em.merge(pedido));
        em.getTransaction().commit();
    }
}
