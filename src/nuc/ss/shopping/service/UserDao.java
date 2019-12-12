package nuc.ss.shopping.service;

import nuc.ss.shopping.entity.User;

public interface UserDao {
	/**
	 * ��ѯ���е��û���Ϣ
	 * 
	 * @return ���е��û���Ϣ
	 */
	public User[] queryAllUsers();
	
	/**
	 * �����û���Ų�ѯ�û�����ϸ��Ϣ
	 * 
	 * @param id �û����
	 * @return �û�����ϸ��Ϣ
	 */
	public User queryUserById(String id);
	
	/**
	 * �û���¼
	 * 
	 * @param id �û����
	 * @param password �û���¼����
	 * @return ����¼�ɹ����򷵻��û����󣻷��򷵻�null
	 */
	public User login(String id, String password);
	
	/**
	 * �û�ע��
	 * 
	 * @param user User����
	 * @return ��ע��ɹ����򷵻�true�����򷵻�false
	 */
	public int register(User user);
}
