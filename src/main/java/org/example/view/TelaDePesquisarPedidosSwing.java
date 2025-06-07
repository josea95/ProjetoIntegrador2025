package org.example.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class TelaDePesquisarPedidosSwing extends JFrame {

    private JTextField campoBusca;
    private JTable tabelaPedidos;

    public TelaDePesquisarPedidosSwing() {
        setTitle("Pesquisar Pedidos");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setLayout(null);

        // Painel de topo
        JPanel panel = new JPanel();
        panel.setBounds(10, 10, 760, 40);
        getContentPane().add(panel);
        panel.setLayout(null);

        JLabel lblBusca = new JLabel("Cliente ou ID:");
        lblBusca.setBounds(10, 10, 100, 20);
        panel.add(lblBusca);

        campoBusca = new JTextField();
        campoBusca.setBounds(110, 10, 400, 20);
        panel.add(campoBusca);

        JButton botaoPesquisar = new JButton("Pesquisar");
        botaoPesquisar.setBounds(530, 10, 100, 20);
        panel.add(botaoPesquisar);

        // Tabela
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 60, 760, 290);
        getContentPane().add(scrollPane);

        String[] colunas = {
            "ID do Pedido", "Nome do Cliente", "Produtos", "Valor Total", "Status", "Data e Hora"
        };

        tabelaPedidos = new JTable();
        tabelaPedidos.setModel(new DefaultTableModel(new Object[][] {}, colunas));
        scrollPane.setViewportView(tabelaPedidos);

        // Visível
        setVisible(true);
    }
}
