package org.example.view.swing;

import org.example.model.entities.ProdutoEntity;
import org.example.model.services.ProdutoService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class AtualizarProdutoSwing extends JFrame {

    private JComboBox<String> categoriaCombo;                  // Dropdown para selecionar categoria
    private DefaultListModel<ProdutoEntity> listModel;         // Modelo da lista que armazena produtos
    private JList<ProdutoEntity> produtosList;                 // Lista visual de produtos

    private JTextField idField;
    private JTextField nomeField;
    private JTextField precoField;
    private JTextField descricaoField;
    private JTextField dataCriacaoField;
    private JTextField categoriaField;

    private JButton buscarButton;
    private JButton selecionarButton;
    private JButton atualizarButton;
    private JButton voltarButton;


    private ProdutoService produtoService;

    // Construtor que inicializa a janela e recebe o serviço como parâmetro
    public AtualizarProdutoSwing(ProdutoService produtoService) {
        this.produtoService = produtoService;                // Atribui o serviço recebido
        setTitle( "Atualizar Produto" );                        // Define o título da janela
        setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );    // Fecha a janela sem encerrar a aplicação inteira
        setSize( 500, 400 );                                    // Define o tamanho da janela
        setLocationRelativeTo( null );                          // Centraliza a janela na tela
        initCategoriaPanel();                                 // Inicializa o painel de seleção de categoria
        setVisible( true );                                     // Torna a janela visível
    }

    // Metodo para criar e configurar o painel inicial, onde se escolhe a categoria e busca os produtos
    private void initCategoriaPanel() {
        JPanel panel = new JPanel( new BorderLayout( 5, 5 ) );

        // Cria um painel para a seleção da categoria com layout FlowLayout
        JPanel categoriaPanel = new JPanel( new FlowLayout() );
        // Adiciona um rótulo para indicar que o usuário deve selecionar uma categoria
        categoriaPanel.add( new JLabel( "Selecione a Categoria:" ) );
        // Array de categorias disponíveis
        String[] categorias = {"Marmitas", "Bebidas", "Porções"};
        // Cria o componente JComboBox com as categorias
        categoriaCombo = new JComboBox<>( categorias );
        categoriaPanel.add( categoriaCombo );
        // Cria o botão de busca para produtos
        buscarButton = new JButton( "Buscar Produtos" );
        // Associa um evento ao botão que chama o buscarProdutosPorCategoria
        buscarButton.addActionListener( e -> buscarProdutosPorCategoria() );
        categoriaPanel.add( buscarButton );
        // Adiciona o painel de categoria na parte superior do painel principal
        panel.add( categoriaPanel, BorderLayout.NORTH );

        // Inicializa o modelo da lista e o componente JList para exibir os produtos
        listModel = new DefaultListModel<>();
        produtosList = new JList<>( listModel );
        produtosList.setSelectionMode( ListSelectionModel.SINGLE_SELECTION ); // Permite seleção de apenas um item
        // Adiciona a lista dentro do JScrollPane para rolagem
        panel.add( new JScrollPane( produtosList ), BorderLayout.CENTER );

        // Cria um painel para os botões de seleção e retorno
        JPanel botoesPanel = new JPanel( new FlowLayout() );
        // Botão para selecionar um produto da lista
        selecionarButton = new JButton( "Selecionar Produto" );
        // Evento do botão que verifica se algum produto foi selecionado, se sim chama o painel de atualização
        selecionarButton.addActionListener( e -> {
            ProdutoEntity produtoSelecionado = produtosList.getSelectedValue();
            if (produtoSelecionado != null) {
                initAtualizacaoPanel( produtoSelecionado );
            } else {
                JOptionPane.showMessageDialog( this, "Selecione um produto da lista." );
            }
        } );
        botoesPanel.add( selecionarButton );
        // Botão para voltar/fechar a janela atual
        voltarButton = new JButton( "Voltar" );
        voltarButton.addActionListener( e -> dispose() );
        botoesPanel.add( voltarButton );
        panel.add( botoesPanel, BorderLayout.SOUTH );

        // Define o conteúdo da janela para o painel criado e atualiza a interface
        setContentPane( panel );
        revalidate();
        repaint();
    }

    // Metodo para buscar os produtos pela categoria selecionada no JComboBox
    private void buscarProdutosPorCategoria() {
        // Obtém a categoria selecionada
        String categoriaSelecionada = (String) categoriaCombo.getSelectedItem();
        // Chama o serviço para buscar os produtos da categoria
        List<ProdutoEntity> filtrados = produtoService.buscarProdutosPorCategoria( categoriaSelecionada );
        listModel.clear(); // Limpa a lista atual
        if (filtrados == null || filtrados.isEmpty()) {
            // Exibe mensagem se nenhum produto for encontrado
            JOptionPane.showMessageDialog( this, "Nenhum produto encontrado para a categoria " + categoriaSelecionada );
        } else {
            // Adiciona cada produto encontrado ao modelo da lista
            filtrados.forEach( listModel::addElement );
        }
    }

    // Metodo para criar o painel de atualização do produto selecionado
    private void initAtualizacaoPanel(ProdutoEntity produto) {
        JPanel panel = new JPanel( new GridLayout( 8, 2, 10, 10 ) );

        // Exibe o ID do produto (campo não editável)
        panel.add( new JLabel( "ID do Produto:" ) );
        idField = new JTextField( String.valueOf( produto.getId() ) );
        idField.setEditable( false );
        panel.add( idField );

        // Exibe a data de criação do produto (formata a data para padrão ISO_LOCAL_DATE)
        panel.add( new JLabel( "Data de Criação:" ) );
        dataCriacaoField = new JTextField( produto.getDataCriacao().format( DateTimeFormatter.ISO_LOCAL_DATE ) );
        dataCriacaoField.setEditable( false );
        panel.add( dataCriacaoField );

        // Exibe a categoria do produto (campo não editável)
        panel.add( new JLabel( "Categoria:" ) );
        categoriaField = new JTextField( produto.getCategoria() );
        categoriaField.setEditable( false );
        panel.add( categoriaField );

        // Campo para atualizar o nome do produto
        panel.add( new JLabel( "Novo Nome:" ) );
        nomeField = new JTextField( produto.getNome() );
        panel.add( nomeField );

        // Campo para atualizar o preço do produto
        panel.add( new JLabel( "Novo Preço:" ) );
        precoField = new JTextField( String.valueOf( produto.getPreco() ) );
        panel.add( precoField );

        // Campo para atualizar a descrição do produto (aceita descrição vazia)
        panel.add( new JLabel( "Nova Descrição:" ) );
        descricaoField = new JTextField( produto.getDescricao() == null ? "" : produto.getDescricao() );
        panel.add( descricaoField );

        // Botão para confirmar a atualização
        atualizarButton = new JButton( "Atualizar" );
        atualizarButton.addActionListener( this::atualizarProduto );
        panel.add( atualizarButton );

        // Botão para voltar à tela de seleção de categoria
        voltarButton = new JButton( "Voltar" );
        voltarButton.addActionListener( e -> initCategoriaPanel() );
        panel.add( voltarButton );

        // Define o painel de atualização como conteúdo da janela e atualiza a interface
        setContentPane( panel );
        revalidate();
        repaint();
    }

    private void atualizarProduto(ActionEvent e) {
        // Coleta os dados informados pelo usuário nos campos do formulário
        String novoNome = nomeField.getText().trim();
        String precoText = precoField.getText().trim();
        String novaDescricao = descricaoField.getText().trim();

        // Verifica se algum campo obrigatório está vazio
        if (novoNome.isEmpty() || precoText.isEmpty() || novaDescricao.isEmpty()) {
            JOptionPane.showMessageDialog( this, "Todos os campos devem ser preenchidos." );
            return;
        }

        try {
            // Converte o valor do preço para double e valida se é positivo
            double novoPreco = Double.parseDouble( precoText );
            if (novoPreco <= 0) {
                JOptionPane.showMessageDialog( this, "O preço deve ser maior que zero." );
                return;
            }
            // Converte o valor do ID para Long
            Long id = Long.parseLong( idField.getText() );
            // Cria uma nova instância de ProdutoEntity e atualiza os atributos
            ProdutoEntity produtoAtualizado = new ProdutoEntity();
            produtoAtualizado.setId( id );
            produtoAtualizado.setNome( novoNome );
            produtoAtualizado.setPreco( novoPreco );
            produtoAtualizado.setDescricao( novaDescricao );

            // Atualizo a data de criação a partir do campo
            produtoAtualizado.setDataCriacao(
                    java.time.LocalDate.parse( dataCriacaoField.getText(), DateTimeFormatter.ISO_LOCAL_DATE )
            );
            // Reatribui a categoria do produto
            produtoAtualizado.setCategoria( categoriaField.getText() );

            // Define a data de atualização como a data atual
            produtoAtualizado.setDataAtualizacao( java.time.LocalDate.now() );

            // Chama o serviço para persistir a atualização do produto
            produtoService.atualizarProduto( produtoAtualizado );

            // Exibe mensagem de sucesso para o usuario
            JOptionPane.showMessageDialog( this, "Produto atualizado com sucesso!" );
            // Retorna para o painel de seleção
            initCategoriaPanel();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog( this, "Preço inválido." );
        }
    }
}