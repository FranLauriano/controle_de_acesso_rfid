package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Date;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import model.Log;
import model.Person;
import comunication.DataTransfer;
import controler.BusinessException;
import controler.LogController;
import controler.PersonController;

public class MainScreen {

	private JFrame frmFbvRfid;
	private JTextField textUid;
	private JTextField textNome;
	private JTextField textCPF;
	private JTextField textMatricula;
	private JTextField textTelefone;
	private JTextField searchUid;
	private JTextField searchName;
	private JTextField searchCpf;
	public static JLabel lblPonto;

	/**
	 * Create the application.
	 */
	DataTransfer transfer = new DataTransfer(Conexao.serial.getCommPort());
	private JTable table;
	private DefaultTableModel dtm;
	private JScrollPane scroll;

	public MainScreen() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		transfer.enableReading();
		frmFbvRfid = new JFrame();
		frmFbvRfid.setResizable(false);
		frmFbvRfid.setTitle("FBV - RFID");
		frmFbvRfid.setBounds(100, 100, 500, 500);
		frmFbvRfid.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmFbvRfid.getContentPane().setLayout(null);
		frmFbvRfid.setVisible(true);
		final JButton btnBuscar = new JButton("Pesquisar Pessoa");
		final JButton btnCadastro = new JButton("Nova Pessoa");
		final JButton btnPonto = new JButton("Registrar Ponto");

		final JPanel panelCadastro = new JPanel();
		panelCadastro.setBorder(new LineBorder(Color.GRAY, 2, true));
		panelCadastro.setBounds(13, 82, 467, 347);
		frmFbvRfid.getContentPane().add(panelCadastro);
		panelCadastro.setLayout(null);

