package org.example.model.repository;
import org.example.model.entities.FilaPedidoEntity;
import org.example.model.entities.UsuarioEntity;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import org.example.model.enums.StatusPedido;
import org.example.model.util.CustomizerFactory;
import javax.persistence.EntityTransaction;

public class FilaPedidoRepository {

    private final EntityManager em;

    // Construtor com EntityManager
    public FilaPedidoRepository(EntityManager em) {
        this.em = em;
    }
    public EntityManager getEntityManager() {
        return em;
    }

    public void salvar(FilaPedidoEntity pedido) {
        em.getTransaction().begin();
        em.persist(pedido);
        em.getTransaction().commit();
    }

    public void deletar(FilaPedidoEntity pedido) {
        em.getTransaction().begin();
        em.remove(em.contains(pedido) ? pedido : em.merge(pedido));
        em.getTransaction().commit();
    }

    public void atualizar(FilaPedidoEntity pedido) {
        em.getTransaction().begin();
        em.merge(pedido);
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
        return em.createQuery(
                        "SELECT f FROM FilaPedidoEntity f WHERE f.senhaPedido = :senha",
                        FilaPedidoEntity.class
                )
                .setParameter("senha", senha)
                .getResultStream()
                .findFirst()
                .orElse(null);
    }

    public List<FilaPedidoEntity> listarPorUsuario(UsuarioEntity usuario) {
        return em.createQuery(
                        "SELECT p FROM FilaPedidoEntity p WHERE p.usuario = :usuario",
                        FilaPedidoEntity.class
                )
                .setParameter("usuario", usuario)
                .getResultList();
    }

    public FilaPedidoEntity buscarUltimoPedido() {
        return em.createQuery(
                        "SELECT f FROM FilaPedidoEntity f ORDER BY f.id DESC",
                        FilaPedidoEntity.class
                )
                .setMaxResults(1)
                .getResultStream()
                .findFirst()
                .orElse(null);
    }

}
