package org.example.model.repository;

import org.example.model.entities.HistoricoPedidoEntity;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class HistoricoPedidoRepository {

    private final EntityManager em;

    public HistoricoPedidoRepository(EntityManager em) {
        this.em = em;
    }

    public void salvar(HistoricoPedidoEntity historico) {
        em.getTransaction().begin();
        em.persist(historico);
        em.getTransaction().commit();
    }

    public List<HistoricoPedidoEntity> listarHistoricoPorUsuario(Object usuario) {
        TypedQuery<HistoricoPedidoEntity> query = em.createQuery(
                "SELECT h FROM HistoricoPedidoEntity h WHERE h.usuario = :usuario", HistoricoPedidoEntity.class);
        query.setParameter("usuario", usuario);
        return query.getResultList();
    }
}