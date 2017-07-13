package client;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Client extends JFrame implements Runnable, ActionListener {

	private Socket socket;
	private DataInputStream dis;
	private DataOutputStream dos;

	private JTextField textField;
	private JTextArea textArea;

	public Client() {
		connect();
	}

	public void buildClientWindow() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				disconnect();
				System.exit(0);
			}
		});
		setSize(300, 400);
		setTitle("Let's Chat!");
		textField = new JTextField();
		getContentPane().add(textField, BorderLayout.SOUTH);
		textField.setColumns(10);
		textField.addActionListener(this);

		JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		textArea = new JTextArea();
		scrollPane.setViewportView(textArea);

		setVisible(true);
		new Thread(this).start();
	}

	public void connect() {
		try {
			socket = new Socket("localhost", 8989);
			dos = new DataOutputStream(socket.getOutputStream());
			dis = new DataInputStream(socket.getInputStream());

			// nickname = ci.getNickname(ci.getDos());
			buildClientWindow();
		} catch (Exception e) {
			// System.out.println(e.getMessage());
		}

	}

	public void disconnect() {
		try {
			if (socket != null) {
				socket.close();
			}
			if (dis != null) {
				dis.close();
			}
			if (dos != null) {
				dos.close();
			}
		} catch (Exception e) {
			// System.out.println(e.getMessage());
		}
	}

	public static void main(String[] args) {
		new Client();
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == textField) {
			try {
				String sending = textField.getText();
				textArea.append("my message> " + sending + "\n");
				dos.writeUTF(sending);
				textField.setText("");
			} catch (Exception e) {
				// disconnect();
				// System.out.println(e.getMessage());
			}
		}
	}

	@Override
	public void run() {
		boolean flag = true;
		while (flag) {
			try {
				textArea.append(dis.readUTF() + "\n");
			} catch (Exception e) {
				flag = false;
				disconnect();
				// System.out.println(e.getMessage());
			}
		}
	}

}