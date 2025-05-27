package org.example.view;

import org.example.model.entities.ProdutoEntity;
import org.example.model.repository.ProdutoRepository;
import org.example.model.services.ProdutoService;

import java.time.LocalDate;
import java.util.Scanner;

public class ProdutoView {
    private final Scanner scanner;
    private final ProdutoService produtoService;

    public ProdutoView(Scanner scanner, ProdutoService produtoService) {
        this.scanner = scanner;
        this.produtoService = produtoService;
    }

    public void cadastro() {
        System.out.println( "=== Cadastro de Produto ===" );
        String categoria = null;
        while (categoria == null) {
            System.out.println( "Escolha a categoria:" );
            System.out.println( "1. Marmitas" );
            System.out.println( "2. Bebidas" );
            System.out.println( "3. Porções" );
            System.out.print( "Digite o número da categoria: " );
            String opcao = scanner.nextLine();
            switch (opcao) {
                case "1":
                    categoria = "Marmitas";
                    break;
                case "2":
                    categoria = "Bebidas";
                    break;
                case "3":
                    categoria = "Porções";
                    break;
                default:
                    System.out.println( "Opção inválida. Tente novamente." );
            }
        }

        System.out.print( "Digite o nome do produto: " );
        String nome = scanner.nextLine();

        System.out.print( "Digite o preço do produto: " );
        double preco = scanner.nextDouble();
        scanner.nextLine();

        System.out.print( "Digite a descrição do produto: " );
        String descricao = scanner.nextLine();

        System.out.print( "Digite a data de criação do produto (yyyy-MM-dd): " );
        String dataStr = scanner.nextLine();
        LocalDate dataCriacao = LocalDate.parse( dataStr );

        ProdutoEntity produto = new ProdutoEntity();
        produto.setCategoria( categoria );
        produto.setNome( nome );
        produto.setPreco( preco );
        produto.setDescricao( descricao );
        produto.setDataCriacao( dataCriacao );

        produtoService.cadastrarProduto(produto);

    }
}
