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
		// 获取所有的用户信息
		User[] users = UserDataSet.getUsers();
		
		// 遍历所有的用户信息
		for (User user : users) {
			if (user != null && user.getId().equals(id))
				return user;
		}

		// 没有找到对应的用户信息
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
			System.out.println("您输入的用户编号已存在！");
			return 1;
		}
		
		if(!(UserDataSet.addUser(user))) {
			System.out.println("注册用户数量已经达到上限！");
			return 2;
		}
		return 0;
	}

}
