package org.example.repository;

import org.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class HistoricoPedidoRepository {

    public void salvar(org.example.entities.HistoricoPedidoEntity pedido) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        session.persist(pedido);

        tx.commit();
        session.close();
    }

    public org.example.entities.HistoricoPedidoEntity buscarPorId(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        org.example.entities.HistoricoPedidoEntity pedido = session.get(org.example.entities.HistoricoPedidoEntity.class, id);
        session.close();
        return pedido;
    }

    public List<org.example.entities.HistoricoPedidoEntity> listarTodos() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<org.example.entities.HistoricoPedidoEntity> pedidos = session.createQuery("FROM HistoricoPedidoEntity", org.example.entities.HistoricoPedidoEntity.class).list();
        session.close();
        return pedidos;
    }

    public void deletar(org.example.entities.HistoricoPedidoEntity pedido) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        session.remove(pedido);

        tx.commit();
        session.close();
    }
}
