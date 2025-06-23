package org.example.controller;

import org.example.model.entities.UsuarioEntity;
import org.example.model.services.UsuarioService;

public class UsuarioController {
    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    public UsuarioEntity fazerLogin(String login, String senha) {
        return usuarioService.login( login, senha );
    }
}