package org.example.entities;
import javax.validation.constraints.*;
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

    @NotBlank(message = "O login é obrigatório e não pode ser vazio.")
    @Column(name = "login", unique = true, nullable = false,length = 50)
    private String login;

    @NotBlank (message = "A senha é obrigatória.")
    @Column(name = "senha", nullable = false)
    private String senha;

    @NotBlank(message = "O nome é obrigatório e não pode ser vazio.")
    private String nome;

    @NotBlank(message = "O email é obrigatório e não pode ser vazio.")
    @Email(message = "Formato de email inválido.")
    @Column(nullable = false, length = 100, unique = true)
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

