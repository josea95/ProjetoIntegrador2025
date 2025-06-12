package org.example.Swing;

import org.example.controller.FilaPedidoController;
import org.example.model.repository.FilaPedidoRepository;
import org.example.model.services.FilaPedidoService;

import org.example.model.util.CustomizerFactory;
import javax.persistence.EntityManager;

import javax.swing.*;
import java.awt.*;

public class TelaCancelarPedidoSwing extends JFrame {
    FilaPedidoController filaPedidoController;
    FilaPedidoService filaPedidoService;
    FilaPedidoRepository filaPedidoRepository;
    EntityManager em = CustomizerFactory.getEntityManager();

    public TelaCancelarPedidoSwing() {
        this.filaPedidoRepository = new FilaPedidoRepository(em);
        this.filaPedidoService = new FilaPedidoService(em);
        this.filaPedidoController = new FilaPedidoController(filaPedidoService);

        setTitle("Cancelar Pedido");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 500);
        setLocationRelativeTo(null); // Centraliza a janela
        setResizable(true);          // Permite redimensionar (ativa maximizar)

        getContentPane().setLayout(null);

        JLabel lblId = new JLabel("Senha do Pedido:");
        lblId.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblId.setBounds(33, 100, 119, 30);
        getContentPane().add(lblId);

        JTextField txtId = new JTextField();
        txtId.setBounds(162, 101, 200, 30);
        getContentPane().add(txtId);

        JButton cancelarButton = new JButton("Cancelar Pedido");
        cancelarButton.setFont(new Font("Tahoma", Font.PLAIN, 12));
        cancelarButton.addActionListener(e -> {
            String senhaPedido = txtId.getText();
                filaPedidoController.cancelarPedido(senhaPedido);

        });
        cancelarButton.setBounds(531, 411, 145, 30);
        getContentPane().add( cancelarButton );

        JButton btnVoltar = new JButton("Voltar");
        btnVoltar.setFont(new Font("Tahoma", Font.PLAIN, 12));
        btnVoltar.setBounds(10, 411, 145, 30);
        getContentPane().add(btnVoltar);

        btnVoltar.addActionListener(e -> dispose());
    }

}