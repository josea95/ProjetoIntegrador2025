package org.example.model.entities;

import javax.persistence.*;

@Entity
@Table(name = "produtos_pedidos")
public class ProdutoPedidoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_pedido", nullable = false)
    private FilaPedidoEntity pedido;

    @ManyToOne
    @JoinColumn(name = "id_produto", nullable = false)
    private ProdutoEntity produto;

    @ManyToOne
    @JoinColumn(name = "id_historico")
    private HistoricoPedidoEntity historicoPedido;

    public void setPedido(FilaPedidoEntity pedido) {
        this.pedido = pedido;
    }

    public FilaPedidoEntity getPedido() {
        return pedido;
    }

    public void setProduto(ProdutoEntity produto) {
        this.produto = produto;
    }

    public ProdutoEntity getProduto() {
        return produto;
    }
}
