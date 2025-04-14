package repository;

import org.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class HistoricoPedido {

    public void salvar(org.example.entity.HistoricoPedido pedido) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        session.persist(pedido);

        tx.commit();
        session.close();
    }

    public org.example.entity.HistoricoPedido buscarPorId(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        org.example.entity.HistoricoPedido pedido = session.get(org.example.entity.HistoricoPedido.class, id);
        session.close();
        return pedido;
    }

    public List<org.example.entity.HistoricoPedido> listarTodos() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<org.example.entity.HistoricoPedido> pedidos = session.createQuery("FROM HistoricoPedido", org.example.entity.HistoricoPedido.class).list();
        session.close();
        return pedidos;
    }

    public void deletar(org.example.entity.HistoricoPedido pedido) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        session.remove(pedido);

        tx.commit();
        session.close();
    }
}

