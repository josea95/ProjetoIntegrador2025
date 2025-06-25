package org.example.view;

import org.example.model.entities.FilaPedidoEntity;
import org.example.model.services.FilaPedidoService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class TelaDePesquisarPedidosSwing extends JFrame {

    private JTextField campoBusca;
    private JTable tabelaPedidos;
    private FilaPedidoService filaPedidoService;

    public TelaDePesquisarPedidosSwing(FilaPedidoService filaPedidoService) {
        this.filaPedidoService = filaPedidoService;

        setTitle("Pesquisar Pedidos");
        setSize(820, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Painel superior com campo de busca e botão
        JPanel painelSuperior = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));

        JLabel lblBusca = new JLabel("Cliente ou ID:");
        campoBusca = new JTextField(30);
        JButton botaoPesquisar = new JButton("Pesquisar");

        painelSuperior.add(lblBusca);
        painelSuperior.add(campoBusca);
        painelSuperior.add(botaoPesquisar);

        // Tabela
        String[] colunas = {
                "ID do Pedido", "Nome do Cliente", "Valor Total", "Status", "Data e Hora"
        };

        DefaultTableModel modeloTabela = new DefaultTableModel(new Object[][]{}, colunas);
        tabelaPedidos = new JTable(modeloTabela);
        JScrollPane scrollPane = new JScrollPane(tabelaPedidos);

        // Botão Voltar
        JPanel painelInferior = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton botaoVoltar = new JButton("Voltar");
        painelInferior.add(botaoVoltar);

        // Adiciona os painéis ao JFrame
        add(painelSuperior, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(painelInferior, BorderLayout.SOUTH);

        // Ação do botão Pesquisar
        botaoPesquisar.addActionListener(e -> {
            String busca = campoBusca.getText().trim();
            List<FilaPedidoEntity> pedidos;

            if (busca.isEmpty()) {
                pedidos = filaPedidoService.listarFilaPedidos();
            } else {
                FilaPedidoEntity pedido = filaPedidoService.pesquisarPedido(busca);
                if (pedido != null) {
                    pedidos = List.of(pedido);
                } else {
                    JOptionPane.showMessageDialog(this, "Nenhum pedido encontrado.");
                    pedidos = List.of();
                }
            }

            atualizarTabela(pedidos);
        });

        // Ação do botão Voltar
        botaoVoltar.addActionListener(e -> dispose());

        setVisible(true);
    }

    private void atualizarTabela(List<FilaPedidoEntity> pedidos) {
        DefaultTableModel model = (DefaultTableModel) tabelaPedidos.getModel();
        model.setRowCount(0);

        for (FilaPedidoEntity pedido : pedidos) {
            Object[] row = {
                    pedido.getSenhaPedido(),
                    pedido.getUsuario().getNome(),
                    calcularValorTotal(pedido),
                    pedido.getStatusPedido().toString(),
                    pedido.getDataPedido() + " " + pedido.getHoraPedido()
            };
            model.addRow(row);
        }
    }

    private double calcularValorTotal(FilaPedidoEntity pedido) {
        double total = 0;
        if (pedido.getProdutos() != null) {
            for (var produtoPedido : pedido.getProdutos()) {
                total += produtoPedido.getProduto().getPreco() * produtoPedido.getQuantidade();
            }
        }
        return total; //teste
    }
}
