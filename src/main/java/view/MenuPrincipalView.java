package view;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Color;

public class MenuPrincipalView {
	
	/**
	 * @wbp.parser.entryPoint
	 */
	public static void teste() {
		JFrame frmMenuPrincipal = new JFrame();
		frmMenuPrincipal.setBackground(new Color(255, 255, 255));
		frmMenuPrincipal.setTitle("Menu Principal");
		frmMenuPrincipal.getContentPane().setLayout(new GridLayout(4, 2, 0, 0));
		
		frmMenuPrincipal.setSize(400, 300);              // Define tamanho
		frmMenuPrincipal.setLocationRelativeTo(null);    // Centraliza na tela
		frmMenuPrincipal.setVisible(true);               // Exibe

		
		JButton btnNewButton_1 = new JButton("1. Fazer Pedido");
		frmMenuPrincipal.getContentPane().add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("2. Cancelar Pedido");
		frmMenuPrincipal.getContentPane().add(btnNewButton_2);
		
		JButton btnNewButton_3 = new JButton("3. Ver Fila de Pedidos");
		frmMenuPrincipal.getContentPane().add(btnNewButton_3);
		
		JButton btnNewButton_4 = new JButton("4. Personalização de Produtos");
		frmMenuPrincipal.getContentPane().add(btnNewButton_4);
		
		JButton btnNewButton_5 = new JButton("5. Pesquisar Pedido");
		frmMenuPrincipal.getContentPane().add(btnNewButton_5);
		
		JButton btnNewButton_6 = new JButton("6. Ver Histórico de Pedidos");
		frmMenuPrincipal.getContentPane().add(btnNewButton_6);
		
		JButton btnNewButton_7 = new JButton("7. Relatório de Vendas");
		frmMenuPrincipal.getContentPane().add(btnNewButton_7);
		
		JButton btnNewButton_8 = new JButton("8. Sair");
		frmMenuPrincipal.getContentPane().add(btnNewButton_8);
		
		 frmMenuPrincipal.setVisible(true);
		 
	}
	
	public static void main(String[] args) {
	    SwingUtilities.invokeLater(() -> teste());
	}

}


