package org.example.entities;

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
}

