package org.example.entity;

import javax.persistence.*;

@Entity
@Table(name = "tb_fila_pedidos")
public class FilaPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "data_pedido")
    private java.sql.Date dataPedido;

    @Column(name = "hora_pedido")
    private java.sql.Time horaPedido;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @Column(name = "status_pedido")
    private String statusPedido;

    @Column(name = "senha_pedido")
    private String senhaPedido;

    private String observacao;


}

