package org.example.model.services;

import org.example.model.entities.UsuarioEntity;
import org.example.model.repository.UsuarioRepository;
import org.example.model.util.CustomizerFactory;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;

public class UsuarioService {

    private UsuarioRepository usuarioRepository;

    public UsuarioService(EntityManager em) {

        this.usuarioRepository = new UsuarioRepository(em);
    }

    public UsuarioEntity autenticar(String login, String senha) {
        EntityManager em = CustomizerFactory.getEntityManager();
        try {
            return em.createQuery("SELECT u FROM UsuarioEntity u WHERE u.login = :login AND u.senha = :senha", UsuarioEntity.class)
                    .setParameter("login", login)
                    .setParameter("senha", senha)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }


    public void criarUsuario(String nome, String email, String login, String senha) {
        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setNome(nome);
        usuario.setEmail(email);
        usuario.setLogin(login);
        usuario.setSenha(senha);

        usuarioRepository.salvar(usuario);
    }

    public UsuarioEntity buscarPorId(int id) {

        return usuarioRepository.buscarPorId(id);
    }

    public List<UsuarioEntity> listarTodos() {
        return usuarioRepository.listarTodos();
    }

    public boolean deletarUsuarioPorId(int id) {
        UsuarioEntity usuario = usuarioRepository.buscarPorId(id);
        if (usuario != null) {
            usuarioRepository.deletar(usuario);
            return true;
        }
        return false;
    }

    public UsuarioEntity login(String login, String senha) {
        if (login == null || login.isBlank() || senha == null || senha.isBlank()) {
            return null;
        }
        return usuarioRepository.buscarPorLoginESenha(login, senha);
    }
}
