package nuc.ss.shopping.entity;

import nuc.ss.shopping.datapath.DataPath;

import java.io.*;

public class UserDataSet {
	private static User[] users;

	public static void init() {
		users = new User[100];
		
		for(int i = 0; i < users.length; i++) {
			users[i] = null;
		}
		try {
			File fileDic = new File(DataPath.USERSDATABASE);
			String[] list = fileDic.list();
			for(int i=0;i<list.length;i++){
				FileReader fileReader = new FileReader(DataPath.USERSDATABASE+"\\"+list[i]);
				BufferedReader bufferedReader = new BufferedReader(fileReader);
				String[] userInfo = bufferedReader.readLine().split(":");
				addUser(new User(userInfo));
				fileReader.close();
				bufferedReader.close();
			}
		}catch (FileNotFoundException e){
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static boolean addUser(User user) {
		int i;
		for (i = 0; i < users.length; i++) {
			if (users[i] == null) {break;}
		}
		if (i < users.length) {
			try {
				users[i] = (User)user.clone();
				File userfile = new File(DataPath.USERSDATABASE+"\\"+user.getName()+".txt");
				if(userfile.exists()){
					;
				}else {
					FileWriter fileWriter = new FileWriter(userfile);
					fileWriter.write(user.toString());
					fileWriter.close();
				}
			} catch (FileNotFoundException ignored){

			} catch (CloneNotSupportedException | IOException e) {
				e.printStackTrace();
			}
			return true;
		} else {
			return false;
		}
		
	}

	public static User[] getUsers() {
		return users;
	}
}
