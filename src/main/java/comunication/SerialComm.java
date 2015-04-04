package comunication;

import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;

import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;

import javax.swing.JOptionPane;

public class SerialComm {

	private ArrayList<String> portList;
	private SerialPort commPort = null;
	private String[] taxList = { "9600" };
	private int tax;

	public SerialComm() {
		Enumeration ports = CommPortIdentifier.getPortIdentifiers();
		this.portList = new ArrayList<String>();

		while (ports.hasMoreElements()) {
			CommPortIdentifier ips = (CommPortIdentifier) ports.nextElement();

			portList.add(ips.getName());
		}

	}

	public ArrayList<String> getPortList() {
		return this.portList;
	}

	public SerialPort getCommPort() {
		return this.commPort;
	}

	public String[] getTaxList() {
		return this.taxList;
	}

	public int getTax() {
		return this.tax;
	}

	public boolean openPort(String port, String tax) {
		CommPortIdentifier portId = null;

		try {
			portId = CommPortIdentifier.getPortIdentifier(port);
			this.commPort = (SerialPort) portId.open("Comunicação serial", Integer.parseInt(tax));
			this.tax = Integer.parseInt(tax);
			System.out.println("["+new Date()+"] Comunicação aberta com a porta: " + port
					+ ". Taxa de transferencia: " + this.tax+".");
		} catch (NoSuchPortException e) {
			JOptionPane.showMessageDialog(null, "Nenhuma porta encontrada.",
					"Porta COM", JOptionPane.PLAIN_MESSAGE);
			return false;
		} catch (PortInUseException e) {
			JOptionPane.showMessageDialog(null,
					"Não foi possivel abrir porta.", "Porta COM",
					JOptionPane.PLAIN_MESSAGE);
			return false;
		}
		
		return true;

	}

	public void closePort() {
		try {

			commPort.close();

			System.out.println("["+new Date()+"] Comunicação fechada com a porta: "
					+ this.commPort+".");
			this.commPort = null;
			this.tax = 0;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,
					"Não foi possível fechar porta COM.", "Fechar porta COM",
					JOptionPane.PLAIN_MESSAGE);
		}

	}
}
