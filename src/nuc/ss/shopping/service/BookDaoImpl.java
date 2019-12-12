package nuc.ss.shopping.service;

import nuc.ss.shopping.datapath.DataPath;
import nuc.ss.shopping.entity.Book;
import nuc.ss.shopping.entity.BookDataSet;
import nuc.ss.shopping.entity.User;
import nuc.ss.shopping.exception.CartyException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class BookDaoImpl implements BookDao{
	/** �鼮���ݼ��������鼮����ز������ݣ� */
	private BookDataSet bds = null;
	
	public BookDaoImpl() {
		bds = new BookDataSet();
	}

	public BookDataSet getBds() {
		return bds;
	}

	/**
	 * ��ѯ���е��鼮��Ϣ
	 * 
	 * @return ���е��鼮��Ϣ
	 */
	@Override
    public Book[] queryAllBooks() {
		return bds.getBooks();
	}

	/**
	 * �����鼮��Ų�ѯ�鼮����ϸ��Ϣ
	 * 
	 * @param id �û�������鼮���
	 * @return �鼮����ϸ��Ϣ
	 */
	@Override
	public Book queryBookById(String id){
		// ��ȡ���е��鼮��Ϣ
		Book[] books = bds.getBooks();
		
		// �������е��鼮��Ϣ
		for (Book book : books) {
			if (book.getBid().equals(id)) {
				return book;
			}
		}

		// û���ҵ��κ��鼮��Ϣ
		return null;
	}

	@Override
	public boolean buy(String bid, int number) throws CartyException {
		Book book = queryBookById(bid);
		if (book == null) {
			return false;
		}
		if (book.getNumber() < number ) {
			throw new CartyException();
		}
		
		book.setNumber(book.getNumber() - number);
		return true;
	}

	@Override
	public boolean addBook(Book book) {
		Book[] books = bds.getBooks();
		books[BookDataSet.cnt++] = book;
		try {
			File userfile = new File(DataPath.BOOKSDATABASE+"\\["+book.getBid()+"]"+book.getName()+".txt");
			FileWriter fileWriter = new FileWriter(userfile);
			fileWriter.write(book.toString());
			fileWriter.close();
		}
		catch (FileNotFoundException e){

		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}

	public boolean remove(Book book){
		Book[] books = bds.getBooks();
		for(int i=0;i<books.length;i++){
			if(book.getBid().equals(books[i].getBid())){
				File bookFile = new File(DataPath.BOOKSDATABASE+"\\["+book.getBid()+"]"+book.getName()+".txt");
				bookFile.delete();
				return true;
			}
		}
		return false;
	}
}
