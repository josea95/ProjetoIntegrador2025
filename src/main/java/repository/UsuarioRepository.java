
package repository;

import org.example.entity.Usuario;
import org.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;


public class UsuarioRepository {

    public void salvar(Usuario usuario) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        session.persist(usuario);

        tx.commit();
        session.close();
    }

    public Usuario buscarPorId(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Usuario usuario = session.get(Usuario.class, id);
        session.close();
        return usuario;
    }

    public List<Usuario> listarTodos() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Usuario> usuarios = session.createQuery("FROM Usuario", Usuario.class).list();
        session.close();
        return usuarios;
    }

    public void deletar(Usuario usuario) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        session.remove(usuario);

        tx.commit();
        session.close();
    }
}
