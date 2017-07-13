package server;

import java.io.DataOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class ChatInformation {

	public static ArrayList<DataOutputStream> dosList = new ArrayList<>();
	public static HashMap<DataOutputStream, String> nicknameList = new HashMap<>();

	public void addDos(DataOutputStream dos) {
		dosList.add(dos);
	}
	
	public void removeDos (DataOutputStream dos) {
		dosList.remove(dos);
	}

	public void addNickname(DataOutputStream dos, String nickname) {
		nicknameList.put(dos, nickname);
	}
	
	public String getNickname (DataOutputStream dos) {
		String nickname = nicknameList.get(dos);
		return nickname;
	}
	
	public void removeNickname(DataOutputStream dos){
		nicknameList.remove(dos);
		
	}

	public static ArrayList<DataOutputStream> getDosList() {
		return dosList;
	}

	public static HashMap<DataOutputStream, String> getNicknameList() {
		return nicknameList;
	}

}