package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ServerThread implements Runnable {

	private DataInputStream dis;
	private DataOutputStream dos;

	private ArrayList<DataOutputStream> dosList = new ArrayList<>();

	public ServerThread(DataInputStream dis, DataOutputStream dos, ArrayList<DataOutputStream> dosList) {
		this.dis = dis;
		this.dos = dos;
		this.dosList = dosList;
	}

	@Override
	public void run() {
		Boolean flag = true;
		while (flag) {
			try {
				String message = dis.readUTF();
				for (DataOutputStream doss : dosList) {
					if (!doss.equals(dos)) {
						doss.writeUTF(message);
					}
				}
			} catch (Exception e) {
				flag = false;
				// System.out.println(e.getMessage());
			}
		}
	}

}
