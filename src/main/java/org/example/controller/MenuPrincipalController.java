package org.example.controller;
import org.example.model.entities.UsuarioEntity;
import org.example.model.services.RelatorioService;
import org.example.view.MenuPrincipalView;
import org.example.view.RelatorioFrame;

public class MenuPrincipalController {

    private final MenuPrincipalView menuView;
    private final PedidoController pedidoController;
    private final ProdutoController produtoController;
    private final FilaPedidoController filaController;
    private final UsuarioEntity usuarioLogado;
    private final RelatorioController relatorioController;
    private final RelatorioService relatorioService;


    /* Constructor para inicializar as dependencias:
     * - responsável por exibir o menu e ler as opções do usuário
     * - controla as ações relacionadas a pedidos
     * - controla as ações relacionadas aos produtos
     * - controla a fila de pedidos (cancelamento, listagem, historico)
     * - representa o usuário que fez login no sistema
     * -> Essas dependências são injetadas no momento da criação do controlador
     */
    public MenuPrincipalController(
            MenuPrincipalView menuView,
            PedidoController pedidoController,
            ProdutoController produtoController,
            FilaPedidoController filaController,
            UsuarioEntity usuarioLogado,
            RelatorioController relatorioController,
            RelatorioService relatorioService) {

        this.menuView = menuView;
        this.pedidoController = pedidoController;
        this.produtoController = produtoController;
        this.filaController = filaController;
        this.usuarioLogado = usuarioLogado;
        this.relatorioController = relatorioController;
        this.relatorioService = relatorioService;
    }

    public void executar() {
        boolean executando = true;

        while (executando) {
            menuView.exibirMenu();
            String opcao = menuView.lerOpcao();

            switch (opcao) {
                case "1":
                    pedidoController.iniciarPedido( usuarioLogado );
                    break;
                case "2":
                    filaController.cancelarPedido();
                    break;
                case "3":
                    filaController.listarPedidos();
                    break;
                case "4":
                    produtoController.iniciarPersonalizacao();
                    break;
                case "5":
                    filaController.pesquisarPedido();
                    break;
                case "6":
                    filaController.verHistoricoPedidos( usuarioLogado );
                    break;
                case "7":
                    new RelatorioFrame(relatorioService);
                    break;
                case "8":
                    executando = false;
                    System.out.println( "Encerrando o sistema..." );
                    break;
                default:
                    System.out.println( "Opção inválida. Tente novamente." );
            }
        }
    }
}
