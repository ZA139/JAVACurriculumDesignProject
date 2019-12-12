package nuc.ss.shopping.service;

import nuc.ss.shopping.entity.Book;
import nuc.ss.shopping.exception.CartyException;

public interface BookDao {
	/**
	 * ��ѯ���е��鼮��Ϣ
	 * 
	 * @return ���е��鼮��Ϣ
	 */
	public Book[] queryAllBooks();
	
	/**
	 * �����鼮��Ų�ѯ�鼮����ϸ��Ϣ
	 * 
	 * @param id �û�������鼮���
	 * @return �鼮����ϸ��Ϣ
	 */
	public Book queryBookById(String id);
	
	public boolean buy(String bid, int number) throws CartyException;

	public boolean addBook(Book book);
}
