package org.example.Swing;
import org.example.controller.ProdutoController;
import org.example.model.entities.ProdutoEntity;
import org.example.model.services.ProdutoService;
import javax.swing.*;
import java.awt.*;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.layout.FormSpecs;

public class CadastrarProdutoSwing extends JFrame {

    private JTextField nomeField;
    private JComboBox<String> categoriaCombo;
    private JTextField precoField;
    private JTextField descricaoField;
    private JButton cadastrarButton;
    private JButton voltarButton;

    private ProdutoController produtoController;
    private ProdutoService produtoService;
    private JLabel label_4;
    private JLabel label_5;
    private JLabel label_6;
    private JLabel label_7;
    private JLabel label_8;
    private JLabel label_9;
    private JLabel label_10;
    private JLabel label_11;
    private JLabel label_12;
    private JLabel label_13;
    private JLabel label_14;
    private JLabel label_15;
    private JLabel label_16;
    private JLabel label_17;
    private JLabel label_18;
    private JLabel label_19;
    private JLabel label_20;
    private JLabel label_21;

    public CadastrarProdutoSwing(ProdutoController produtoController) {
        this.produtoController = produtoController;
        setTitle( "Cadastro de Produto" );
        setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
        setSize( 1200, 700 );
        setLocationRelativeTo( null );
        initComponents();
        setVisible( true );
    }

    private void initComponents() {
        JPanel panel = new JPanel();
        panel.setLayout(new FormLayout(new ColumnSpec[] {
        		ColumnSpec.decode("296px"),
        		ColumnSpec.decode("296px"),
        		ColumnSpec.decode("296px"),
        		ColumnSpec.decode("296px"),},
        	new RowSpec[] {
        		FormSpecs.LABEL_COMPONENT_GAP_ROWSPEC,
        		RowSpec.decode("94px"),
        		RowSpec.decode("94px"),
        		RowSpec.decode("94px"),
        		RowSpec.decode("94px"),
        		RowSpec.decode("94px"),
        		RowSpec.decode("94px"),
        		RowSpec.decode("94px"),}));
        
        label_4 = new JLabel("");
        panel.add(label_4, "1, 2, fill, fill");
        
        label_5 = new JLabel("");
        panel.add(label_5, "2, 2, fill, fill");
        
        label_6 = new JLabel("");
        panel.add(label_6, "3, 2, fill, fill");
        
        label_7 = new JLabel("");
        panel.add(label_7, "4, 2, fill, fill");

        JLabel label = new JLabel("Categoria:");
        panel.add(label, "1, 3, center, fill");
        String[] categorias = {"Marmitas", "Bebidas", "Porções"};
        categoriaCombo = new JComboBox<>(categorias);
        panel.add(categoriaCombo, "2, 3, default, center");
        
        label_8 = new JLabel("");
        panel.add(label_8, "3, 3, fill, fill");
        
        label_9 = new JLabel("");
        panel.add(label_9, "4, 3, fill, fill");

        JLabel label_1 = new JLabel("Nome:");
        panel.add(label_1, "1, 4, center, default");
        nomeField = new JTextField();
        panel.add(nomeField, "2, 4, default, center");
        
        label_10 = new JLabel("");
        panel.add(label_10, "3, 4, fill, fill");
        
        label_11 = new JLabel("");
        panel.add(label_11, "4, 4, fill, fill");

        JLabel label_2 = new JLabel("Preço:");
        panel.add(label_2, "1, 5, center, fill");
        precoField = new JTextField();
        panel.add(precoField, "2, 5");
        
        label_12 = new JLabel("");
        panel.add(label_12, "3, 5, fill, fill");
        
        label_13 = new JLabel("");
        panel.add(label_13, "4, 5, fill, fill");

        JLabel label_3 = new JLabel("Descrição: ");
        panel.add(label_3, "1, 6, center, fill");
        descricaoField = new JTextField();
        panel.add(descricaoField, "2, 6, default, center");
        
        label_14 = new JLabel("");
        panel.add(label_14, "3, 6, fill, fill");
        
        label_15 = new JLabel("");
        panel.add(label_15, "4, 6, fill, fill");
        
        label_16 = new JLabel("");
        panel.add(label_16, "1, 7, fill, fill");
        
        label_17 = new JLabel("");
        panel.add(label_17, "2, 7, fill, fill");
        
        label_18 = new JLabel("");
        panel.add(label_18, "3, 7, fill, fill");
        
        label_19 = new JLabel("");
        panel.add(label_19, "4, 7, fill, fill");
        categorias = new String[]{"Marmitas", "Bebidas", "Porções"};

        setContentPane(panel);

        voltarButton = new JButton("Voltar");
        voltarButton.addActionListener(e -> dispose());
        
                cadastrarButton = new JButton("Cadastrar");
                cadastrarButton.addActionListener(e -> cadastrarProduto());
                panel.add(cadastrarButton, "1, 8, center, center");
        
        label_20 = new JLabel("");
        panel.add(label_20, "2, 8, fill, fill");
        
        label_21 = new JLabel("");
        panel.add(label_21, "3, 8, fill, fill");
        panel.add(voltarButton, "4, 8, center, default");
    }

    private void cadastrarProduto() {
        try {
            String nome = nomeField.getText().trim();
            String categoria = (String) categoriaCombo.getSelectedItem();
            String descricao = descricaoField.getText().trim();
            String precoText = precoField.getText().trim();

            double preco = Double.parseDouble( precoText );

            ProdutoEntity produto = new ProdutoEntity();
            produto.setNome( nome );
            produto.setCategoria( categoria );
            produto.setDescricao( descricao );
            produto.setPreco( preco );

            produtoController.cadastrarProduto( produto );

            JOptionPane.showMessageDialog( this, "Produto cadastrado com sucesso!" );
            limparCampos();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog( this, "Preço inválido. Informe um número válido." );
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog( this, e.getMessage() );
        } catch (Exception e) {
            JOptionPane.showMessageDialog( this, "Erro inesperado: " + e.getMessage() );
        }
    }

    private void limparCampos() {
        nomeField.setText( "" );
        precoField.setText( "" );
        descricaoField.setText( "" );
    }

}