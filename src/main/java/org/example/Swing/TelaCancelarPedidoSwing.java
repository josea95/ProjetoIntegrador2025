package org.example.Swing;

import javax.swing.*;
import java.awt.*;

public class TelaCancelarPedidoSwing extends JFrame {

    public TelaCancelarPedidoSwing() {
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

        JButton btnCancelar = new JButton("Cancelar Pedido");
        btnCancelar.setFont(new Font("Tahoma", Font.PLAIN, 12));
        btnCancelar.setBounds(531, 411, 145, 30);
        getContentPane().add(btnCancelar);

        JButton btnVoltar = new JButton("Voltar");
        btnVoltar.setFont(new Font("Tahoma", Font.PLAIN, 12));
        btnVoltar.setBounds(10, 411, 145, 30);
        getContentPane().add(btnVoltar);

        // Ação do botão voltar
        btnVoltar.addActionListener(e -> dispose());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TelaCancelarPedidoSwing tela = new TelaCancelarPedidoSwing();
            tela.setVisible(true); // IMPORTANTE: apenas aqui!
        });
    }
}