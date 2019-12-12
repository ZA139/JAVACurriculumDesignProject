package nuc.ss.shopping.entity;
import	nuc.ss.shopping.entity.Book;

import nuc.ss.shopping.datapath.DataPath;

import java.io.*;

public class BookDataSet {
	private Book[] books;
	public static int cnt=0;
	public BookDataSet() {
		cnt=0;
		try {
			File fileDic = new File(DataPath.BOOKSDATABASE);
			String[] list = fileDic.list();
			books = new Book[100];
			for(int i=0;i<list.length;i++){
				FileReader fileReader = new FileReader(DataPath.BOOKSDATABASE+"\\"+list[i]);
				BufferedReader bufferedReader = new BufferedReader(fileReader);
				String[] bookInfo = bufferedReader.readLine().split(":");
				books[cnt++] = new Book(bookInfo);
				fileReader.close();
				bufferedReader.close();
			}
		}catch (FileNotFoundException e){
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Book[] getBooks() {
		return books;
	}
}
