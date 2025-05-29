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

    public void executar() {
        boolean executando = true;
        while (executando) {
            produtoView.exibirMenu();
            String opcao = produtoView.lerOpcao();

            switch (opcao) {
                case "1":
                    ProdutoEntity novoProduto = produtoView.lerDadosProdutoParaCadastro();
                    produtoService.cadastrarProduto( novoProduto );
                    produtoView.exibirMensagem( "Produto cadastrado com sucesso." );
                    break;
                case "2":
                    String categoria = produtoView.lerCategoria();  // Reutilizando método de categoria da View
                    List<ProdutoEntity> produtos = produtoService.buscarProdutosPorCategoria( categoria );

                    if (produtos.isEmpty()) {
                        produtoView.exibirMensagem( "Nenhum produto encontrado para essa categoria." );
                        break;
                    }

                    ProdutoEntity produtoSelecionado = produtoView.escolherProdutoParaAtualizacao( produtos );
                    if (produtoSelecionado == null) {
                        produtoView.exibirMensagem( "Nenhum produto selecionado." );
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
                    produtoView.exibirMensagem( "Produto atualizado com sucesso." );
                    produtoView.exibirProdutos( categoria, produtos );
                    break;

                case "3":
                    Long id = produtoView.lerIdProdutoParaDeletar();
                    if (id == null) {
                        produtoView.exibirMensagem( "ID inválido. Operação cancelada." );
                        break;
                    }
                    produtoService.deletarProduto( id );
                    produtoView.exibirMensagem( "Produto removido com sucesso." );
                    break;

                case "0":
                    executando = false;
                    produtoView.exibirMensagem( "Voltando..." );
                    break;

                default:
                    produtoView.exibirMensagem( "Opção inválida. Tente novamente." );
            }
        }
    }

}

