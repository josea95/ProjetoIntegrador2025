package org.example.entity;

import javax.persistence.*;

@Entity
@Table(name = "tb_produtos")

public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private int id;

    private String nome;
    private String descricao;
    private Float preco;

    @Column(name = "data_criacao")
    private java.sql.Date dataCriacao;

    @Column(name = "data_atualizacao")
    private java.sql.Date dataAtualizacao;

}
