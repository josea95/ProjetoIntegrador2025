package org.example.entities;

import javax.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "tb_historico_pedidos")
public class HistoricoPedidoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome_cliente")
    private String nomeCliente;


    @Column(name = "data_pedido")
    private LocalDate dataPedido;

    @Column(name = "hora_pedido")
    private LocalTime horaPedido;

    @Column(name = "valor_pedido")
    private Float valorPedido;

    @Column(name = "senha_pedido")
    private String senhaPedido;

    @Column(columnDefinition = "TEXT")
    private String observacao;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private UsuarioEntity usuario;

    @OneToMany(mappedBy = "historicoPedido")
    private List<ProdutoPedidoEntity> produtos;

    @ManyToOne
    @JoinColumn(name = "id_historico")
    private HistoricoPedidoEntity historicoPedido;



        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getNomeCliente() {
            return nomeCliente;
        }

        public void setNomeCliente(String nomeCliente) {
            this.nomeCliente = nomeCliente;
        }

        public LocalDate getDataPedido() {
            return dataPedido;
        }

        public void setDataPedido(LocalDate dataPedido) {
            this.dataPedido = dataPedido;
        }

        public LocalTime getHoraPedido() {
            return horaPedido;
        }

        public void setHoraPedido(LocalTime horaPedido) {
            this.horaPedido = horaPedido;
        }

        public Float getValorPedido() {
            return valorPedido;
        }

        public void setValorPedido(Float valorPedido) {
            this.valorPedido = valorPedido;
        }

        public String getSenhaPedido() {
            return senhaPedido;
        }

        public void setSenhaPedido(String senhaPedido) {
            this.senhaPedido = senhaPedido;
        }

        public String getObservacao() {
            return observacao;
        }

        public void setObservacao(String observacao) {
            this.observacao = observacao;
        }

        public UsuarioEntity getUsuario() {
            return usuario;
        }

        public void setUsuario(UsuarioEntity usuario) {
            this.usuario = usuario;
        }

        public List<ProdutoPedidoEntity> getProdutos() {
            return produtos;
        }

        public void setProdutos(List<ProdutoPedidoEntity> produtos) {
            this.produtos = produtos;
        }

        public HistoricoPedidoEntity getHistoricoPedido() {
            return historicoPedido;
        }

        public void setHistoricoPedido(HistoricoPedidoEntity historicoPedido) {
            this.historicoPedido = historicoPedido;
        }
    }

