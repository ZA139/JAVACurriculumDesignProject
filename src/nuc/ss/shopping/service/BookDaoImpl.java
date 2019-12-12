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
	/** 书籍数据集（包含书籍的相关测试数据） */
	private BookDataSet bds = null;
	
	public BookDaoImpl() {
		bds = new BookDataSet();
	}

	public BookDataSet getBds() {
		return bds;
	}

	/**
	 * 查询所有的书籍信息
	 * 
	 * @return 所有的书籍信息
	 */
	@Override
    public Book[] queryAllBooks() {
		return bds.getBooks();
	}

	/**
	 * 根据书籍编号查询书籍的详细信息
	 * 
	 * @param id 用户输入的书籍编号
	 * @return 书籍的详细信息
	 */
	@Override
	public Book queryBookById(String id){
		// 索取所有的书籍信息
		Book[] books = bds.getBooks();
		
		// 遍历所有的书籍信息
		for (Book book : books) {
			if (book.getBid().equals(id)) {
				return book;
			}
		}

		// 没有找到任何书籍信息
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
