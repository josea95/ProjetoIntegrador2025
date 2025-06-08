package org.example.model.repository;

import org.example.model.entities.UsuarioEntity;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;

public class UsuarioRepository {

    private final EntityManager em;

    public UsuarioRepository(EntityManager em) {
        this.em = em;
    }

    public void salvar(UsuarioEntity usuario) {
        em.getTransaction().begin();
        em.persist(usuario);
        em.getTransaction().commit();
    }

    public UsuarioEntity buscarPorId(int id) {
        return em.find(UsuarioEntity.class, id);
    }

    public List<UsuarioEntity> listarTodos() {
        return em.createQuery("FROM UsuarioEntity", UsuarioEntity.class).getResultList();
    }

    public void deletar(UsuarioEntity usuario) {
        em.getTransaction().begin();
        em.remove(em.contains(usuario) ? usuario : em.merge(usuario));
        em.getTransaction().commit();
    }

    public UsuarioEntity buscarPorLoginESenha(String login, String senha) {
        try {
            return em.createQuery(
                            "SELECT u FROM UsuarioEntity u WHERE u.login = :login AND u.senha = :senha", UsuarioEntity.class)
                    .setParameter("login", login)
                    .setParameter("senha", senha)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}