		JButton btnTagCad = new JButton("Ler Tag");
		btnTagCad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (!transfer.isEnableRead()) {
					transfer.enableReading();
				}
				Date now = new Date();
				String uid = null;
				while (uid == null) {
					uid = transfer.getUidRead();

					if ((new Date()).getSeconds() - now.getSeconds() == 3) {
						break;
					}
				}
				if (uid != null) {
					textUid.setText(uid);
				}
				transfer.disableReadingWriting();
			}
		});
		btnTagCad.setBounds(368, 59, 89, 23);
		panelCadastro.add(btnTagCad);

		textUid = new JTextField();
		textUid.setEditable(false);
		textUid.setBounds(132, 90, 325, 20);
		panelCadastro.add(textUid);
		textUid.setColumns(10);

		JLabel lblUserIdentification = new JLabel("UId:");
		lblUserIdentification.setBounds(10, 93, 134, 14);
		panelCadastro.add(lblUserIdentification);

		JLabel lblNome = new JLabel("Nome:");
		lblNome.setBounds(10, 124, 134, 14);
		panelCadastro.add(lblNome);

		textNome = new JTextField();
		textNome.setBounds(132, 121, 325, 20);
		panelCadastro.add(textNome);
		textNome.setColumns(10);

		JLabel lblTelefone = new JLabel("CPF:");
		lblTelefone.setBounds(10, 156, 134, 14);
		panelCadastro.add(lblTelefone);

		textCPF = new JTextField();
		textCPF.setBounds(132, 153, 325, 20);
		panelCadastro.add(textCPF);
		textCPF.setColumns(10);

		JLabel lblCadastro = new JLabel("CADASTRO");
		lblCadastro.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblCadastro.setHorizontalAlignment(SwingConstants.CENTER);
		lblCadastro.setBounds(10, 28, 447, 20);
		panelCadastro.add(lblCadastro);

		JLabel lblTelefone_1 = new JLabel("Telefone:");
		lblTelefone_1.setBounds(10, 219, 134, 14);
		panelCadastro.add(lblTelefone_1);

		JLabel lblMatrcula = new JLabel("Matr\u00EDcula:");
		lblMatrcula.setBounds(10, 187, 134, 14);
		panelCadastro.add(lblMatrcula);

		textMatricula = new JTextField();
		textMatricula.setColumns(10);
		textMatricula.setBounds(132, 184, 325, 20);
		panelCadastro.add(textMatricula);

		textTelefone = new JTextField();
		textTelefone.setColumns(10);
		textTelefone.setBounds(132, 216, 325, 20);
		panelCadastro.add(textTelefone);

		JButton btnCadastrar = new JButton("Cadastrar");
		btnCadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Person p = new Person();
				PersonController controller = new PersonController();

				p.setName(textNome.getText());
				p.setCpf(textCPF.getText());
				p.setEnrollment(textMatricula.getText());
				p.setPhone(textTelefone.getText());
				p.setuId(textUid.getText());
				try {
					controller.insert(p);
					JOptionPane.showMessageDialog(null, "Usuário cadastrado com sucesso.",
							"Alerta", JOptionPane.PLAIN_MESSAGE);
					textUid.setText("");
					textNome.setText("");
					textCPF.setText("");
					textMatricula.setText("");
					textTelefone.setText("");
					lblPonto.setText("");
				} catch (BusinessException e) {
					JOptionPane.showMessageDialog(null, e.getMessage(),
							"Alerta", JOptionPane.PLAIN_MESSAGE);
				}

			}
		});
		btnCadastrar.setBounds(353, 313, 104, 23);
		panelCadastro.add(btnCadastrar);
		panelCadastro.setVisible(false);

		final JPanel panelBemVindo = new JPanel();
		panelBemVindo.setBounds(0, 82, 494, 390);
		frmFbvRfid.getContentPane().add(panelBemVindo);
		panelBemVindo.setLayout(null);

		JLabel lblBemvindo = new JLabel("BEM-VINDO");
		lblBemvindo.setFont(new Font("Stencil", Font.PLAIN, 50));
		lblBemvindo.setHorizontalAlignment(SwingConstants.CENTER);
		lblBemvindo.setBounds(10, 48, 474, 88);
		panelBemVindo.add(lblBemvindo);

		lblPonto = new JLabel("");
		lblPonto.setFont(new Font("Shruti", Font.PLAIN, 18));
		lblPonto.setHorizontalAlignment(SwingConstants.CENTER);
		lblPonto.setBounds(10, 199, 474, 32);
		panelBemVindo.add(lblPonto);

		JLabel lblAproximeOCarto = new JLabel(
				"Aproxime o cart\u00E3o do leitor para bater o ponto.");
		lblAproximeOCarto.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblAproximeOCarto.setHorizontalAlignment(SwingConstants.CENTER);
		lblAproximeOCarto.setBounds(10, 133, 474, 42);
		panelBemVindo.add(lblAproximeOCarto);
		btnPonto.setEnabled(false);

		final JPanel panelResul = new JPanel();
		panelResul.setBounds(13, 276, 467, 185);
		frmFbvRfid.getContentPane().add(panelResul);
		panelResul.setVisible(false);

		JLabel lblResultadoDaBuscar = new JLabel("RESULTADO DA BUSCAR");
		lblResultadoDaBuscar.setHorizontalAlignment(SwingConstants.CENTER);
		lblResultadoDaBuscar.setFont(new Font("Tahoma", Font.BOLD, 20));
		panelResul.add(lblResultadoDaBuscar);

		table = new JTable();
		table.setFillsViewportHeight(true);
		panelResul.add(table);

		final JPanel panelBusca = new JPanel();
		panelBusca.setLayout(null);
		panelBusca.setBorder(new LineBorder(Color.GRAY, 2, true));
		panelBusca.setBounds(13, 82, 467, 194);
		frmFbvRfid.getContentPane().add(panelBusca);

		searchUid = new JTextField();
		searchUid.setEditable(false);
		searchUid.setColumns(10);
		searchUid.setBounds(132, 67, 325, 20);
		panelBusca.add(searchUid);

		JLabel label = new JLabel("UId:");
		label.setBounds(10, 70, 134, 14);
		panelBusca.add(label);

		JLabel label_1 = new JLabel("Nome:");
		label_1.setBounds(10, 101, 134, 14);
		panelBusca.add(label_1);

		searchName = new JTextField();
		searchName.setColumns(10);
		searchName.setBounds(132, 98, 325, 20);
		panelBusca.add(searchName);

		JLabel label_2 = new JLabel("CPF:");
		label_2.setBounds(10, 132, 134, 14);
		panelBusca.add(label_2);

		searchCpf = new JTextField();
		searchCpf.setColumns(10);
		searchCpf.setBounds(132, 129, 325, 20);
		panelBusca.add(searchCpf);

		JLabel lblBuscar = new JLabel("BUSCAR");
		lblBuscar.setHorizontalAlignment(SwingConstants.CENTER);
		lblBuscar.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblBuscar.setBounds(10, 11, 447, 20);
		panelBusca.add(lblBuscar);

		JButton btnBuscar_1 = new JButton("Buscar");
		btnBuscar_1.setBounds(353, 160, 104, 23);
		panelBusca.add(btnBuscar_1);

		JButton bntTagSearch = new JButton("Ler Tag");
		bntTagSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (!transfer.isEnableRead()) {
					transfer.enableReading();
				}
				Date now = new Date();
				String uid = null;
				while (uid == null) {
					uid = transfer.getUidRead();
					if ((new Date()).getSeconds() - now.getSeconds() == 3) {
						break;
					}
				}
				if (uid != null) {
					searchUid.setText(uid);
				}
				transfer.disableReadingWriting();
			}
		});
		bntTagSearch.setBounds(362, 35, 95, 23);
		panelBusca.add(bntTagSearch);
		panelBusca.setVisible(false);

		btnCadastro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelResul.setVisible(false);
				panelBemVindo.setVisible(false);
				panelBusca.setVisible(false);
				panelCadastro.setVisible(true);
				btnCadastro.setEnabled(false);
				btnBuscar.setEnabled(true);
				btnPonto.setEnabled(true);
				textUid.setText("");
				textNome.setText("");
				textCPF.setText("");
				textMatricula.setText("");
				textTelefone.setText("");
				lblPonto.setText("");
				transfer.setPoint(false);
				transfer.disableReadingWriting();
			}
		});
		btnCadastro.setBounds(177, 11, 141, 60);
		frmFbvRfid.getContentPane().add(btnCadastro);

		Component horizontalStrut = Box.createHorizontalStrut(20);
		horizontalStrut.setBounds(10, 126, 72, 1);
		frmFbvRfid.getContentPane().add(horizontalStrut);

		btnBuscar.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				panelBemVindo.setVisible(false);
				panelBusca.setVisible(true);
				panelCadastro.setVisible(false);
				btnBuscar.setEnabled(false);
				btnCadastro.setEnabled(true);
				btnPonto.setEnabled(true);
				searchUid.setText("");
				searchName.setText("");
				searchCpf.setText("");
				lblPonto.setText("");
				transfer.setPoint(false);
				transfer.disableReadingWriting();
			}
		});

		btnBuscar_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Person p = new Person();
				p.setuId(searchUid.getText());
				p.setName(searchName.getText());
				p.setCpf(searchCpf.getText());

				PersonController controller = new PersonController();
				List<Person> persons = controller.search(p);

				if (persons.size() > 0) {
					panelResul.setVisible(true);
					if (scroll == null) {
						scroll = new JScrollPane(table);
					}

					dtm = new DefaultTableModel(0, 0);
					String header[] = new String[] { "UID", "Nome", "CPF",
							"Matricula", "Telefone", "", "" };
					dtm.setColumnIdentifiers(header);
					for (Person person : persons) {

						table.setModel(dtm);
						dtm.addRow(new Object[] { person.getuId(),
								person.getName(), person.getCpf(),
								person.getEnrollment(), person.getPhone(),
								"Log", "Del" });

					}
					panelResul.add(scroll);
					AbstractAction actionBtn = new AbstractAction() {
						public void actionPerformed(ActionEvent e) {
							int linhaSelecionada = table.getSelectedRow();
							String uid = (String) table.getValueAt(
									linhaSelecionada, 0);

							LogController logController = new LogController();
							List<Log> logs = logController.search(uid);
							if (logs != null) {
								PersonController personController = new PersonController();
								Person person = new Person();
								person.setuId(uid);
								person = personController.find(person);
								dtm = new DefaultTableModel(0, 0);
								String header[] = new String[] { "UID", "Nome",
										"Data" };
								dtm.setColumnIdentifiers(header);

								for (Log log : logs) {
									table.setModel(dtm);
									dtm.addRow(new Object[] { log.getUid(),
											person.getName(),
											log.getAcessRegistry() });
								}

								panelResul.add(scroll);

							}
						}
					};

					ButtonColumn btnLog = new ButtonColumn(table, actionBtn, 5);
					btnLog.setMnemonic(KeyEvent.VK_D);

					AbstractAction actionBtnDelete = new AbstractAction() {
						public void actionPerformed(ActionEvent e) {
							int linhaSelecionada = table.getSelectedRow();
							String uid = (String) table.getValueAt(
									linhaSelecionada, 0);
							PersonController personController = new PersonController();
							Person person = new Person();
							person.setuId(uid);
							person = personController.find(person);
							personController.delete(person);
							panelResul.setEnabled(false);

						}
					};

					ButtonColumn btnBtnDelete = new ButtonColumn(table,
							actionBtnDelete, 6);
					btnBtnDelete.setMnemonic(KeyEvent.VK_D);

				} else {
					panelResul.setVisible(false);
					JOptionPane.showMessageDialog(null,
							"Nenhum resistro encontrado", "Alerta",
							JOptionPane.PLAIN_MESSAGE);
				}

			}
		});

		btnBuscar.setBounds(339, 11, 141, 60);
		frmFbvRfid.getContentPane().add(btnBuscar);

		btnPonto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				panelBusca.setVisible(false);
				panelCadastro.setVisible(false);
				panelResul.setVisible(false);
				panelBemVindo.setVisible(true);
				btnBuscar.setEnabled(true);
				btnCadastro.setEnabled(true);
				btnPonto.setEnabled(false);
				transfer.setPoint(true);
				if (!transfer.isEnableRead()) {
					transfer.enableReading();
				}
			}
		});
		btnPonto.setBounds(13, 11, 141, 60);
		frmFbvRfid.getContentPane().add(btnPonto);
		frmFbvRfid.setLocationRelativeTo(null);
		frmFbvRfid.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

	}
}
