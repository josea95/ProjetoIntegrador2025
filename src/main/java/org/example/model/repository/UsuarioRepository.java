package org.example.model.repository;


import org.example.model.entities.UsuarioEntity;
import org.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;


public class UsuarioRepository {

    private EntityManager em;

    public UsuarioRepository(EntityManager em) {
        this.em = em;
    }

    public void salvar(UsuarioEntity usuario) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        session.persist(usuario);

        tx.commit();
        session.close();
    }

    public UsuarioEntity buscarPorId(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        UsuarioEntity usuario = session.get(UsuarioEntity.class, id);
        session.close();
        return usuario;
    }

    public List<UsuarioEntity> listarTodos() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<UsuarioEntity> usuarios = session.createQuery("FROM UsuarioEntity", UsuarioEntity.class).list();
        session.close();
        return usuarios;
    }

    public void deletar(UsuarioEntity usuario) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        session.remove(usuario);

        tx.commit();
        session.close();
    }

    public UsuarioEntity buscarPorLoginESenha(String login, String senha) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            return session.createQuery(
                            "SELECT u FROM UsuarioEntity u WHERE u.login = :login AND u.senha = :senha", UsuarioEntity.class)
                    .setParameter("login", login)
                    .setParameter("senha", senha)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            session.close();
        }
    }
}
