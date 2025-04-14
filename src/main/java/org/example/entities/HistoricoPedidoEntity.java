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

}