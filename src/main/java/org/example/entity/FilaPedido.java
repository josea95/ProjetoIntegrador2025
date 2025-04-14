package org.example.entity;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;

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


    public void setUsuario(Usuario usuario) {
    }

    public Object getDataPedido() {
        return null;
    }

    public Object getHoraPedido() {
        return null;
    }

    public String getSenhaPedido() {
        return "";
    }

    public Object getObservacao() {
        return null;
    }

    public void setDataPedido(Date date) {
    }

    public void setHoraPedido(Time time) {
    }

    public void setStatusPedido(String emPreparo) {
    }

    public void setSenhaPedido(String m123) {
    }

    public void setObservacao(String semCebola) {

    }

    public String getStatusPedido() {
        return "";
    }
}

