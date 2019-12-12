package nuc.ss.shopping.service;

import nuc.ss.shopping.entity.User;
import nuc.ss.shopping.entity.UserDataSet;

public class UserDaoImpl implements UserDao {
	
	@Override
	public User[] queryAllUsers() {
		return UserDataSet.getUsers();
	}

	@Override
	public User queryUserById(String id) {
		// ��ȡ���е��û���Ϣ
		User[] users = UserDataSet.getUsers();
		
		// �������е��û���Ϣ
		for (User user : users) {
			if (user != null && user.getId().equals(id))
				return user;
		}

		// û���ҵ���Ӧ���û���Ϣ
		return null;
	}

	@Override
	public User login(String id, String password) {
		User user = queryUserById(id); 
		if (user != null && user.getPassword().equals(password)) {
			return user;
		}
			
		return null;
	}

	@Override
	public int register(User user) {
		if (queryUserById(user.getId()) != null) {
			System.out.println("��������û�����Ѵ��ڣ�");
			return 1;
		}
		
		if(!(UserDataSet.addUser(user))) {
			System.out.println("ע���û������Ѿ��ﵽ���ޣ�");
			return 2;
		}
		return 0;
	}

}
