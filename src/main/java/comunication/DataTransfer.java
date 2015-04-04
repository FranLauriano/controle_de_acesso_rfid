package comunication;

import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import gui.MainScreen;

import java.awt.Color;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

import javax.swing.JOptionPane;

import model.Log;
import model.Person;
import controler.LogController;
import controler.PersonController;

public class DataTransfer implements Runnable, SerialPortEventListener {

	private InputStream input;
	private OutputStream output;
	private SerialPort port;
	private Thread threadRead;
	private boolean enableRead = false;
	private boolean enableWriting = false;
	private String uidRead;
	private boolean point = true;

	public DataTransfer(SerialPort port) {
		this.port = port;
	}

	public void setPoint(boolean point) {
		this.point = point;
	}

	public boolean isEnableRead() {
		return enableRead;
	}

	public boolean isEnableWriting() {
		return enableWriting;
	}

	public void enableWriting(String msg) {
		this.enableWriting = true;
		this.enableRead = false;
		// port.removeEventListener();
		writePort(msg);
	}

	public void enableReading() {
		this.enableWriting = false;
		this.enableRead = true;
		port.removeEventListener();
		readPort();
	}

	private void writePort(String msg) {
		if (enableWriting) {
			System.out.println("teste1");
			try {
				output = port.getOutputStream();
				// port.addEventListener(this);
			} catch (Exception e) {
				System.err.println("[" + new Date()
						+ "] Erro no fluxo de saída. ");
				System.err.println("STATUS: " + e);
			}

			try {
				output.write(msg.getBytes());
				Thread.sleep(100);
				output.flush();
				System.out.println("[" + new Date()
						+ "] Mensagem enviada com sucesso. Mensagem enviada: "
						+ msg);
			} catch (Exception e) {
				System.err.println("[" + new Date()
						+ "] Houve um erro durante o envio. ");
				System.err.println("STATUS: " + e);
			}
		}
	}

	private void readPort() {
		if (enableRead) {

			try {
				input = port.getInputStream();
				port.addEventListener(this);
				port.notifyOnDataAvailable(true);
				System.out.println("[" + new Date()
						+ "] Leitura da porta serial habilida.");
				threadRead = new Thread(this);
				threadRead.start();
			} catch (Exception e) {
				System.err.println(e);
			}
		}
	}

	public void serialEvent(SerialPortEvent ev) {
		StringBuffer bufferRead = new StringBuffer();
		System.out.println("[" + new Date()
				+ "] Ocorreu um evento na porta serial.");
		int newData = 0;

		switch (ev.getEventType()) {

		case SerialPortEvent.BI:
		case SerialPortEvent.OE:
		case SerialPortEvent.FE:
		case SerialPortEvent.PE:
		case SerialPortEvent.CD:
		case SerialPortEvent.CTS:
		case SerialPortEvent.DSR:
		case SerialPortEvent.RI:
		case SerialPortEvent.OUTPUT_BUFFER_EMPTY:
			break;
		case SerialPortEvent.DATA_AVAILABLE:
			while (newData != -1) {

				try {
					newData = input.read();
					if (newData == -1) {
						break;
					}
					if ('\r' == (char) newData) {
						bufferRead.append('\n');
					} else {
						bufferRead.append((char) newData);
					}
				} catch (IOException ioe) {
					System.out.println("Erro de leitura serial: " + ioe);
				}
			}
			System.out.println("[" + new Date()
					+ "] Recebeu dados na pota serial.");

			this.uidRead = new String(bufferRead);
			if (point) {
				PersonController personController = new PersonController();
				Person person = new Person();
				person.setuId(this.uidRead);
				this.uidRead = null;
				person = personController.find(person);
				if (person != null) {
					LogController logController = new LogController();
					Log log = new Log();
					log.setUid(person.getuId());
					logController.insert(log);
					enableWriting(person.getName());
								
					MainScreen.lblPonto.setText(person.getName()
							+ " bateu o ponto.");
					MainScreen.lblPonto.setForeground(Color.GREEN);
					Date now = new Date();
					while (true) {
						if (new Date().getSeconds() - now.getSeconds() == 3) {
							break;
						}
					}
					MainScreen.lblPonto.setText("");

				} else {
					MainScreen.lblPonto.setText("Cartão não está cadastrado.");
					MainScreen.lblPonto.setForeground(Color.RED);
					Date now = new Date();
					while (true) {
						if (new Date().getSeconds() - now.getSeconds() == 3) {
							break;
						}
					}
					MainScreen.lblPonto.setText("");
				}
			}

		}

	}

	public void run() {
		try {
			Thread.sleep(1000);
		} catch (Exception e) {
			System.out.println("Erro. Status = " + e);
		}
	}

	public String getUidRead() {
		String uid = this.uidRead;
		this.uidRead = null;
		return uid;
	}
}
