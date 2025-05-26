package org.example.model.services;

import org.example.model.entities.FilaPedidoEntity;
import org.example.model.entities.HistoricoPedidoEntity;
import org.example.model.entities.ProdutoEntity;
import org.example.model.entities.ProdutoPedidoEntity;
import org.example.model.entities.ProdutoHistoricoPedidoEntity;
import org.example.model.entities.UsuarioEntity;
import org.example.model.enums.StatusPedido;
import org.example.model.repository.FilaPedidoRepository;
import org.example.model.repository.HistoricoPedidoRepository;
import org.example.model.repository.ProdutoHistoricoPedidoRepository;
import org.example.model.repository.ProdutoRepository;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Random;

public class PedidoService {

    private final FilaPedidoRepository pedidoRepo;
    private final ProdutoRepository produtoRepo;
    private final HistoricoPedidoRepository historicoRepo;
    private final ProdutoHistoricoPedidoRepository produtoHistoricoRepo;

    // Construtor sem Scanner, pois a 'view' gerencia apenas as entradas do usuário
    public PedidoService(EntityManager em) {
        this.pedidoRepo = new FilaPedidoRepository(em);
        this.produtoRepo = new ProdutoRepository(em);
        this.historicoRepo = new HistoricoPedidoRepository(em);
        this.produtoHistoricoRepo = new ProdutoHistoricoPedidoRepository(em);
    }

    // Cria o pedido sem realizar exibições
    public FilaPedidoEntity fazerPedido(UsuarioEntity usuarioLogado) {
        FilaPedidoEntity pedido = new FilaPedidoEntity();
        pedido.setDataPedido(LocalDate.now());
        pedido.setHoraPedido(LocalTime.now());
        pedido.setStatusPedido(StatusPedido.FILA);
        pedido.setUsuario(usuarioLogado);
        pedido.setSenhaPedido(gerarSenha());
        return pedido;
    }

    // Busca os produtos de uma categoria
    public List<ProdutoEntity> buscarProdutosPorCategoria(String categoria) {
        return produtoRepo.buscarPorCategoria(categoria);
    }

    // Adiciona o produto selecionado ao pedido
    public void adicionarProdutoAoPedido(FilaPedidoEntity pedido, ProdutoEntity produtoEscolhido) {
        ProdutoPedidoEntity produtoPedido = new ProdutoPedidoEntity();
        produtoPedido.setPedido(pedido);
        produtoPedido.setProduto(produtoEscolhido);
        pedido.getProdutos().add(produtoPedido);
    }

    // Salva o pedido e dispara a thread de atualização dos status
    public boolean salvarPedido(FilaPedidoEntity pedido) {
        if (pedido.getProdutos().isEmpty()) {
            return false;
        }
        pedidoRepo.salvar(pedido);
        iniciarMudancaStatus(pedido);
        return true;
    }

    private String gerarSenha() {
        Random rand = new Random();
        int numero = rand.nextInt(900) + 100;
        return String.valueOf(numero);
    }

    // Thread para atualizar o status do pedido e salvar histórico
    private void iniciarMudancaStatus(FilaPedidoEntity pedido) {
        new Thread(() -> {
            try {
                Thread.sleep(1 * 60 * 1000); // Espera 1 minuto
                FilaPedidoEntity pedidoAtual = pedidoRepo.buscarPorSenha(pedido.getSenhaPedido());
                if (pedidoAtual != null && pedidoAtual.getStatusPedido() == StatusPedido.FILA) {
                    pedidoAtual.setStatusPedido(StatusPedido.PREPARANDO);
                    pedidoRepo.atualizar(pedidoAtual);
                } else {
                    return;
                }
                Thread.sleep(1 * 60 * 1000); // Espera mais 1 minuto
                pedidoAtual = pedidoRepo.buscarPorSenha(pedido.getSenhaPedido());
                if (pedidoAtual != null && pedidoAtual.getStatusPedido() == StatusPedido.PREPARANDO) {
                    pedidoAtual.setStatusPedido(StatusPedido.FINALIZADO);
                    pedidoRepo.atualizar(pedidoAtual);

                    // Cria e salva o histórico
                    HistoricoPedidoEntity historico = new HistoricoPedidoEntity();
                    historico.setSenhaPedido(pedidoAtual.getSenhaPedido());
                    historico.setDataPedido(pedidoAtual.getDataPedido());
                    historico.setHoraPedido(pedidoAtual.getHoraPedido());
                    historico.setStatusPedido(StatusPedido.FINALIZADO);
                    historico.setObservacao(pedidoAtual.getObservacao());
                    historico.setUsuario(pedidoAtual.getUsuario());
                    historicoRepo.salvar(historico);

                    if (pedidoAtual.getProdutos() != null) {
                        for (ProdutoPedidoEntity produtoPedido : pedidoAtual.getProdutos()) {
                            ProdutoHistoricoPedidoEntity prodHist = new ProdutoHistoricoPedidoEntity();
                            prodHist.setHistoricoPedido(historico);
                            prodHist.setProduto(produtoPedido.getProduto());
                            produtoHistoricoRepo.salvar(prodHist);
                        }
                    }
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); //marca a thread como interrompida
                e.printStackTrace();
            } catch (Exception e) { //pega qualquer outra exceção
                e.printStackTrace();
            }
        }).start();
    }
}