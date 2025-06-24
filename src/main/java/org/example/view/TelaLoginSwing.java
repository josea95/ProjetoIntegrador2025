package org.example.view;

import org.example.model.entities.UsuarioEntity;
import org.example.model.repository.ProdutoHistoricoPedidoRepository;
import org.example.model.services.*;
import org.example.controller.UsuarioController;
import org.example.model.util.CustomizerFactory;

import javax.persistence.EntityManager;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.Font;
import javax.swing.border.CompoundBorder;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;

public class TelaLoginSwing extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField textField;
    private JPasswordField passwordField;

    public TelaLoginSwing() {
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        setBounds( 10, 11, 700, 500 );
        setLocationRelativeTo( null );
        contentPane = new JPanel();
        contentPane.setBackground( Color.gray );
        contentPane.setBorder( new EmptyBorder( 5, 5, 5, 5 ) );

        setContentPane( contentPane );
        contentPane.setLayout( null );

        JPanel panel = new JPanel();
        panel.setBounds( 177, 91, 350, 239 );
        panel.setBorder( new CompoundBorder( new CompoundBorder( new BevelBorder( BevelBorder.RAISED, new Color( 240, 240, 240 ), new Color( 255, 255, 255 ), new Color( 105, 105, 105 ), new Color( 160, 160, 160 ) ), new LineBorder( new Color( 180, 180, 180 ) ) ), null ) );
        panel.setToolTipText( "" );

        contentPane.add( panel );
        panel.setLayout( null );

        JLabel lblNewLabel_2 = new JLabel( "Login" );
        lblNewLabel_2.setFont( new Font( "Arial Black", Font.PLAIN, 14 ) );
        lblNewLabel_2.setBackground( new Color( 201, 206, 231 ) );
        lblNewLabel_2.setBounds( 150, 43, 73, 20 );
        panel.add( lblNewLabel_2 );

        textField = new JTextField();
        textField.setForeground( new Color( 72, 141, 183 ) );
        textField.setBackground( new Color( 255, 255, 255 ) );
        textField.setBounds( 91, 62, 203, 20 );
        panel.add( textField );
        textField.setColumns( 10 );

        JLabel lblNewLabel_1 = new JLabel( "Senha" );
        lblNewLabel_1.setForeground( new Color( 75, 99, 107 ) );
        lblNewLabel_1.setBackground( new Color( 186, 199, 186 ) );
        lblNewLabel_1.setFont( new Font( "Arial Black", Font.BOLD, 12 ) );
        lblNewLabel_1.setBounds( 150, 118, 46, 14 );
        panel.add( lblNewLabel_1 );

        JLabel lblNewLabel = new JLabel( "Sistema Marmitech" );
        lblNewLabel.setForeground( new Color( 0, 0, 0 ) );
        lblNewLabel.setBounds( 81, 0, 259, 38 );
        lblNewLabel.setFont( new Font( "Goudy Old Style", Font.ITALIC, 26 ) );
        panel.add( lblNewLabel );

        JButton btnNewButton = new JButton( "Entrar" );
        btnNewButton.setFont( new Font( "Malgun Gothic Semilight", Font.BOLD, 13 ) );
        btnNewButton.setForeground( new Color( 0, 0, 0 ) );
        btnNewButton.setBounds( 130, 175, 89, 23 );
        btnNewButton.addActionListener( e -> fazerLogin() );
        panel.add( btnNewButton );

        passwordField = new JPasswordField();
        passwordField.setBounds( 91, 134, 203, 20 );
        panel.add( passwordField );
    }

    // Metodo  para fazer login
    private void fazerLogin() {
        EntityManager em = CustomizerFactory.getEntityManager();

        String login = textField.getText();
        String senha = new String( passwordField.getPassword() );

        UsuarioService usuarioService = new UsuarioService( em );
        UsuarioController usuarioController = new UsuarioController( usuarioService );
        UsuarioEntity usuario = usuarioController.fazerLogin( login, senha );

        if (usuario != null) {
            JOptionPane.showMessageDialog( this, "Login bem-sucedido!" );

            PedidoService pedidoService = new PedidoService( em );
            FilaPedidoService filaPedidoService = new FilaPedidoService( em );
            HistoricoPedidoService historicoPedidoService = new HistoricoPedidoService( em );
            ProdutoHistoricoPedidoRepository produtoHistoricoPedidoRepo = new ProdutoHistoricoPedidoRepository( em );
            RelatorioService relatorioService = new RelatorioService( produtoHistoricoPedidoRepo );

            dispose();
            new MenuPrincipalSwing(
                    pedidoService,
                    usuario,
                    filaPedidoService,
                    historicoPedidoService,
                    relatorioService
            ).setVisible( true );
        } else {
            JOptionPane.showMessageDialog( this, "Login ou senha inválidos." );
        }
    }
}