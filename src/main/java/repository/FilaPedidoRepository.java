package repository;

import org.example.entity.FilaPedido;
import org.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class FilaPedidoRepository {

    public void salvar(FilaPedido pedido) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        session.persist(pedido);

        tx.commit();
        session.close();
    }

    public FilaPedido buscarPorId(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        FilaPedido pedido = session.get(FilaPedido.class, id);
        session.close();
        return pedido;
    }

    public List<FilaPedido> listarTodos() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<FilaPedido> pedidos = session.createQuery("FROM FilaPedido", FilaPedido.class).list();
        session.close();
        return pedidos;
    }

    public void deletar(FilaPedido pedido) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        session.remove(pedido);

        tx.commit();
        session.close();
    }
}

