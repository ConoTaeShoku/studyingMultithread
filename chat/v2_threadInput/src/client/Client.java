package client;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class Client extends JFrame implements Runnable, ActionListener {

	private String nickname;

	private Socket socket;
	private DataInputStream dis;
	private DataOutputStream dos;

	private JFrame joinFrame;
	private JFrame chatFrame;

	private JTextField textField;
	private JTextArea textArea;

	private JTextField joinTextField;
	private JButton joinBtn;

	public Client() {
		connect();
		joinPage();
		chatPage();
		new Thread(this).start();
	}

	public void connect() {
		try {
			socket = new Socket("localhost", 8989);
			dos = new DataOutputStream(socket.getOutputStream());
			dis = new DataInputStream(socket.getInputStream());
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

	public void joinPage() {

		joinFrame = new JFrame();

		joinFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		joinFrame.setSize(400, 150);
		joinFrame.setResizable(false);
		joinFrame.setTitle("joinPage");
		joinFrame.getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JLabel lbl = new JLabel("Welcome~ Enter your nickname~ :D");
		lbl.setFont(new Font("Gen Jyuu Gothic Medium", Font.PLAIN, 20));
		lbl.setHorizontalAlignment(SwingConstants.CENTER);
		joinFrame.getContentPane().add(lbl);

		joinTextField = new JTextField();
		joinTextField.setColumns(10);
		joinFrame.getContentPane().add(joinTextField);

		joinBtn = new JButton("enter");
		joinBtn.addActionListener(this);
		joinFrame.getContentPane().add(joinBtn);

		joinFrame.setVisible(true);
	}

	public void chatPage() {
		chatFrame = new JFrame();

		chatFrame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				disconnect();
				System.exit(0);
			}
		});
		chatFrame.setSize(300, 400);
		chatFrame.setTitle("Let's Chat!");

		textField = new JTextField();
		textField.setColumns(10);
		textField.requestFocus();
		textField.addActionListener(this);
		chatFrame.getContentPane().add(textField, BorderLayout.SOUTH);

		JScrollPane scrollPane = new JScrollPane();
		textArea = new JTextArea();
		textArea.setEditable(false);
		scrollPane.setViewportView(textArea);
		chatFrame.getContentPane().add(scrollPane, BorderLayout.CENTER);

		chatFrame.setVisible(false);
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == textField) {
			try {
				String sending = textField.getText();
				textArea.append("my message> " + sending + "\n");
				dos.writeUTF(nickname + "> " + sending);
				textField.setText("");
			} catch (Exception e) {
				// disconnect();
				// System.out.println(e.getMessage());
			}
		}

		else if (ae.getSource() == joinBtn) {
			nickname = joinTextField.getText();
			chatFrame.setVisible(true);
			joinFrame.setVisible(false);
			textArea.append("Welcome!! " + nickname + "\n");
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