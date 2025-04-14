package repository;

import org.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class FilaPedidoRepository {

    public void salvar(org.example.entity.FilaPedido pedido) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        session.persist(pedido);

        tx.commit();
        session.close();
    }

    public org.example.entity.FilaPedido buscarPorId(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        org.example.entity.FilaPedido pedido = session.get(org.example.entity.FilaPedido.class, id);
        session.close();
        return pedido;
    }

    public List<org.example.entity.FilaPedido> listarTodos() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<org.example.entity.FilaPedido> pedidos = session.createQuery("FROM FilaPedido", org.example.entity.FilaPedido.class).list();
        session.close();
        return pedidos;
    }

    public void deletar(org.example.entity.FilaPedido pedido) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        session.remove(pedido);

        tx.commit();
        session.close();
    }
}

