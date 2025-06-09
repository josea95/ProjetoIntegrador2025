package org.example.controller;
import org.example.Swing.MenuPrincipalSwing;
import org.example.model.entities.UsuarioEntity;
import org.example.model.services.FilaPedidoService;
import org.example.model.services.PedidoService;
import org.example.model.services.RelatorioService;
import org.example.view.MenuPrincipalView;
import org.example.Swing.RelatorioSwing;
import org.example.Swing.FilaPedidosSwing;

public class MenuPrincipalController {
    private final MenuPrincipalSwing menuPrincipalSwing;
    private final MenuPrincipalView menuView;
    private final PedidoController pedidoController;
    private final ProdutoController produtoController;
    private final FilaPedidoController filaController;
    private final UsuarioEntity usuarioLogado;
    private final RelatorioController relatorioController;
    private final RelatorioService relatorioService;
    private final FilaPedidoService filaPedidoService;
    private final PedidoService pedidoService;
    private final MenuPersonalizacaoProdutoController menuPersonalizacaoProdutoController; // Adicionando o controller de personalização de produtos


    /* Constructor para inicializar as dependencias:
     * - responsável por exibir o menu e ler as opções do usuário
     * - controla as ações relacionadas a pedidos
     * - controla as ações relacionadas aos produtos
     * - controla a fila de pedidos (cancelamento, listagem, historico)
     * - representa o usuário que fez login no sistema
     * -> Essas dependências são injetadas no momento da criação do controlador
     */
    public MenuPrincipalController(
            MenuPrincipalSwing menuPrincipalSwing,
            MenuPrincipalView menuView,
            PedidoController pedidoController,
            ProdutoController produtoController,
            FilaPedidoController filaController,
            UsuarioEntity usuarioLogado,
            RelatorioController relatorioController,
            PedidoService pedidoService,
            FilaPedidoService filaPedidoService,
            RelatorioService relatorioService,
            // Adicionando o controller de personalização de produtos
            MenuPersonalizacaoProdutoController menuPersonalizacaoProdutoController) {

        this.menuPrincipalSwing = menuPrincipalSwing;
        this.menuView = menuView;
        this.pedidoController = pedidoController;
        this.produtoController = produtoController;
        this.filaController = filaController;
        this.pedidoService = pedidoService;
        this.filaPedidoService = filaPedidoService;
        this.usuarioLogado = usuarioLogado;
        this.relatorioController = relatorioController;
        this.relatorioService = relatorioService;
        this.menuPersonalizacaoProdutoController = menuPersonalizacaoProdutoController; //Adicionando o controller de personalização de produtos

    }

    public void executar() {
        boolean executando = true;
        while (executando) {

            String opcao = menuView.lerOpcao();

            switch (opcao) {
                case "1":
                    pedidoController.iniciarPedido( usuarioLogado );
                    break;
                case "2":
                    filaController.cancelarPedido();
                    break;
                case "3":
                    new FilaPedidosSwing(filaPedidoService);
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
                    new RelatorioSwing(relatorioService);
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
