package org.example.view;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import org.example.model.entities.FilaPedidoEntity;
import org.example.model.services.FilaPedidoService;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.example.model.enums.StatusPedido;
import javax.swing.table.TableColumn;
import java.util.List;
import static org.example.model.util.FormatadorUtils.formatarProdutos;

public class FilaPedidosSwing extends JFrame {
	private JTable table;
	private final FilaPedidoService filaPedidoService;

	public FilaPedidosSwing(FilaPedidoService filaPedidoService) {
		this.filaPedidoService = filaPedidoService;

		setTitle("Fila de Pedidos");
		setSize(1200, 700);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(null);
		setVisible(true);

		// Botão VOLTAR
		JButton btnVoltar = new JButton("Voltar");
		btnVoltar.setBounds(10, 627, 89, 23);
		getContentPane().add(btnVoltar);

		// Botão ATUALIZAR
		JButton btnAtualizar = new JButton("Atualizar Fila");
		btnAtualizar.setBounds(1064, 627, 110, 23);
		getContentPane().add(btnAtualizar);

		JButton btnSalvarAlteracoes = new JButton("Salvar Alterações");
		btnSalvarAlteracoes.setBounds(413, 627, 150, 23);
		getContentPane().add(btnSalvarAlteracoes);

		btnSalvarAlteracoes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				int rowCount = model.getRowCount();

				for (int i = 0; i < rowCount; i++) {
					String senhaPedido = model.getValueAt(i, 0).toString();
					Object valorStatus = model.getValueAt(i, 4);
					if (valorStatus == null) continue;

					String novoStatus = valorStatus.toString();

					// Busca o pedido pela senha e atualiza o status
					FilaPedidoEntity pedido = filaPedidoService.pesquisarPedido(senhaPedido);
					if (pedido != null && !pedido.getStatusPedido().toString().equals(novoStatus)) {
						filaPedidoService.atualizarStatusPedido(pedido.getId(), StatusPedido.valueOf(novoStatus));
					}
					if (novoStatus.equals("FINALIZADO")) {
						filaPedidoService.finalizarPedido(pedido);
					} else {
						filaPedidoService.atualizarStatusPedido(pedido.getId(), StatusPedido.valueOf(novoStatus));
					}

				}

				JOptionPane.showMessageDialog(null, "Pedidos atualizados com sucesso!");
				carregarDadosDaFila();
			}
		});

		// Tabela
		table = new JTable();
		setSize(1200,700);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(10, 11, 1200, 600);
		getContentPane().add(scrollPane);

		// Carrega dados inicialmente
		carregarDadosDaFila();

		// Atualiza ao clicar
		btnAtualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				carregarDadosDaFila();
			}
		});

		// Fecha janela ao clicar em Voltar
		btnVoltar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
	}

	private void carregarDadosDaFila() {
		List<FilaPedidoEntity> pedidos = filaPedidoService.listarFilaPedidos();

		String[] colunas = {"Senha","Produto","Observação", "Status","Novo Status"};
		Object[][] dados = new Object[pedidos.size()][colunas.length];

		for (int i = 0; i < pedidos.size(); i++) {
			FilaPedidoEntity p = pedidos.get(i);
			dados[i][0] = p.getSenhaPedido();
			dados[i][1] = 	formatarProdutos(p.getProdutos());
			dados[i][2] = p.getObservacao();
			dados[i][3] = p.getStatusPedido().toString();
		}

		DefaultTableModel model = new DefaultTableModel(dados, colunas);
		table.setModel(model);
		String[] statusOptions = {"FILA", "PREPARANDO", "FINALIZADO"};
		JComboBox<String> comboBox = new JComboBox<>(statusOptions);

		TableColumn novaColuna = table.getColumnModel().getColumn(4);
		novaColuna.setCellEditor(new DefaultCellEditor(comboBox));
	}
}
