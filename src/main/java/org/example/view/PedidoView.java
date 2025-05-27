package org.example.view;

import org.example.model.entities.FilaPedidoEntity;
import org.example.model.entities.ProdutoEntity;
import org.example.model.entities.UsuarioEntity;
import org.example.model.services.PedidoService;

import java.util.List;
import java.util.Scanner;

public class PedidoView {

    private final Scanner scanner;
    private final PedidoService pedidoService;

    public PedidoView(Scanner scanner, PedidoService pedidoService) {
        this.scanner = scanner;
        this.pedidoService = pedidoService;
    }

    // Metodo que centraliza a interação com o usuário para fazer o pedido
    public void iniciarPedido(UsuarioEntity usuarioLogado) {
        exibeMensagem( "Fazendo novo pedido..." );
        FilaPedidoEntity pedido = pedidoService.fazerPedido( usuarioLogado );

        boolean adicionandoProdutos = true;
        while (adicionandoProdutos) {
            exibeMenuCategorias();
            String opcao = lerEntradaObservacao( "Digite o número da categoria: " );
            if (opcao.equals( "0" )) {
                exibeMensagem( "Pedido cancelado. Nenhum pedido foi armazenado." );
                return;
            }

            String categoria = null;
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
                case "4":
                    adicionandoProdutos = false;
                    continue;
                default:
                    exibeMensagem( "Opção inválida. Tente novamente." );
                    continue;
            }

            List<ProdutoEntity> produtos = pedidoService.buscarProdutosPorCategoria( categoria );
            exibeProdutos( categoria, produtos );

            int escolha = lerEntradaDoCancelar( "Digite o número do produto que deseja adicionar (ou 0 para voltar): " );
            if (escolha == 0) {
                continue;
            }
            if (escolha < 1 || escolha > produtos.size()) {
                exibeMensagem( "Opção inválida." );
            } else {
                ProdutoEntity produtoEscolhido = produtos.get( escolha - 1 );
                pedidoService.adicionarProdutoAoPedido( pedido, produtoEscolhido );
                exibeMensagem( "Produto adicionado: " + produtoEscolhido.getNome() );
            }
        }

        String op = lerEntradaObservacao( "Deseja adicionar uma observação ao pedido? (s/n): " );
        if (op.equalsIgnoreCase( "s" )) {
            String observacao = lerEntradaObservacao( "Digite a observação: " );
            pedido.setObservacao( observacao );
        }

        String confirmacao = lerEntradaObservacao( "Pedido concluído com sucesso? 1 - sim | 2 - nao: " );
        if (confirmacao.equals( "1" )) {
            // Verifica se o carrinho está vazio antes de finalizar o pedido
            if (pedido.getProdutos().isEmpty()) {
                System.out.println( "Erro: Não é possível finalizar o pedido sem produtos no carrinho." );
                return;
            }
            if (pedidoService.salvarPedido( pedido )) {
                exibeMensagem( "Pedido com status 'FILA' salvo. Histórico atualizado." );
            }
        } else {
            exibeMensagem( "Pedido cancelado. Nenhum pedido foi armazenado." );
        }
    }

    public void exibeMensagem(String mensagem) {
        System.out.println( mensagem );
    }

    public String lerEntradaObservacao(String prompt) {
        System.out.print( prompt );
        return scanner.nextLine();
    }

    public int lerEntradaDoCancelar(String prompt) {
        System.out.print( prompt );
        int valor = scanner.nextInt();
        scanner.nextLine(); // Consumir a quebra de linha
        return valor;
    }

    public void exibeMenuCategorias() {
        System.out.println( "\nEscolha a categoria:" );
        System.out.println( "1. Marmitas" );
        System.out.println( "2. Bebidas" );
        System.out.println( "3. Porções" );
        System.out.println( "4. Finalizar pedido" );
        System.out.println( "0. Cancelar pedido" );
    }

    public void exibeProdutos(String categoria, List<ProdutoEntity> produtos) {
        if (produtos == null || produtos.isEmpty()) {
            System.out.println( "Nenhum produto encontrado nesta categoria." );
        } else {
            System.out.println( "Produtos disponíveis na categoria " + categoria + ":" );
            for (int i = 0; i < produtos.size(); i++) {
                ProdutoEntity produto = produtos.get( i );
                System.out.println( (i + 1) + ". " + produto.getNome() + " - R$" + produto.getPreco() );
            }
        }
    }
}