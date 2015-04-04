package gui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import comunication.SerialComm;

public class Conexao {

	private JFrame frmAbrirConexo;
	protected static SerialComm serial;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Conexao window = new Conexao();
					window.frmAbrirConexo.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Conexao() {
		this.serial = new SerialComm();
		initialize();		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmAbrirConexo = new JFrame();
		frmAbrirConexo.setResizable(false);
		frmAbrirConexo.setTitle("Abrir Conex\u00E3o");
		frmAbrirConexo.setBounds(-8, 100, 360, 173);
		frmAbrirConexo.setDefaultCloseOperation(frmAbrirConexo.EXIT_ON_CLOSE);
		frmAbrirConexo.getContentPane().setLayout(null);
		frmAbrirConexo.setVisible(true);
		
		final JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(serial.getPortList().toArray()));
		comboBox.setBounds(10, 36, 145, 20);
		frmAbrirConexo.getContentPane().add(comboBox);
		
		JLabel lblPortas = new JLabel("Portas:");
		lblPortas.setBounds(10, 11, 46, 14);
		frmAbrirConexo.getContentPane().add(lblPortas);
		
		JLabel lblTaxas = new JLabel("Taxas:");
		lblTaxas.setBounds(188, 11, 46, 14);
		frmAbrirConexo.getContentPane().add(lblTaxas);
		
		final JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setModel(new DefaultComboBoxModel(serial.getTaxList()));
		comboBox_1.setBounds(188, 36, 145, 20);
		frmAbrirConexo.getContentPane().add(comboBox_1);
		
		JButton btnNewButton = new JButton("Conectar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				boolean sucess = serial.openPort((String) comboBox.getItemAt(0),(String) comboBox_1.getItemAt(0));
				frmAbrirConexo.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE); 
				
				frmAbrirConexo.dispose();
				if (sucess) {
					JOptionPane.showMessageDialog(null, "Conexão estabelecida com sucesso",
							"Porta COM", JOptionPane.PLAIN_MESSAGE);
					MainScreen tela = new MainScreen();
				}
			}
		});
		btnNewButton.setBounds(127, 76, 89, 46);
		frmAbrirConexo.getContentPane().add(btnNewButton);
		frmAbrirConexo.setLocationRelativeTo(null);
		frmAbrirConexo.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE); 
	}
}
