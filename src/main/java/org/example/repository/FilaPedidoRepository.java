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

    public void salvar(FilaPedidoEntity pedido) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        session.persist( pedido );

        tx.commit();
        session.close();
    }

    public void deletar(FilaPedidoEntity pedido) {
        em.getTransaction().begin();
        em.remove( em.contains( pedido ) ? pedido : em.merge( pedido ) );
        em.getTransaction().commit();
    }

    public void atualizar(FilaPedidoEntity pedido) {
        em.getTransaction().begin();
        em.merge( pedido );
        em.getTransaction().commit();
    }


    public List<FilaPedidoEntity> listarTodos() {
        TypedQuery<FilaPedidoEntity> query = em.createQuery(
                "SELECT DISTINCT fp FROM FilaPedidoEntity fp LEFT JOIN FETCH fp.produtos",
                FilaPedidoEntity.class
        );
        return query.getResultList();
    }


    public FilaPedidoEntity buscarPorSenha(String senha) {
        return em.createQuery( "SELECT f FROM FilaPedidoEntity f WHERE f.senhaPedido = :senha", FilaPedidoEntity.class )
                .setParameter( "senha", senha )
                .getResultStream()
                .findFirst()
                .orElse( null );
    }

    public List<FilaPedidoEntity> listarPorUsuario(UsuarioEntity usuario) {
        TypedQuery<FilaPedidoEntity> query = em.createQuery( "SELECT p FROM FilaPedidoEntity p WHERE p.usuario = :usuario", FilaPedidoEntity.class );
        query.setParameter( "usuario", usuario );
        return query.getResultList();
    }

    public FilaPedidoEntity buscarUltimoPedido() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        FilaPedidoEntity ultimo = session.createQuery(
                        "FROM FilaPedidoEntity ORDER BY id DESC", FilaPedidoEntity.class )
                .setMaxResults( 1 )
                .uniqueResult();
        session.close();
        return ultimo;
    }

}