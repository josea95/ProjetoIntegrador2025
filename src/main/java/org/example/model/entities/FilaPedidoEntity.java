
package org.example.model.entities;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.example.model.enums.StatusPedido;

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

    @Enumerated(EnumType.STRING) // ao inves de aparecer 0,1,2 vai aparecer fila, preparando, finalizado
    @Column(name = "status_pedido")
    private StatusPedido statusPedido;

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

    // Setters e Getters
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

    public StatusPedido getStatusPedido() {
        return statusPedido;
    }

    public void setStatusPedido(StatusPedido statusPedido) {
        this.statusPedido = statusPedido;
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