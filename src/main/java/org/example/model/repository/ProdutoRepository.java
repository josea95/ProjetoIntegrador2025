package org.example.model.repository;

import org.example.model.entities.ProdutoEntity;
import org.example.model.enums.StatusPedido;

import javax.persistence.*;

import java.util.List;

public class ProdutoRepository {
    private EntityManager em;

    public ProdutoRepository(EntityManager em) {
        this.em = em;
    }

    public void salvar(ProdutoEntity produto) {
        em.getTransaction().begin(); // inicia/faz uma transacao
        em.persist( produto ); // persiste o produto no banco de dados
        em.getTransaction().commit(); // confirma a transacao
    }

    public void atualizar(ProdutoEntity produto) {
        em.getTransaction().begin(); // inicia/faz uma transacao
        em.merge( produto ); // atualiza/mescla o produto no banco de dados
        em.getTransaction().commit(); // confirma a transacao
    }

    public void deletar(Long id) {
        ProdutoEntity produto = em.find( ProdutoEntity.class, id ); //busca o produto pelo ID
        if (produto != null) { // verifica se o produto existe
            em.getTransaction().begin(); // inicia/faz uma transacao
            em.remove( produto ); // remove o produto do banco de dados
            em.getTransaction().commit(); // confirma a transacao
        }
    }
    public boolean existeProdutoEmFilaPedidos(ProdutoEntity produto) {
        Long count = em.createQuery(
                        "SELECT COUNT(fp) FROM FilaPedidoEntity fp JOIN fp.produtos pp " +
                                "WHERE pp.produto = :produto AND fp.statusPedido IN :statusList", Long.class)
                .setParameter("produto", produto)
                .setParameter("statusList", List.of( StatusPedido.FILA, StatusPedido.PREPARANDO))
                .getSingleResult();

        return count != null && count > 0;
    }

    public ProdutoEntity buscarPorId(Long id) {
        return em.find( ProdutoEntity.class, id ); // retorna o produto pelo ID
    }

    public List<ProdutoEntity> buscarTodos() {
        return em.createQuery( "SELECT p FROM ProdutoEntity p", ProdutoEntity.class ).getResultList();
    }

    public List<ProdutoEntity> buscarPorCategoria(String categoria) {
        return em.createQuery( "SELECT p FROM ProdutoEntity p WHERE p.categoria = :categoria", ProdutoEntity.class )
                .setParameter( "categoria", categoria ) // definindo o valor do parametro 'categoria'
                .getResultList(); // executa a consulta e retorna uma lista de resultados
    }
}