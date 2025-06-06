package org.example.Swing;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.CompoundBorder;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import javax.swing.JPasswordField;
public class TelaLoginSwing extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JPasswordField passwordField;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaLoginSwing frame = new TelaLoginSwing();
					frame.setVisible(true);
                  frame.setLocationRelativeTo(null);				
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public TelaLoginSwing() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 700, 500);
		contentPane = new JPanel();
		contentPane.setForeground(new Color(128, 128, 0));
		contentPane.setBackground(new Color(16, 19, 44));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new CompoundBorder(new CompoundBorder(new BevelBorder(BevelBorder.RAISED, new Color(240, 240, 240), new Color(255, 255, 255), new Color(105, 105, 105), new Color(160, 160, 160)), new LineBorder(new Color(180, 180, 180))), null));
		panel.setBounds(136,24,251,227);
		panel.setToolTipText("");
		panel.setForeground(new Color(237, 143, 239));
		panel.setBounds(47, 11, 350, 239);
		panel.setBackground(new Color(223, 239, 242));
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel_2 = new JLabel("Login");
		lblNewLabel_2.setFont(new Font("Arial Black", Font.PLAIN, 14));
		lblNewLabel_2.setForeground(new Color(75, 99, 107));
		lblNewLabel_2.setBackground(new Color(201, 206, 231));
		lblNewLabel_2.setBounds(150, 43, 73, 20);
		panel.add(lblNewLabel_2);
		
		textField = new JTextField();
		textField.setForeground(new Color(72, 141, 183));
		textField.setBackground(new Color(255, 255, 255));
		textField.setBounds(91, 62, 203, 20);
		panel.add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Senha");
		lblNewLabel_1.setForeground(new Color(75, 99, 107));
		lblNewLabel_1.setBackground(new Color(186, 199, 186));
		lblNewLabel_1.setFont(new Font("Arial Black", Font.BOLD, 12));
		lblNewLabel_1.setBounds(150, 118, 46, 14);
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel = new JLabel("Sistema Marmitch");
		lblNewLabel.setForeground(new Color(0, 0, 0));
		lblNewLabel.setBounds(91, 0, 179, 38);
		lblNewLabel.setFont(new Font("Goudy Old Style", Font.ITALIC, 26));
		panel.add(lblNewLabel);
		
		JButton btnNewButton = new JButton("Entrar");
		
		btnNewButton.addActionListener(new ActionListener() {
			

			
			public void actionPerformed(ActionEvent e) {
			
				
			}
		});
		//btnNewButton.setFocusable(Color.YELLOW );
		btnNewButton.setBackground(new Color(45,67,87));
		btnNewButton.setFont(new Font("Malgun Gothic Semilight", Font.BOLD, 13));
		btnNewButton.setBackground(new Color(41, 26, 64));
		btnNewButton.setForeground(new Color(75, 99, 107));
		btnNewButton.setBounds(130, 175, 89, 23);
		panel.add(btnNewButton);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(91, 134, 203, 20);
		panel.add(passwordField);
		
	}
}