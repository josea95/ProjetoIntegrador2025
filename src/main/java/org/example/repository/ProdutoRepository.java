package org.example.repository;

import org.example.entities.ProdutoEntity;
import javax.persistence.*;

import java.util.List;

public class ProdutoRepository {
    private EntityManager em;

    //Construtor padrao
    public ProdutoRepository(EntityManager em) {
        this.em = em;
    }

    //metodo para salvar o produto no banco de dados
    public void salvar(ProdutoEntity produto) {
        em.getTransaction().begin(); // inicia/faz uma transacao
        em.persist( produto ); // persiste o produto no banco de dados
        em.getTransaction().commit(); // confirma a transacao
    }

    //metodo para atualizar o produto no banco de dados
    public void atualizar(ProdutoEntity produto) {
        em.getTransaction().begin(); // inicia/faz uma transacao
        em.merge( produto ); // atualiza/mescla o produto no banco de dados
        em.getTransaction().commit(); // confirma a transacao
    }

    //metodo para remover o produto pelo ID
    public void deletar(Long id) {
        ProdutoEntity produto = em.find( ProdutoEntity.class, id ); //busca o produto pelo ID
        if (produto != null) { // verifica se o produto existe
            em.getTransaction().begin(); // inicia/faz uma transacao
            em.remove( produto ); // remove o produto do banco de dados
            em.getTransaction().commit(); // confirma a transacao
        }
    }

    //metodo para buscar o produto pelo ID
    public ProdutoEntity buscarPorId(Long id) {
        return em.find( ProdutoEntity.class, id ); // retorna o produto pelo ID
    }

    //metodo para buscar todos os produtos do banco de dados
    public List<ProdutoEntity> buscarTodos() {
        return em.createQuery( "SELECT p FROM ProdutoEntity p", ProdutoEntity.class ).getResultList();
    }

    //metodo para buscar o produto pelo nome
    public ProdutoEntity buscarPorNome(String nome) {
        return em.createQuery( "SELECT p FROM ProdutoEntity p WHERE p.nome = :nome", ProdutoEntity.class )
                .setParameter( "nome", nome ) // definindo o valor do parametro 'nome'
                .getResultStream() // executa a consulta e retorna um stream de resultados
                .findFirst() // se tiver um elemento no stream, retorna o primeiro
                .orElse( null ); // se nao tiver retorna null
    }

    //metodo para buscar o produto pela categoria que ele estiver cadastrado
    public List<ProdutoEntity> buscarPorCategoria(String categoria) {
        return em.createQuery( "SELECT p FROM ProdutoEntity p WHERE p.categoria = :categoria", ProdutoEntity.class )
                .setParameter( "categoria", categoria ) // definindo o valor do parametro 'categoria'
                .getResultList(); // executa a consulta e retorna uma lista de resultados
    }


    //metodo para exibir os produtos que foram vendidos
    public List<ProdutoEntity> listarProdutosVendidos() {
        return em.createQuery( "SELECT DISTINCT p FROM ProdutoEntity p JOIN p.produtosPedidos pp", ProdutoEntity.class )
                .getResultList(); // executa a consulta e retorna uma lista de produtos que foram vendidos
    }


}
