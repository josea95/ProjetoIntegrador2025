
package org.example.view;
import org.example.model.entities.HistoricoPedidoEntity;
import org.example.model.entities.UsuarioEntity;
import org.example.model.services.HistoricoPedidoService;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import static org.example.model.util.FormatadorUtils.formatarProdutosHistorico;

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

        String[] colunas = {"Senha", "Data","Produto", "Hora", "Status", "Usuário", "Observação", "Valor"};
        Object[][] dados = new Object[pedidos.size()][colunas.length];

        for (int i = 0; i < pedidos.size(); i++) {
            HistoricoPedidoEntity p = pedidos.get( i );
            dados[i][0] = p.getSenhaPedido();
            dados[i][1] = p.getDataPedido();
            dados[i][2] = 	formatarProdutosHistorico(p.getProdutos());
            dados[i][3] = p.getHoraPedido();
            dados[i][4] = p.getStatusPedido();
            dados[i][5] = p.getUsuario().getNome();
            dados[i][6] = p.getObservacao();
            dados[i][7] = String.format( "%.2f", p.getValorPedido() );
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
