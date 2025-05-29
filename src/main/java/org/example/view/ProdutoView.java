package org.example.view;

import org.example.model.entities.ProdutoEntity;

import java.util.List;
import java.util.Scanner;

public class ProdutoView {
    private final Scanner scanner;

    public ProdutoView(Scanner scanner) {
        this.scanner = scanner;
    }

    public void exibirMenu() {
        System.out.println( "\n=== Menu de Produtos ===" );
        System.out.println( "1. Cadastrar produto" );
        System.out.println( "2. Atualizar produto" );
        System.out.println( "3. Deletar produto" );
        System.out.println( "0. Sair" );
    }

    public String lerOpcao() {
        System.out.print( "Escolha uma opção: " );
        return scanner.nextLine();
    }

    public String lerCategoria() {
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
        return categoria;
    }

    public ProdutoEntity lerDadosProdutoParaCadastro() {
        ProdutoEntity produto = new ProdutoEntity();
        String categoria = lerCategoria();
        produto.setCategoria( categoria );
        System.out.print( "Digite o nome do produto: " );
        produto.setNome( scanner.nextLine() );
        double preco = 0.0;
        boolean precoValido = false;
        while (!precoValido) {
            System.out.print( "Digite o preço do produto: " );
            try {
                preco = Double.parseDouble( scanner.nextLine() );
                if (preco <= 0) {
                    System.out.println( "Preço deve ser maior que zero e positivo." );
                } else {
                    precoValido = true;
                }
            } catch (NumberFormatException e) {
                System.out.println( "Preço inválido. Digite um número decimal." );
            }
        }
        produto.setPreco( preco );
        System.out.print( "Digite a descrição do produto: " );
        produto.setDescricao( scanner.nextLine() );
        System.out.print( "Digite a data de criação do produto (yyyy-MM-dd): " );
        String dataStr = scanner.nextLine();
        produto.setDataCriacao( java.time.LocalDate.parse( dataStr ) );
        return produto;
    }

    public void exibirMensagem(String mensagem) {
        System.out.println( mensagem );
    }

    public ProdutoEntity escolherProdutoParaAtualizacao(List<ProdutoEntity> produtos) {
        System.out.println( "=== Seleção de Produto para Atualização ===" );
        for (int i = 0; i < produtos.size(); i++) {
            ProdutoEntity p = produtos.get( i );
            System.out.println( (i + 1) + ". " + p.getNome() + " (ID: " + p.getId() + ")" );
        }
        System.out.print( "Digite o número correspondente ao produto: " );
        try {
            int escolha = Integer.parseInt( scanner.nextLine() );
            if (escolha > 0 && escolha <= produtos.size()) {
                return produtos.get( escolha - 1 );
            } else {
                System.out.println( "Opção inválida." );
                return null;
            }
        } catch (NumberFormatException e) {
            System.out.println( "Entrada inválida." );
            return null;
        }
    }

    public ProdutoEntity lerDadosAtualizacao() {
        ProdutoEntity produto = new ProdutoEntity();
        System.out.println( "=== Atualização de Produto ===" );
        System.out.print( "Digite o novo nome do produto: " );
        produto.setNome( scanner.nextLine() );
        double preco = 0.0;
        boolean precoValido = false;
        while (!precoValido) {
            System.out.print( "Digite o novo preço do produto: " );
            try {
                preco = Double.parseDouble( scanner.nextLine() );
                if (preco <= 0) {
                    System.out.println( "Preço deve ser maior que zero e positivo." );
                } else {
                    precoValido = true;
                }
            } catch (NumberFormatException e) {
                System.out.println( "Preço inválido. Digite um número decimal." );
            }
        }
        produto.setPreco( preco );
        System.out.print( "Digite a nova descrição do produto: " );
        produto.setDescricao( scanner.nextLine() );
        return produto;
    }

    public Long lerIdProdutoParaDeletar() {
        System.out.print( "Digite o ID do produto a ser removido: " );
        try {
            return Long.parseLong( scanner.nextLine() );
        } catch (NumberFormatException e) {
            System.out.println( "ID inválido." );
            return null;
        }
    }

    // Método adicionado para exibir a lista de produtos atualizada
    public void exibirProdutos(String categoria, List<ProdutoEntity> produtos) {
        System.out.println( "Produtos disponíveis na categoria " + categoria + ":" );
        for (int i = 0; i < produtos.size(); i++) {
            ProdutoEntity p = produtos.get( i );
            System.out.println( (i + 1) + ". " + p.getNome() + " - R$" + p.getPreco() );
        }
    }
}
