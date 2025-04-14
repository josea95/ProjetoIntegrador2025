package org.example.entity;
import javax.persistence.*;

@Entity
@Table(name = "tb_usuarios")

public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nome;
    private String senha;
    private String email;
}
