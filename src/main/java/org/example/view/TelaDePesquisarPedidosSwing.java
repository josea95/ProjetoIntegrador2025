package org.example.view;

import javax.swing.*;
import java.awt.*;

public class TelaDePesquisarPedidosSwing extends JFrame {
	private JTextField textField;
	public TelaDePesquisarPedidosSwing() {
		getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(128, 128, 128));
		panel.setBounds(0, 0, 436, 48);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Cliente ou ID");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		//lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel.setBounds(53, 0, 83, 38);
		panel.add(lblNewLabel);
		
		textField = new JTextField();
		textField.setBounds(146, 12, 147, 19);
		panel.add(textField);
		textField.setColumns(10);
		
		JButton btnNewButton = new JButton("Pesquisar ");
		btnNewButton.setFont(new Font("Arial", Font.PLAIN, 12));
		btnNewButton.setBounds(314, 11, 112, 21);
		panel.add(btnNewButton);
	}
    }

