package org.example.model.repository;
import org.example.model.entities.ProdutoHistoricoPedidoEntity;
import java.time.LocalDate;
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
    public List<Object[]> gerarRelatorioVendasPorCategoria(LocalDate data) {
        return em.createQuery(
                        "SELECT p.categoria, COUNT(php), SUM(php.quantidade) " +
                                "FROM ProdutoHistoricoPedidoEntity php " +
                                "JOIN php.produto p " +
                                "JOIN php.historicoPedido h " +
                                "WHERE h.dataPedido = :data " +
                                "GROUP BY p.categoria", Object[].class
                )
                .setParameter("data", data)
                .getResultList();
    }
}



