package org.example.entities;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "tb_usuarios")
public class UsuarioEntity {

    public UsuarioEntity() {

    }

    public UsuarioEntity(String nome, String email, String senha) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "login", unique = true, nullable = false)
    private String login;

    @Column(name = "senha", nullable = false)
    private String senha;

    private String nome;
    private String email;

    @OneToMany(mappedBy = "usuario")
    private List<FilaPedidoEntity> filaPedidos;

    @OneToMany(mappedBy = "usuario")
    private List<HistoricoPedidoEntity> historicoPedidos;


    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }



}

