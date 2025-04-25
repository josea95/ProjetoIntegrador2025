package org.example.services;

import org.example.entities.ProdutoEntity;
import org.example.repository.ProdutoRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class ProdutoService {
    private final ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public static void cadastrarProduto(Scanner scanner, ProdutoRepository produtoRepository) {
        // Menu para selecionar a categoria
        String categoria = null;
        while (categoria == null) {
            System.out.println( "Escolha a categoria do produto:" );
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

        System.out.println( "Digite o nome do produto:" );
        String nome = scanner.nextLine();

        System.out.println( "Digite o preço do produto:" );
        double preco = scanner.nextDouble();
        scanner.nextLine(); // Consumir a quebra de linha

        System.out.println( "Digite a descrição do produto:" );
        String descricao = scanner.nextLine();

        System.out.println( "Digite a data de criação do produto (formato: yyyy-MM-dd):" );
        String dataCriacao = scanner.nextLine();

        // Conversão da String para LocalDate
        LocalDate dataCriacaoDate = LocalDate.parse( dataCriacao );

        // Criação do produto
        ProdutoEntity produto = new ProdutoEntity();
        produto.setNome( nome );
        produto.setPreco( preco );
        produto.setCategoria( categoria );
        produto.setDescricao( descricao );
        produto.setDataCriacao( dataCriacaoDate );

        // Salvar no banco de dados
        produtoRepository.salvar( produto );

        System.out.println( "Produto cadastrado com sucesso: Categoria: " + categoria + " - Nome: " + nome + " - R$" + preco );
    }

    public void exibirProdutosPorCategoria() {
        String[] categorias = {"Marmitas", "Bebidas", "Porções"};

        for (String categoria : categorias) {
            System.out.println( "\n=== " + categoria.toUpperCase() + " ===" );
            List<ProdutoEntity> produtos = produtoRepository.buscarPorCategoria( categoria ); // Busca os produtos da categoria

            if (produtos.isEmpty()) {
                System.out.println( "Nenhum produto encontrado nesta categoria." );
            } else {
                for (ProdutoEntity produto : produtos) {
                    System.out.println( "- " + produto.getNome() );
                }
            }
        }
    }

}


