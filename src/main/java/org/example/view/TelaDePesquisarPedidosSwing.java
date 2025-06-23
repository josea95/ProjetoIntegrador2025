package org.example.view;

import org.example.controller.FilaPedidoController;
import org.example.model.repository.FilaPedidoRepository;
import org.example.model.services.FilaPedidoService;

import org.example.model.util.CustomizerFactory;

import javax.persistence.EntityManager;

import javax.swing.*;
import java.awt.*;

public class TelaDePesquisarPedidosSwing extends JFrame {

    EntityManager em = CustomizerFactory.getEntityManager();
    FilaPedidoRepository filaPedidoRepository = new FilaPedidoRepository( em );
    FilaPedidoService filaPedidoService = new FilaPedidoService( em );
    FilaPedidoController filaPedidoController = new FilaPedidoController( filaPedidoService );

    private JTextField textField;

    public TelaDePesquisarPedidosSwing() {
        getContentPane().setLayout( null );
        setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
        setSize( 700, 500 );
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setBackground( new Color( 128, 128, 128 ) );
        panel.setBounds( 0, 0, 436, 48 );
        getContentPane().add( panel );
        panel.setLayout( null );

        JLabel lblNewLabel = new JLabel( "ID do pedido" );
        lblNewLabel.setFont( new Font( "Tahoma", Font.PLAIN, 14 ) );
        lblNewLabel.setBounds( 53, 0, 83, 38 );
        panel.add( lblNewLabel );

        textField = new JTextField();
        textField.setBounds( 146, 12, 147, 19 );
        panel.add( textField );
        textField.setColumns( 10 );

        JButton pesquisarButton = new JButton( "Pesquisar " );
        pesquisarButton.setFont( new Font( "Arial", Font.PLAIN, 12 ) );
        pesquisarButton.addActionListener( e -> {
            String senha = textField.getText();
            if (senha.isEmpty()) {
                JOptionPane.showMessageDialog( null, "Por favor, insira uma senha." );
                return;
            }
            filaPedidoController.pesquisarPedido( senha );
        } );
        pesquisarButton.setBounds( 314, 11, 112, 21 );
        panel.add( pesquisarButton );
    }
}