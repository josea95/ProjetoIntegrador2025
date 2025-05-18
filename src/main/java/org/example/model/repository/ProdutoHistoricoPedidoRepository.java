package org.example.model.repository;
import org.example.model.entities.ProdutoHistoricoPedidoEntity;

import javax.persistence.EntityManager;
import java.util.List;

public class ProdutoHistoricoPedidoRepository {
    private EntityManager em;

    public ProdutoHistoricoPedidoRepository(EntityManager em) {
        this.em = em;
    }

    public void salvar(ProdutoHistoricoPedidoEntity produtoHistorico) {
        em.getTransaction().begin();
        em.persist(produtoHistorico);
        em.getTransaction().commit();
    }

    public ProdutoHistoricoPedidoEntity buscarPorId(Long id) {
        return em.find(ProdutoHistoricoPedidoEntity.class, id);
    }

    public List<ProdutoHistoricoPedidoEntity> buscarTodos() {
        return em.createQuery("SELECT ph FROM ProdutoHistoricoPedidoEntity ph", ProdutoHistoricoPedidoEntity.class).getResultList();
    }

    public void deletar(Long id) {
        ProdutoHistoricoPedidoEntity produtoHistorico = em.find(ProdutoHistoricoPedidoEntity.class, id);
        if (produtoHistorico != null) {
            em.getTransaction().begin();
            em.remove(produtoHistorico);
            em.getTransaction().commit();
        }
    }
}
