package org.example.view;

import java.util.Scanner;

public class UsuarioView {
    private final Scanner scanner;

    public UsuarioView(Scanner scanner) {
        this.scanner = scanner;
    }

    public String solicitarLogin() {
        System.out.print("Login: ");
        return scanner.nextLine();
    }

    public String solicitarSenha() {
        System.out.print("Senha: ");
        return scanner.nextLine();
    }

    public void exibirMensagemLoginSucesso(String nome) {
        System.out.println("✅ Login realizado com sucesso! Bem-vindo, " + nome);
    }

    public void exibirMensagemLoginFalhou() {
        System.out.println("❌ Login ou senha incorretos. Tente novamente.");
    }

}
