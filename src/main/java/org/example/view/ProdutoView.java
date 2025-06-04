package org.example.view;

import org.example.model.entities.ProdutoEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class ProdutoView {
    private final Scanner scanner;

    public ProdutoView(Scanner scanner) {
        this.scanner = scanner;
    }

    //menu para a permissão de cadastro, atualização e deleção de produtos
    public void exibirMenu() {
        System.out.println( "\n=== Menu de Personalização ===" );
        System.out.println( "1. Cadastrar produto" );
        System.out.println( "2. Atualizar produto" );
        System.out.println( "3. Deletar produto" );
        System.out.println( "0. Sair" );
    }

    public String lerOpcao() {
        System.out.print( "Escolha uma opção: " );
        return scanner.nextLine();
    }

    //menu da personalização de produtos
    public String lerCategoria() {
        String categoria = null;
        while (categoria == null) {
            System.out.println( "Escolha a categoria:" );
            System.out.println( "1. Marmitas" );
            System.out.println( "2. Bebidas" );
            System.out.println( "3. Porções" );
            System.out.println( "0. Voltar" );
            System.out.print( "Digite o número da categoria! (ou 0 para voltar): " );
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
                case "0":
                    //volta ao menu de produtos -> que eh onde o usuario pode cadastrar, atualizar ou deletar produtos
                    System.out.println( "Voltando ao menu principal..." );
                    return "0";
                default:
                    System.out.println( "Opção inválida. Tente novamente." );
            }
        }
        return categoria;
    }

    public ProdutoEntity lerDadosProdutoParaCadastro() {
        System.out.println( "=== Cadastro de Produto ===" );

        System.out.println( "Categorias:" );
        System.out.println( "1. Marmitas" );
        System.out.println( "2. Bebidas" );
        System.out.println( "3. Porções" );
        System.out.println( "0. Voltar" );
        System.out.print( "Escolha a categoria: " );
        String opcaoCategoria = scanner.nextLine();

        String categoria = null;
        switch (opcaoCategoria) {
            case "1":
                categoria = "Marmitas";
                break;
            case "2":
                categoria = "Bebidas";
                break;
            case "3":
                categoria = "Porções";
                break;
            case "0":
                System.out.println( "Operação cancelada." );
                categoria = null;
                break;
            default:
                System.out.println( "Opção inválida." );
                categoria = null;
                break;
        }

        if (categoria == null) {
            return null;
        }

        System.out.print( "Digite o nome do produto (ou 0 para cancelar): " );
        String nome = scanner.nextLine();
        if ("0".equals( nome )) return null;

        // Validação do nome do produto, fica em loop ate que o usuario digite um preco valido ou cancele
        double preco = 0.0;
        boolean precoValido = false;

        while (!precoValido) {
            System.out.print( "Digite o preço do produto (ou 0 para cancelar): " );
            String input = scanner.nextLine();
            if ("0".equals( input )) return null;
            try {
                preco = Double.parseDouble( input );
                if (preco <= 0) {
                    System.out.println( "Preço deve ser maior que zero e positivo." );
                } else {
                    precoValido = true;
                }
            } catch (NumberFormatException e) {
                System.out.println( "Preço inválido. Digite um número decimal." );
            }
        }

        LocalDate dataCriacao = null;
        while (true) {
            System.out.print( "Digite a data de criação do produto (yyyy-MM-dd) (ou 0 para cancelar): " );
            String dataInput = scanner.nextLine();
            if ("0".equals( dataInput )) return null;
            try {
                dataCriacao = LocalDate.parse( dataInput );
                break;
            } catch (Exception e) {
                System.out.println( "Data inválida. Tente novamente." );
            }
        }

        ProdutoEntity produto = new ProdutoEntity();
        produto.setNome( nome );
        produto.setCategoria( categoria );
        produto.setPreco( preco );
        produto.setDataCriacao( dataCriacao );

        System.out.println( "Produto cadastrado com sucesso." );
        return produto;
    }


    public ProdutoEntity escolherProdutoParaAtualizacao(List<ProdutoEntity> produtos) {
        System.out.println( "=== Seleção de Produto para Atualização ===" );

        for (int i = 0; i < produtos.size(); i++) {
            ProdutoEntity p = produtos.get( i );
            System.out.println( (i + 1) + ". " + p.getNome() + " (ID: " + p.getId() + ")" );
        }

        System.out.println( "0. Voltar" );
        System.out.println( "Digite o número correspondente ao produto: " );
        String input = scanner.nextLine();
        if ("0".equals( input )) {
            return null; // Volta para o menu anterior
        }
        try {
            int escolha = Integer.parseInt( input );
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
        System.out.println( "Digite o novo nome do produto: " );
        produto.setNome( scanner.nextLine() );
        double preco = 0.0;
        boolean precoValido = false;
        while (!precoValido) {
            System.out.println( "Digite o novo preço do produto: " );
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
        System.out.println( "Digite a nova descrição do produto: " );
        produto.setDescricao( scanner.nextLine() );
        System.out.println( "Produto atualizado com sucesso." );
        return produto;
    }

    //Lê o ID do produto a ser deletado
    public Long lerIdProdutoParaDeletar(List<ProdutoEntity> produtosDaCategoria) {
        System.out.println( "=== Remover Produto ===" );

        if (produtosDaCategoria.isEmpty()) {
            System.out.println( "Nenhum produto disponível para remoção." );
            return null;
        }

        System.out.println( "Produtos disponíveis:" );
        for (ProdutoEntity produto : produtosDaCategoria) {
            System.out.println( "ID: " + produto.getId() + " | Nome: " + produto.getNome() );
        }

        System.out.print( "Digite o ID do produto a ser removido (ou 0 para cancelar): " );
        String input = scanner.nextLine();

        if ("0".equals( input )) {
            System.out.println( "Operação cancelada." );
            return null;
        }

        try {
            Long id = Long.parseLong( input );
            boolean existe = produtosDaCategoria.stream().anyMatch( p -> p.getId().equals( id ) );

            if (!existe) {
                System.out.println( "ID não encontrado na lista." );
                return null;
            }
            return id;
        } catch (NumberFormatException e) {
            System.out.println( "ID inválido. Deve ser um número." );
            return null;
        }
    }


    //Exibe os produtos disponíveis na categoria selecionada
    public void exibirProdutos(String categoria, List<ProdutoEntity> produtos) {
        System.out.println( "Produtos disponíveis na categoria " + categoria + ":" );
        for (int i = 0; i < produtos.size(); i++) {
            ProdutoEntity p = produtos.get( i );
            System.out.println( (i + 1) + ". " + p.getNome() + " - R$" + p.getPreco() );
        }
    }

    public void exibirMensagem(String mensagem) {
        System.out.println( mensagem );
    }

}




