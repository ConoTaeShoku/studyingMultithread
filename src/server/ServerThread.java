package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ServerThread implements Runnable {

	private DataInputStream dis;
	private DataOutputStream dos;

	private ArrayList<DataOutputStream> dosList = new ArrayList<>();
	private Map<DataOutputStream, String> nicknameList = new HashMap<>();

	public ServerThread(DataInputStream dis, DataOutputStream dos, ArrayList<DataOutputStream> dosList,
			HashMap<DataOutputStream, String> nicknameList) {
		this.dis = dis;
		this.dos = dos;
		this.dosList = dosList;
		this.nicknameList = nicknameList;
	}

	@Override
	public void run() {
		boolean flag = true;
		while (flag) {
			try {
				String message = dis.readUTF();
				for (DataOutputStream doss : dosList) {
					if (!doss.equals(dos)) {
						doss.writeUTF(nicknameList.get(dos) + "> " + message);
					}
				}
			} catch (Exception e) {
				flag = false;
				// System.out.println(e.getMessage());
			}
		}
	}

}
