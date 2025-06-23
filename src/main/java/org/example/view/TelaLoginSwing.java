package org.example.view;

import org.example.model.entities.UsuarioEntity;
import org.example.model.services.UsuarioService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaLoginSwing extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JPasswordField passwordField;
	private UsuarioService usuarioService;
	private UsuarioEntity usuarioLogado;

	public TelaLoginSwing(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setForeground(new Color(128, 128, 0));
		contentPane.setBackground(new Color(16, 19, 44));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(47, 11, 350, 239);
		panel.setBackground(new Color(223, 239, 242));
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel lblTitulo = new JLabel("Sistema Marmitech");
		lblTitulo.setForeground(Color.BLACK);
		lblTitulo.setFont(new Font("Goudy Old Style", Font.ITALIC, 26));
		lblTitulo.setBounds(91, 0, 260, 38);
		panel.add(lblTitulo);

		JLabel lblLogin = new JLabel("Login");
		lblLogin.setFont(new Font("Arial Black", Font.PLAIN, 14));
		lblLogin.setForeground(new Color(29, 50, 196));
		lblLogin.setBounds(150, 43, 73, 20);
		panel.add(lblLogin);

		textField = new JTextField();
		textField.setForeground(new Color(111, 124, 148));
		textField.setBackground(Color.WHITE);
		textField.setBounds(91, 62, 203, 20);
		panel.add(textField);
		textField.setColumns(10);

		JLabel lblSenha = new JLabel("Senha");
		lblSenha.setForeground(new Color(24, 73, 160));
		lblSenha.setFont(new Font("Arial Black", Font.BOLD, 12));
		lblSenha.setBounds(150, 118, 46, 14);
		panel.add(lblSenha);

		passwordField = new JPasswordField();
		passwordField.setBounds(91, 137, 203, 20);
		panel.add(passwordField);

		JButton btnEntrar = new JButton("Entrar");
		btnEntrar.setBackground(new Color(170, 147, 173));
		btnEntrar.setFont(new Font("Malgun Gothic Semilight", Font.BOLD, 13));
		btnEntrar.setBounds(136, 182, 96, 27);
		panel.add(btnEntrar);

		btnEntrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String login = textField.getText();
				String senha = new String(passwordField.getPassword());

				if (login.isEmpty() || senha.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Preencha usuário e senha.", "Erro", JOptionPane.ERROR_MESSAGE);
					return;
				}

				UsuarioEntity usuarioAutenticado = usuarioService.login(login, senha);

				if (usuarioAutenticado != null) {
					JOptionPane.showMessageDialog(null, "Login realizado com sucesso! Bem-vindo, " + usuarioAutenticado.getNome(), "Sucesso", JOptionPane.INFORMATION_MESSAGE);
					usuarioLogado = usuarioAutenticado;
					dispose(); // Fecha a tela de login

					// Aqui você pode abrir a próxima tela do sistema
					// Exemplo: new TelaPrincipal().setVisible(true);

				} else {
					JOptionPane.showMessageDialog(null, "Usuário ou senha incorretos.", "Erro", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
	}

	public UsuarioEntity getUsuarioLogado() {
		return usuarioLogado;
	}
}
