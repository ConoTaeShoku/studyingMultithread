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
	private Map<DataOutputStream, String> nicknameList = new HashMap<>();

	private boolean flag;

	public ServerThread(DataInputStream dis, DataOutputStream dos, ArrayList<DataOutputStream> dosList,
			HashMap<DataOutputStream, String> nicknameList) {
		this.flag = false;
		this.dis = dis;
		this.dos = dos;
		this.dosList = dosList;
		this.nicknameList = nicknameList;
	}

	@Override
	public void run() {
		if (!flag) {
			try {
				dos.writeUTF("+++ Welcome!! " + nicknameList.get(dos) + " +++");
			} catch (IOException e) {
				// System.out.println(e.getMessage());
			}
			flag = true;
		}
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
				System.out.println(nicknameList.get(dos) + " quits!!");
				// System.out.println(e.getMessage());
			}
		}
	}

}
