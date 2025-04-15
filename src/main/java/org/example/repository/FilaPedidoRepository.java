package org.example.repository;

import org.example.entities.FilaPedidoEntity;
import org.example.entities.UsuarioEntity;
import org.example.util.HibernateUtil;

import org.hibernate.Session;
import org.hibernate.Transaction;


import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import java.util.List;

public class FilaPedidoRepository {

    private EntityManager em;

    // Construtor que recebe o EntityManager
    public FilaPedidoRepository(EntityManager em) {
        this.em = em;
    }

    public void salvar(org.example.entities.FilaPedidoEntity pedido) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        session.persist(pedido);

        tx.commit();
        session.close();
    }

    public org.example.entities.FilaPedidoEntity buscarPorId(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        org.example.entities.FilaPedidoEntity pedido = session.get(org.example.entities.FilaPedidoEntity.class, id);
        session.close();
        return pedido;
    }

    public List<org.example.entities.FilaPedidoEntity> listarTodos() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<org.example.entities.FilaPedidoEntity> pedidos = session.createQuery("FROM FilaPedidoEntity", org.example.entities.FilaPedidoEntity.class).list();
        session.close();
        return pedidos;
    }

    public void deletar(org.example.entities.FilaPedidoEntity pedido) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        session.remove( pedido );

        tx.commit();
        session.close();
    }

    public FilaPedidoEntity buscarPorSenha(String senha) {
        return em.createQuery("SELECT f FROM FilaPedidoEntity f WHERE f.senhaPedido = :senha", FilaPedidoEntity.class)
                .setParameter("senha", senha)
                .getResultStream()
                .findFirst()
                .orElse(null);
    }

    public List<FilaPedidoEntity> listarPorUsuario(UsuarioEntity usuario) {
        String jpql = "SELECT p FROM FilaPedidoEntity p WHERE p.usuario = :usuario";
        TypedQuery<FilaPedidoEntity> query = em.createQuery(jpql, FilaPedidoEntity.class);
        query.setParameter("usuario", usuario);
        return query.getResultList();
    }
    public FilaPedidoEntity buscarUltimoPedido() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        FilaPedidoEntity ultimo = session.createQuery(
                        "FROM FilaPedidoEntity ORDER BY id DESC", FilaPedidoEntity.class)
                .setMaxResults(1)
                .uniqueResult();
        session.close();
        return ultimo;
    }

}
