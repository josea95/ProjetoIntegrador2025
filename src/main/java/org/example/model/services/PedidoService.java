package org.example.model.services;

import org.example.model.entities.*;
import org.example.model.enums.StatusPedido;
import org.example.model.repository.*;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class PedidoService {
    private final FilaPedidoRepository pedidoRepo;
    private final ProdutoRepository produtoRepo;
    private final HistoricoPedidoRepository historicoRepo;
    private final ProdutoHistoricoPedidoRepository produtoHistoricoRepo;
    private static final LocalTime HORA_RESET = LocalTime.of( 19, 50 );

    public PedidoService(EntityManager em) {
        this.pedidoRepo = new FilaPedidoRepository( em );
        this.produtoRepo = new ProdutoRepository( em );
        this.historicoRepo = new HistoricoPedidoRepository( em );
        this.produtoHistoricoRepo = new ProdutoHistoricoPedidoRepository( em );
    }

    public FilaPedidoEntity fazerPedido(UsuarioEntity usuarioLogado) {
        FilaPedidoEntity pedido = new FilaPedidoEntity();
        pedido.setDataPedido( LocalDate.now() );
        pedido.setHoraPedido( LocalTime.now() );
        pedido.setStatusPedido( StatusPedido.FILA );
        pedido.setUsuario( usuarioLogado );
        return pedido;
    }

    /**
     * Gera a proxima senha sequencial do pedido com base na última senha registrada no dia atual.
     * Se o horário atual for antes do horário de reset (definido em HORA_RESET), a senha continua do dia atual.
     * Caso contrario, começa uma nova contagem após o horário de reset.
     * Retorna -> uma String representando a nova senha no formato "001", "002", etc.
     */
    private String gerarProximaSenha() {
        LocalDate hoje = LocalDate.now();
        LocalTime agora = LocalTime.now();
        Integer ultimaSenha;

        //Verifica se o horário atual é antes do horário de reset
        if (agora.isBefore( HORA_RESET )) {
            // Busca a última senha gerada no dia atual antes do horário limite
            ultimaSenha = pedidoRepo.buscarUltimaSenhaDoDia();
        } else { // Busca a última senha considerando pedidos após o horário limite
            ultimaSenha = pedidoRepo.buscarUltimaSenhaAposHorario( hoje, HORA_RESET );
        }

        // se ultimaSenha for nula ou 0 ela começa em 1, senão soma +1
        int proximaSenha = (ultimaSenha != null ? ultimaSenha : 0) + 1;
        return String.format( "%03d", proximaSenha );
    }

    /**
     * Gera uma nova senha utilizando o metodo gerarProximaSenha()
     * e define essa senha no pedido informado.
     * parametro -> FilaPedidoEntity pedido que recebera a nova senha gerada.
     */
    public void gerarESetSenhaPedido(FilaPedidoEntity pedido) {
        String novaSenha = gerarProximaSenha();
        pedido.setSenhaPedido( novaSenha );
    }

    public List<ProdutoEntity> buscarProdutosPorCategoria(String categoria) {
        return produtoRepo.buscarPorCategoria( categoria );
    }

    public boolean salvarPedido(FilaPedidoEntity pedido) {
        if (pedido.getProdutos().isEmpty()) {
            return false;
        }
        pedidoRepo.salvar( pedido );
        return true;
    }

    public void adicionarProdutoAoPedido(FilaPedidoEntity pedido, ProdutoEntity produtoEscolhido) {
        ProdutoPedidoEntity produtoPedido = new ProdutoPedidoEntity();
        produtoPedido.setPedido( pedido );
        produtoPedido.setProduto( produtoEscolhido );
        produtoPedido.setQuantidade( 1 );
        pedido.getProdutos().add( produtoPedido );
    }
}