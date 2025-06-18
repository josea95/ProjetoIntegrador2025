//package org.example.Swing;
//
//import org.example.model.entities.HistoricoPedidoEntity;
//import org.example.model.entities.UsuarioEntity;
//import org.example.model.services.HistoricoPedidoService;
//
//import javax.swing.*;
//import java.util.List;
//
//public class HistoricoPedidosSwing {
//
//    private final HistoricoPedidoService historicoService;
//
//    public HistoricoPedidosSwing(HistoricoPedidoService historicoService) {
//        this.historicoService = historicoService;
//    }
//
//    public void verHistoricoPedidosSwing(UsuarioEntity usuarioLogado) {
//        List<HistoricoPedidoEntity> pedidos = historicoService.verHistoricoPedidos(usuarioLogado);
//        StringBuilder sb = new StringBuilder();
//
//        if (pedidos.isEmpty()) {
//            sb.append("Nenhum pedido FINALIZADO encontrado.\n");
//        } else {
//            for (HistoricoPedidoEntity pedido : pedidos) {
//                sb.append(getPedidoTexto(pedido));
//            }
//        }
//
//        exibirTexto(sb.toString(), "Histórico de Pedidos");
//    }
//
//    private String getPedidoTexto(HistoricoPedidoEntity pedido) {
//        return String.format(
//                "Senha: %s\nData: %s\nHora: %s\nStatus: %s\nUsuário: %s\nObservação: %s\nValor: %.2f\n\n",
//                pedido.getSenhaPedido(),
//                pedido.getDataPedido(),
//                pedido.getHoraPedido(),
//                pedido.getStatusPedido(),
//                pedido.getUsuario().getNome(),
//                pedido.getObservacao(),
//                pedido.getValorPedido()
//        );
//    }
//
//    private void exibirTexto(String texto, String titulo) {
//        JTextArea textArea = new JTextArea(texto);
//        textArea.setEditable(false);
//        JScrollPane scrollPane = new JScrollPane(textArea);
//        scrollPane.setPreferredSize(new java.awt.Dimension(600, 400));
//        JOptionPane.showMessageDialog(null, scrollPane, titulo, JOptionPane.INFORMATION_MESSAGE);
//    }
//}
package org.example.Swing;

import org.example.model.entities.HistoricoPedidoEntity;
import org.example.model.entities.UsuarioEntity;
import org.example.model.services.HistoricoPedidoService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class HistoricoPedidosSwing extends JFrame {

    private JTable table;
    private final HistoricoPedidoService historicoService;
    private final UsuarioEntity usuarioLogado;

    public HistoricoPedidosSwing(HistoricoPedidoService historicoService, UsuarioEntity usuarioLogado) {
        this.historicoService = historicoService;
        this.usuarioLogado = usuarioLogado;

        setTitle( "Histórico de Pedidos" );
        setSize( 700, 500 );
        setLocationRelativeTo( null );
        setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
        getContentPane().setLayout( null );

        // Botão Voltar
        JButton btnVoltar = new JButton( "Voltar" );
        btnVoltar.setBounds( 10, 419, 89, 23 );
        getContentPane().add( btnVoltar );

        // Botão Atualizar
        JButton btnAtualizar = new JButton( "Atualizar Histórico" );
        btnAtualizar.setBounds( 561, 419, 120, 23 );
        getContentPane().add( btnAtualizar );

        // Tabela
        table = new JTable();
        JScrollPane scrollPane = new JScrollPane( table );
        scrollPane.setBounds( 10, 11, 664, 397 );
        getContentPane().add( scrollPane );

        // Carregar dados inicialmente
        carregarDadosHistorico();

        // Ações dos botões
        btnAtualizar.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                carregarDadosHistorico();
            }
        } );

        btnVoltar.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        } );

        setVisible( true );
    }

    private void carregarDadosHistorico() {
        List<HistoricoPedidoEntity> pedidos = historicoService.verHistoricoPedidos( usuarioLogado );

        String[] colunas = {"Senha", "Data", "Hora", "Status", "Usuário", "Observação", "Valor"};
        Object[][] dados = new Object[pedidos.size()][colunas.length];

        for (int i = 0; i < pedidos.size(); i++) {
            HistoricoPedidoEntity p = pedidos.get( i );
            dados[i][0] = p.getSenhaPedido();
            dados[i][1] = p.getDataPedido();
            dados[i][2] = p.getHoraPedido();
            dados[i][3] = p.getStatusPedido();
            dados[i][4] = p.getUsuario().getNome();
            dados[i][5] = p.getObservacao();
            dados[i][6] = String.format( "%.2f", p.getValorPedido() );
        }

        DefaultTableModel model = new DefaultTableModel( dados, colunas ) {
            // Deixa as células não editáveis
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table.setModel( model );
    }
}
