package org.example.model.entities;

import javax.persistence.*;

@Entity
@Table(name = "produtos_historico_pedidos")
public class ProdutoHistoricoPedidoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_historico_pedido", nullable = false)
    private HistoricoPedidoEntity historicoPedido;

    @ManyToOne
    @JoinColumn(name = "id_produto", nullable = false)
    private ProdutoEntity produto;


    public HistoricoPedidoEntity getHistoricoPedido() {
        return historicoPedido;
    }
    public void setHistoricoPedido(HistoricoPedidoEntity historicoPedido) {
        this.historicoPedido = historicoPedido;
    }

    public ProdutoEntity getProduto() {
        return produto;
    }
    public void setProduto(ProdutoEntity produto) {
        this.produto = produto;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

// Adiciona quantidade de produtos que foram pedidos //
    @Column(nullable = false)
    private Integer quantidade;

    // Getter e Setter
    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }


}

