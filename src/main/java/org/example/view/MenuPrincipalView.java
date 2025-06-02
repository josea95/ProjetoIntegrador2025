package org.example.view;

import java.util.Scanner;

public class MenuPrincipalView {
    private final Scanner scanner;

    public MenuPrincipalView(Scanner scanner) {
        this.scanner = scanner;
    }

    public void exibirMenu() {
        System.out.println("\n===== MENU PRINCIPAL =====");
        System.out.println("1. Fazer Pedido");
        System.out.println("2. Cancelar Pedido");
        System.out.println("3. Ver Fila de Pedidos");
        System.out.println("4. Personalização de Produtos");
        System.out.println("5. Pesquisar Pedido");
        System.out.println("6. Ver Histórico de Pedidos");
        System.out.println("7. Relatório de Vendas");
        System.out.println("8. Sair");
    }

    public String lerOpcao() {
        System.out.print("Escolha uma opção: ");
        return scanner.nextLine();
    }
}
