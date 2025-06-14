package org.example.model.services;

import org.example.model.entities.FilaPedidoEntity;
import org.example.model.entities.HistoricoPedidoEntity;
import org.example.model.entities.ProdutoEntity;
import org.example.model.entities.ProdutoPedidoEntity;
import org.example.model.entities.ProdutoHistoricoPedidoEntity;
import org.example.model.entities.UsuarioEntity;
import org.example.model.enums.StatusPedido;
import org.example.model.repository.*;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class PedidoService {
    // Contador para gerar numero da senha//
    private static int contador = 1;
    private final FilaPedidoRepository pedidoRepo;
    private final ProdutoRepository produtoRepo;
    private final HistoricoPedidoRepository historicoRepo;
    private final ProdutoHistoricoPedidoRepository produtoHistoricoRepo;

    // Construtor sem Scanner, pois a 'view' gerencia apenas as entradas do usuário
    public PedidoService(EntityManager em) {
        this.pedidoRepo = new FilaPedidoRepository( em );
        this.produtoRepo = new ProdutoRepository( em );
        this.historicoRepo = new HistoricoPedidoRepository( em );
        this.produtoHistoricoRepo = new ProdutoHistoricoPedidoRepository( em );
    }

    // Cria o pedido sem realizar exibições
    public FilaPedidoEntity fazerPedido(UsuarioEntity usuarioLogado) {
        FilaPedidoEntity pedido = new FilaPedidoEntity();
        pedido.setDataPedido( LocalDate.now() );
        pedido.setHoraPedido( LocalTime.now() );
        pedido.setStatusPedido( StatusPedido.FILA );
        pedido.setUsuario( usuarioLogado );
        pedido.setSenhaPedido( gerarSenha() );
        return pedido;
    }

    // Busca os produtos de uma categoria
    public List<ProdutoEntity> buscarProdutosPorCategoria(String categoria) {
        return produtoRepo.buscarPorCategoria( categoria );
    }

    // Adiciona o produto selecionado ao pedido
    public void adicionarProdutoAoPedido(FilaPedidoEntity pedido, ProdutoEntity produtoEscolhido) {
        ProdutoPedidoEntity produtoPedido = new ProdutoPedidoEntity();
        produtoPedido.setPedido( pedido );
        produtoPedido.setProduto( produtoEscolhido );
        produtoPedido.setQuantidade(1);
        pedido.getProdutos().add( produtoPedido );
    }

    // Salva o pedido e dispara a thread de atualização dos status
    public boolean salvarPedido(FilaPedidoEntity pedido) {
        if (pedido.getProdutos().isEmpty()) {
            return false;
        }
        pedidoRepo.salvar( pedido );
        return true;
    }

    public String gerarSenha() {

        return String.format("%03d", contador++);
    }

    private double calcularValorTotalPedido(FilaPedidoEntity pedido) {
        double total = 0.0;

        if (pedido.getProdutos() != null) {
            for (ProdutoPedidoEntity produtoPedido : pedido.getProdutos()) {
                ProdutoEntity produto = produtoPedido.getProduto();
                total += produto.getPreco(); // Se tiver quantidade, multiplique por ela
            }
        }

        return total;
    }

}