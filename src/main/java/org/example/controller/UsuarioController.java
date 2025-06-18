package org.example.controller;
import org.example.model.entities.UsuarioEntity;
import org.example.model.services.UsuarioService;
import org.example.view.UsuarioView;

public class UsuarioController {
    private final UsuarioService usuarioService;
    private final UsuarioView usuarioView;

    public UsuarioController(UsuarioService usuarioService, UsuarioView usuarioView) {
        this.usuarioService = usuarioService;
        this.usuarioView = usuarioView;
    }

    public UsuarioEntity realizarLogin() {
        UsuarioEntity usuarioLogado = null;

        while (usuarioLogado == null) {
            String login = usuarioView.solicitarLogin();
            String senha = usuarioView.solicitarSenha();

            usuarioLogado = usuarioService.login( login, senha );
            if (usuarioLogado != null) {
                usuarioView.exibirMensagemLoginSucesso( usuarioLogado.getNome() );
            } else {
                usuarioView.exibirMensagemLoginFalhou();
            }
        }

        return usuarioLogado;
    }

}

