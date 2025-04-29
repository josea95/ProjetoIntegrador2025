package org.example.entities;

import javax.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_fila_pedidos")
public class FilaPedidoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "data_pedido")
    private LocalDate dataPedido;

    @Column(name = "hora_pedido")
    private LocalTime horaPedido;

    @Column(name = "status_pedido")
    private String statusPedido;

    @Column(name = "senha_pedido")
    private String senhaPedido;

    @Column(columnDefinition = "TEXT")
    private String observacao;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private UsuarioEntity usuario;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProdutoPedidoEntity> produtos = new ArrayList<>();

    public List<ProdutoPedidoEntity> getProdutos() {
        return produtos;
    }

    // Setters and Getters
    public void setUsuario(UsuarioEntity usuario) {
        this.usuario = usuario;
    }

    public UsuarioEntity getUsuario() {
        return usuario;
    }

    public void setSenhaPedido(String senhaPedido) {
        this.senhaPedido = senhaPedido;
    }

    public String getSenhaPedido() {
        return senhaPedido;
    }

    public void setStatusPedido(String statusPedido) {
        this.statusPedido = statusPedido;
    }

    public String getStatusPedido() {
        return statusPedido;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setDataPedido(LocalDate dataPedido) {
        this.dataPedido = dataPedido;
    }

    public LocalDate getDataPedido() {
        return dataPedido;
    }

    public void setHoraPedido(LocalTime horaPedido) {
        this.horaPedido = horaPedido;
    }

    public LocalTime getHoraPedido() {
        return horaPedido;
    }
}