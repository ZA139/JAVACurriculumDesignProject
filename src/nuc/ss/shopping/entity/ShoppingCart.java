package nuc.ss.shopping.entity;

import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import nuc.ss.shopping.datapath.DataPath;
import nuc.ss.shopping.exception.CartyException;
		/*MIT License
		Copyright (c) 2019 ZA139(Charles Liu)
		Permission is hereby granted, free of charge, to any person obtaining a copy
		of this software and associated documentation files (the "Software"), to deal
		in the Software without restriction, including without limitation the rights
		to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
		copies of the Software, and to permit persons to whom the Software is
		furnished to do so, subject to the following conditions:
		The above copyright notice and this permission notice shall be included in all
		copies or substantial portions of the Software.
				THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
		IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
		FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
		AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
		LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
				OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
		SOFTWARE.*/

public class ShoppingCart extends HashMap<Book, Integer> {
	
	private static final long serialVersionUID = 1L;
	private double price = 0.0;

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public ShoppingCart(String shoppingCartDataPath, User user){
		try {
			FileWriter fileWriter = new FileWriter(DataPath.SHOPPINGCARTSDATABASE+"\\"+user.getName()+".txt");
			FileReader fileReader = new FileReader(new File(shoppingCartDataPath));
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			while((bufferedReader.readLine())!=null) {
				String[] inData = bufferedReader.readLine().split(":");
				String[] tempBuffer = new String[6];
				for(int i=0;i<6;i++){
					tempBuffer[i]=inData[i];
				}
				this.put(new Book(tempBuffer),Integer.parseInt(inData[6]));
				fileWriter.close();
				fileReader.close();
				bufferedReader.close();
			}
		}catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * ����Ʒ���빺�ﳵ
	 *
	 * @param book �鼮����
	 * @param quantity ���������
	 * @exception������������ʵ�ʿ��
	 */

	public void buy(Book book, Integer quantity) throws CartyException{
		if (book.getNumber() < quantity ) {
			throw new CartyException();
		}
		book.setNumber(book.getNumber()-quantity);
		if(this.containsKey(book)){
			this.put(book,(this.get(book)+quantity));
		}
		else {
			this.put(book, quantity);
		}
		System.out.println("��ӳɹ���");
	}
	/**
	 * ����Ʒ�ӹ��ﳵ��ɾ�� 
	 * 
	 * @param book �鼮����
	 * @exception������鼮�ڹ��ﳵ�в�����
	 */
	public void Remove(Book book) throws CartyException{
		/*MIT License
		Copyright (c) 2019 ZA139(Charles Liu)
		Permission is hereby granted, free of charge, to any person obtaining a copy
		of this software and associated documentation files (the "Software"), to deal
		in the Software without restriction, including without limitation the rights
		to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
		copies of the Software, and to permit persons to whom the Software is
		furnished to do so, subject to the following conditions:
		The above copyright notice and this permission notice shall be included in all
		copies or substantial portions of the Software.
				THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
		IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
		FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
		AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
		LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
				OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
		SOFTWARE.*/
		// �жϴ�����鼮�ڹ��ﳵ���Ƿ����
		Set<Book> keys = super.keySet();
		Iterator<Book> looper = keys.iterator();
		Book tarBook=null;
		while (looper.hasNext()){
			if(looper.next().getBid()==book.getBid()){
				tarBook=book;
				break;
			}
		}
		if(tarBook==null) {
			throw new CartyException();
		}
		// �ӹ��ﳵ��ɾ��ָ���鼮
		int quantity=this.get(book);
		this.remove(book);
		// ������
		book.setNumber(book.getNumber()+quantity);
		System.out.println("�Ƴ��ɹ���");
		return;
	}
	
	// ��ʾ���ﳵ����
	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer("");
		Set<Book> keys = super.keySet();
		Iterator<Book> looper = keys.iterator();
		
		// ѭ����ʾ���ﳵ���û�������鼮
		while(looper.hasNext()) {
			Book book = looper.next();
			buffer.append(book + " �����ˣ�" + super.get(book) + "��\r\n");
		}
		
		return buffer.toString();
	}
}
