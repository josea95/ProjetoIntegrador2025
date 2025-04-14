package org.example.repository;


import org.example.entities.UsuarioEntity;
import org.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;


public class UsuarioRepository {

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
}
