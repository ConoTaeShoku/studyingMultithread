package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class ServerMain {

	private Socket socket;
	private String nickname;
	private DataInputStream dis;
	private DataOutputStream dos;

	public static ArrayList<DataOutputStream> dosList = new ArrayList<>();
	public static HashMap<DataOutputStream, String> nicknameList = new HashMap<>();

	public ServerMain() {

		Scanner scanner = new Scanner(System.in);

		try {
			ServerSocket ss = new ServerSocket(8989);
			System.out.println("waiting...");
			while (true) {
				socket = ss.accept();

				System.out.print("What's your name? ");
				nickname = scanner.nextLine();
				System.out.println("Connected!! Welcome " + nickname + "!!");

				dis = new DataInputStream(socket.getInputStream());
				dos = new DataOutputStream(socket.getOutputStream());

				nicknameList.put(dos, nickname);
				dosList.add(dos);

				ServerThread st = new ServerThread(dis, dos, dosList, nicknameList);
				Thread th = new Thread(st);
				th.setDaemon(true);
				th.start();
			}
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
			System.out.println(nicknameList.get(dos) + " quits!!");
			dosList.remove(dos);
			// System.out.println(e.getMessage());
		}
	}

	public static void main(String[] args) {
		new ServerMain();
	}

}
