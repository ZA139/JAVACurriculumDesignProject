package nuc.ss.shopping.service;

import nuc.ss.shopping.entity.User;

public interface UserDao {
	/**
	 * 查询所有的用户信息
	 * 
	 * @return 所有的用户信息
	 */
	public User[] queryAllUsers();
	
	/**
	 * 根据用户编号查询用户的详细信息
	 * 
	 * @param id 用户编号
	 * @return 用户的详细信息
	 */
	public User queryUserById(String id);
	
	/**
	 * 用户登录
	 * 
	 * @param id 用户编号
	 * @param password 用户登录密码
	 * @return 若登录成功，则返回用户对象；否则返回null
	 */
	public User login(String id, String password);
	
	/**
	 * 用户注册
	 * 
	 * @param user User对象
	 * @return 若注册成功，则返回true，否则返回false
	 */
	public int register(User user);
}
