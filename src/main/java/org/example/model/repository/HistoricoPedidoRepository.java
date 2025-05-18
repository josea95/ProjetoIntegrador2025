package org.example.model.repository;

import org.example.model.entities.HistoricoPedidoEntity;
import org.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class HistoricoPedidoRepository {

    public void salvar(HistoricoPedidoEntity pedido) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        session.persist(pedido);

        tx.commit();
        session.close();
    }

    public HistoricoPedidoEntity buscarPorId(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        org.example.model.entities.HistoricoPedidoEntity pedido = session.get( org.example.model.entities.HistoricoPedidoEntity.class, id);
        session.close();
        return pedido;
    }

    public List<HistoricoPedidoEntity> listarTodos() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<org.example.model.entities.HistoricoPedidoEntity> pedidos = session.createQuery("FROM HistoricoPedidoEntity", org.example.model.entities.HistoricoPedidoEntity.class).list();
        session.close();
        return pedidos;
    }

    public void deletar(HistoricoPedidoEntity pedido) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        session.remove(pedido);

        tx.commit();
        session.close();
    }
}
