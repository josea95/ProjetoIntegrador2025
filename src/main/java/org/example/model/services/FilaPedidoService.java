package org.example.model.services;

import org.example.model.entities.*;
import org.example.model.enums.StatusPedido;
import org.example.model.repository.FilaPedidoRepository;
import org.example.model.util.CustomizerFactory;

import javax.persistence.EntityManager;
import javax.swing.*;
import java.util.ArrayList;
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

    public void cancelarPedido(String senha) {
        FilaPedidoEntity pedido = filaRepo.buscarPorSenha( senha );
        if (pedido == null) { // Verifica se o pedido existe
            JOptionPane.showMessageDialog( null, "Pedido não encontrado ou já finalizado." );
            return;
        }

        if (pedido.getSenhaPedido() != null && pedido.getStatusPedido() == StatusPedido.FILA) {
            filaRepo.deletar( pedido );
            pedido.setStatusPedido( StatusPedido.CANCELADO );
            filaRepo.atualizar( pedido );
            JOptionPane.showMessageDialog( null, "Pedido cancelado com sucesso!" );
        } else if (pedido.getStatusPedido() == StatusPedido.PREPARANDO ||
                pedido.getStatusPedido() == StatusPedido.FINALIZADO) {
            JOptionPane.showMessageDialog( null, "Pedido não pode ser cancelado, pois já está em preparo ou finalizado." );
        }
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
                List<ProdutoHistoricoPedidoEntity> listaProdutosHistorico = new ArrayList<>();
                for (ProdutoPedidoEntity produto : pedido.getProdutos()) {
                    ProdutoHistoricoPedidoEntity produtoHistorico = new ProdutoHistoricoPedidoEntity();
                    produtoHistorico.setHistoricoPedido( historico );
                    produtoHistorico.setProduto( produto.getProduto() );
                    produtoHistorico.setQuantidade( produto.getQuantidade() );
                    em.persist( produtoHistorico );
                    listaProdutosHistorico.add( produtoHistorico );
                }
                historico.setProdutos( listaProdutosHistorico );
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