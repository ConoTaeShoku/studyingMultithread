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
	private DataInputStream dis;
	private DataOutputStream dos;
	public static ArrayList<DataOutputStream> dosList = new ArrayList<>();

	public ServerMain() {
		try {
			ServerSocket ss = new ServerSocket(8989);
			System.out.println("waiting...");
			while (true) {
				socket = ss.accept();
				System.out.println("Connected!");
				dis = new DataInputStream(socket.getInputStream());
				dos = new DataOutputStream(socket.getOutputStream());
				dosList.add(dos);
				ServerThread st = new ServerThread(dis, dos, dosList);
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
			dosList.remove(dos);
			// System.out.println(e.getMessage());
		}
	}

	public static void main(String[] args) {
		new ServerMain();
	}

}
