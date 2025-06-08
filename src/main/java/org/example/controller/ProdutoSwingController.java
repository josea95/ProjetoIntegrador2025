package org.example.controller;

import org.example.model.entities.ProdutoEntity;
import org.example.model.services.ProdutoService;
import org.example.view.ProdutoView;

import java.util.List;
import java.util.Scanner;

public class ProdutoController {
    private final ProdutoService produtoService;
    private final ProdutoView produtoView;
    private final Scanner scanner;

    public ProdutoController(ProdutoService produtoService, ProdutoView produtoView, Scanner scanner) {
        this.produtoService = produtoService;
        this.produtoView = produtoView;
        this.scanner = scanner;
    }

    public void iniciarPersonalizacao() {
        boolean executando = true;
        while (executando) {
            produtoView.exibirMenu();
            String opcao = produtoView.lerOpcao();

            switch (opcao) {
                //Case para cadastrar um novo produto
                case "1":
                    ProdutoEntity novoProduto = produtoView.lerDadosProdutoParaCadastro();

                    if (novoProduto == null) {
                        break;
                    }
                    produtoService.cadastrarProduto( novoProduto );
                    break;
                //Case para atualizar um produto ja existente
                case "2":
                    String categoria = produtoView.lerCategoria();
                    List<ProdutoEntity> produtos = produtoService.buscarProdutosPorCategoria( categoria );

                    if (produtos.isEmpty()) {
                        produtoView.exibirMensagem( "Nenhum produto encontrado para essa categoria." );
                        break;
                    }

                    ProdutoEntity produtoSelecionado = produtoView.escolherProdutoParaAtualizacao( produtos );
                    if (produtoSelecionado == null) {
                        produtoView.exibirMensagem( "Nenhum produto selecionado. Voltando..." );
                        break;
                    }

                    ProdutoEntity novosDados = produtoView.lerDadosAtualizacao();
                    novosDados.setId( produtoSelecionado.getId() );
                    novosDados.setDataCriacao( produtoSelecionado.getDataCriacao() );
                    // Preservar a categoria do produto selecionado
                    novosDados.setCategoria( produtoSelecionado.getCategoria() );

                    // Atualiza o produto
                    produtoService.atualizarProduto( novosDados );

                    // Recarrega e exibe a lista atualizada de produtos
                    produtos = produtoService.buscarProdutosPorCategoria( categoria );

                    produtoView.exibirProdutos( categoria, produtos );
                    break;

                //Case para deletar um produto pelo id
                case "3":
                    String categoriaDel = produtoView.lerCategoria();
                    List<ProdutoEntity> produtosParaDeletar = produtoService.buscarProdutosPorCategoria( categoriaDel );

                    if (produtosParaDeletar.isEmpty()) {
                        produtoView.exibirMensagem( "Nenhum produto encontrado para essa categoria." );
                        break;
                    }

                    Long id = produtoView.lerIdProdutoParaDeletar( produtosParaDeletar );

                    if (id == null) {
                        produtoView.exibirMensagem( "Remoção cancelada." );
                        break;
                    }

                    produtoService.deletarProduto( id );
                    produtoView.exibirMensagem( "Produto removido com sucesso." );
                    break;

                //volar ao menu anterior -> para o menu principal
                case "0":
                    executando = false;
                    break;

                default:
                    produtoView.exibirMensagem( "Opção inválida. Tente novamente." );
            }
        }
    }

}
