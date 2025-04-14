package org.example.entity;

import javax.persistence.*;

@Entity
@Table(name = "tb_historico_pedidos")
public class HistoricoPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "nome_cliente")
    private String nomeCliente;

    @Column(name = "data_pedido")
    private java.sql.Date dataPedido;

    @Column(name = "hora_pedido")
    private java.sql.Time horaPedido;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @Column(name = "valor_pedido")
    private Float valorPedido;

    @Column(name = "senha_pedido")
    private String senhaPedido;

    private String observacao;


    public void setUsuario(Usuario usuario) {
    }

    public void setValorPedido(float v) {
    }

    public void setObservacao(Object observacao) {
    }

    public void setSenhaPedido(String senhaPedido) {
    }

    public void setHoraPedido(Object horaPedido) {
    }

    public void setDataPedido(Object dataPedido) {
    }

    public void setNomeCliente(String nome) {

    }
}

