package org.example.entity;

import javax.persistence.*;

@Entity
@Table(name = "tb_Produtos_pedidos")
public class ProdutoPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "id_pedidos")
    private FilaPedido pedido;

    @ManyToOne
    @JoinColumn(name = "id_produtos")
    private Produto produto;
}
