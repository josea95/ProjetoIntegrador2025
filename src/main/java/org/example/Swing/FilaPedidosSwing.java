package org.example.Swing;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import org.example.model.entities.FilaPedidoEntity;
import org.example.model.services.FilaPedidoService;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.example.model.enums.StatusPedido;
import javax.swing.table.TableColumn;
import java.util.List;

public class FilaPedidosSwing extends JFrame {
	private JTable table;
	private final FilaPedidoService filaPedidoService;

	public FilaPedidosSwing(FilaPedidoService filaPedidoService) {
		this.filaPedidoService = filaPedidoService;

		setTitle("Fila de Pedidos");
		setSize(700, 500);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(null);
		setVisible(true);

		// Botão VOLTAR
		JButton btnVoltar = new JButton("Voltar");
		btnVoltar.setBounds(10, 419, 89, 23);
		getContentPane().add(btnVoltar);

		// Botão ATUALIZAR
		JButton btnAtualizar = new JButton("Atualizar Fila");
		btnAtualizar.setBounds(561, 419, 110, 23);
		getContentPane().add(btnAtualizar);

		JButton btnSalvarAlteracoes = new JButton("Salvar Alterações");
		btnSalvarAlteracoes.setBounds(230, 419, 150, 23);
		getContentPane().add(btnSalvarAlteracoes);

		btnSalvarAlteracoes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				int rowCount = model.getRowCount();

				for (int i = 0; i < rowCount; i++) {
					String senhaPedido = model.getValueAt(i, 0).toString();
					String novoStatus = model.getValueAt(i, 5).toString();

					// Busca o pedido pela senha e atualiza o status
					FilaPedidoEntity pedido = filaPedidoService.pesquisarPedido(senhaPedido);
					if (pedido != null && !pedido.getStatusPedido().toString().equals(novoStatus)) {
						filaPedidoService.atualizarStatusPedido(pedido.getId(), StatusPedido.valueOf(novoStatus));
					}
				}

				JOptionPane.showMessageDialog(null, "Pedidos atualizados com sucesso!");
				carregarDadosDaFila();
			}
		});

		// Tabela
		table = new JTable();
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(10, 11, 664, 397);
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

		String[] colunas = {"Senha", "Data", "Hora", "Status", "Usuário","Novo Status"};
		Object[][] dados = new Object[pedidos.size()][colunas.length];

		for (int i = 0; i < pedidos.size(); i++) {
			FilaPedidoEntity p = pedidos.get(i);
			dados[i][0] = p.getSenhaPedido();
			dados[i][1] = p.getDataPedido();
			dados[i][2] = p.getHoraPedido();
			dados[i][3] = p.getStatusPedido().toString();
			dados[i][4] = p.getUsuario().getNome();
			dados[i][5] = p.getStatusPedido().toString();
		}

		DefaultTableModel model = new DefaultTableModel(dados, colunas);
		table.setModel(model);
		String[] statusOptions = {"FILA", "PREPARANDO", "FINALIZADO"};
		JComboBox<String> comboBox = new JComboBox<>(statusOptions);

		TableColumn novaColuna = table.getColumnModel().getColumn(5);
		novaColuna.setCellEditor(new DefaultCellEditor(comboBox));
	}
}
