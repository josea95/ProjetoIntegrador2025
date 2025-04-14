package org.example.repository;

import org.example.entities.ProdutoPedidoEntity;
import javax.persistence.*;

import java.util.List;

public class ProdutoPedidoRepository {
    private EntityManager em;

    //contrutor padrao
    public ProdutoPedidoRepository(EntityManager em) {
        this.em = em;
    }

    public void salvar(ProdutoPedidoEntity produtoPedido) {
        em.getTransaction().begin(); // inicia/faz uma transacao
        em.persist(produtoPedido); // persiste o produtoPedido no banco de dados
        em.getTransaction().commit(); // confirma a transacao
    }

    public void remover(Long id) {
        ProdutoPedidoEntity produtoPedido = em.find(ProdutoPedidoEntity.class, id); // busca o produtoPedido pelo ID
        if (produtoPedido != null) { // verifica se o produtoPedido existe
            em.getTransaction().begin(); // inicia/faz uma transacao
            em.remove(produtoPedido); // remove o produtoPedido do banco de dados
            em.getTransaction().commit(); // confirma a transacao
        }
    }

    public void atualizar(ProdutoPedidoEntity produtoPedido) {
        em.getTransaction().begin(); // inicia/faz uma transacao
        em.merge( produtoPedido ); // atualiza/mescla o produto no banco de dados
        em.getTransaction().commit(); // confirma a transacao
    }

    public ProdutoPedidoEntity buscarPorId(Long id) {
        return em.find(ProdutoPedidoEntity.class, id); // retorna o produtoPedido pelo ID
    }

    public List<ProdutoPedidoEntity> buscarTodos() {
        return em.createQuery("SELECT pp FROM ProdutoPedidoEntity pp", ProdutoPedidoEntity.class)
                .getResultList(); // retorna todos os produtosPedidos
    }


}
