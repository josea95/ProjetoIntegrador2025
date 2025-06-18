package org.example.model.services;

import org.example.model.entities.*;
import org.example.model.enums.StatusPedido;
import org.example.model.repository.FilaPedidoRepository;
import org.example.model.util.CustomizerFactory;

import javax.persistence.EntityManager;
import javax.swing.*;
import java.util.List;

public class FilaPedidoService {

    private final FilaPedidoRepository filaRepo;
    private final HistoricoPedidoService historicoService;

    public FilaPedidoService(EntityManager em) {
        this.filaRepo = new FilaPedidoRepository( em );
        this.historicoService = new HistoricoPedidoService( em );
    }

    public List<FilaPedidoEntity> listarFilaPedidos() {
        EntityManager em = CustomizerFactory.getEntityManager();
        try {
            return em.createQuery(
                            "SELECT DISTINCT f FROM FilaPedidoEntity f LEFT JOIN FETCH f.produtos",
                            FilaPedidoEntity.class )
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public FilaPedidoEntity pesquisarPedido(String senha) {
        return filaRepo.buscarPorSenha( senha );
    }

    public List<FilaPedidoEntity> verHistoricoPedidos(UsuarioEntity usuarioLogado) {
        return filaRepo.listarPorUsuario( usuarioLogado );
    }

    public void atualizarStatusPedido(Long idPedido, StatusPedido novoStatus) {
        EntityManager em = CustomizerFactory.getEntityManager();
        try {
            em.getTransaction().begin();
            FilaPedidoEntity pedido = em.find( FilaPedidoEntity.class, idPedido );
            if (pedido != null) {
                pedido.setStatusPedido( novoStatus );
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public void finalizarPedido(FilaPedidoEntity pedido) {
        EntityManager em = CustomizerFactory.getEntityManager();
        try {
            em.getTransaction().begin();

            pedido = em.find( FilaPedidoEntity.class, pedido.getId() );
            if (pedido == null) return;

            pedido.setStatusPedido( StatusPedido.FINALIZADO );

            // Cria o histórico
            HistoricoPedidoEntity historico = new HistoricoPedidoEntity();
            historico.setSenhaPedido( pedido.getSenhaPedido() );
            historico.setDataPedido( pedido.getDataPedido() );
            historico.setHoraPedido( pedido.getHoraPedido() );
            historico.setStatusPedido( StatusPedido.FINALIZADO );
            historico.setObservacao( pedido.getObservacao() );
            historico.setUsuario( pedido.getUsuario() );

            // Se tiver cálculo de valor:
            double valorTotal = calcularValorTotalPedido( pedido );
            historico.setValorPedido( valorTotal );

            em.persist( historico );

            // Se tiver produtos
            if (pedido.getProdutos() != null) {
                for (ProdutoPedidoEntity produto : pedido.getProdutos()) {
                    ProdutoHistoricoPedidoEntity produtoHistorico = new ProdutoHistoricoPedidoEntity();
                    produtoHistorico.setHistoricoPedido( historico );
                    produtoHistorico.setProduto( produto.getProduto() );
                    produtoHistorico.setQuantidade( produto.getQuantidade() );
                    em.persist( produtoHistorico );
                }
            }

            // Remove o pedido da fila
            em.remove( pedido );

            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public void cancelarPedido(String senha) {
        EntityManager em = CustomizerFactory.getEntityManager();
        try {
            em.getTransaction().begin();// Inicia a transação

            FilaPedidoEntity pedido = em.createQuery(
                            "SELECT f FROM FilaPedidoEntity f WHERE f.senhaPedido = :senha",
                            FilaPedidoEntity.class )
                    .setParameter( "senha", senha )
                    .getSingleResult();

            if (pedido == null || pedido.getStatusPedido() != StatusPedido.FILA) {
                JOptionPane.showMessageDialog( null, "Pedido não pode ser cancelado." );
                em.getTransaction().rollback(); // Reverte a transação se não for possível cancelar. Garante que o banco de dados não seja afetado pelas operações realizadas dentro daquela transação
                return;
            }
            pedido.setStatusPedido( StatusPedido.CANCELADO );

            HistoricoPedidoEntity historico = new HistoricoPedidoEntity();
            historico.setSenhaPedido( pedido.getSenhaPedido() );
            historico.setDataPedido( pedido.getDataPedido() );
            historico.setHoraPedido( pedido.getHoraPedido() );
            historico.setStatusPedido( StatusPedido.CANCELADO );
            historico.setObservacao( "Pedido cancelado" );
            historico.setUsuario( pedido.getUsuario() );

            // Persiste o histórico
            em.persist( historico );

            if (pedido.getProdutos() != null) {
                for (ProdutoPedidoEntity produto : pedido.getProdutos()) {
                    ProdutoHistoricoPedidoEntity prodHist = new ProdutoHistoricoPedidoEntity();
                    prodHist.setHistoricoPedido( historico );
                    prodHist.setProduto( produto.getProduto() );
                    prodHist.setQuantidade( produto.getQuantidade() );
                    em.persist( prodHist );
                }
            }
            em.remove( (pedido) ); // Remove o pedido da fila
            em.getTransaction().commit();// Commit da transação -> confirma as alterações no banco de dados
            JOptionPane.showMessageDialog( null, "Pedido cancelado com sucesso!" );
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                //Se uma transação estiver ativa, ela é revertida até o ponto do erro
                em.getTransaction().rollback();
            }
            e.printStackTrace();
            JOptionPane.showMessageDialog( null, "Erro ao cancelar pedido." );
        } finally {
            em.close();
        }
    }

    private double calcularValorTotalPedido(FilaPedidoEntity pedido) {
        double total = 0;
        if (pedido.getProdutos() != null) {
            for (ProdutoPedidoEntity produto : pedido.getProdutos()) {
                total += produto.getProduto().getPreco() * produto.getQuantidade();
            }
        }
        return total;
    }
}

