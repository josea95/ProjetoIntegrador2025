package org.example.entity;
import javax.persistence.*;

@Entity
@Table(name = "tb_produtos_historico_pedidos")
public class ProdutoHistoricoPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "id_historico_pedido")
    private HistoricoPedido historicoPedido;

    @ManyToOne
    @JoinColumn(name = "id_produto")
    private Produto produto;


}

