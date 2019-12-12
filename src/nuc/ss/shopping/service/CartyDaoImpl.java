package nuc.ss.shopping.service;

import nuc.ss.shopping.datapath.DataPath;
import nuc.ss.shopping.entity.Book;
import nuc.ss.shopping.entity.BookDataSet;
import nuc.ss.shopping.entity.User;
import nuc.ss.shopping.exception.CartyException;

import javax.swing.*;
import java.io.*;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class CartyDaoImpl implements CartyDao {
    BookDataSet bds = new BookDataSet();
    Book[] bookset = bds.getBooks();
    @Override
    public boolean buy(User user, Book book, int num) throws CartyException {
        try {
            File file =new File(DataPath.SHOPPINGCARTSDATABASE+"\\"+user.getName()+".txt");
            file.delete();
            Map<Book,Integer> books = user.getShoppingCart();
            Set<Book> keys = user.getShoppingCart().keySet();
            Iterator<Book> looper = keys.iterator();
            FileWriter fileWriter = new FileWriter(DataPath.SHOPPINGCARTSDATABASE+"\\"+user.getName()+".txt",true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            while(looper.hasNext()) {
                Book bookk = looper.next();
                bufferedWriter.write(bookk+":"+books.get(bookk));
                bufferedWriter.newLine();
            }
            bufferedWriter.close();
            fileWriter.close();
            for(int i=0;i<bookset.length;i++){
                if(book.getBid().equals(bookset[i].getBid())){
                    if(bookset[i].getNumber()-num>=0) {
                        user.getShoppingCart().setPrice(user.getShoppingCart().getPrice()+book.getPrice()*num);
                        if(user.getShoppingCart().containsKey(book)){
                            user.getShoppingCart().replace(book,user.getShoppingCart().get(book)+num);
                        }
                        else {
                            user.getShoppingCart().put(book, num);
                        }
                        bookset[i].setNumber(bookset[i].getNumber() - num);
                        File file1 = new File(DataPath.BOOKSDATABASE + "\\" + "[" + book.getBid() + "]" + book.getName() + ".txt");
                        file1.delete();
                        FileWriter fileWriter1 = new FileWriter(DataPath.BOOKSDATABASE + "\\" + "[" + book.getBid() + "]" + book.getName() + ".txt");
                        BufferedWriter bufferedWriter1 = new BufferedWriter(fileWriter1);
                        bufferedWriter1.write(bookset[i].toString());
                        bufferedWriter1.close();
                        fileWriter1.close();
                        break;
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "很抱歉，本商品目前库存不足以满足您的购买需求", "添加失败", JOptionPane.WARNING_MESSAGE);
                        return false;
                        //throw new CartyException();
                    }
                }
            }
        } catch (IOException ex) {
           ;
        }
        return true;
    }

    @Override
    public boolean remove(User user, Book book) throws CartyException {
        try {
            int num=0;
            Map<Book,Integer> books = user.getShoppingCart();
            Set<Book> keys = user.getShoppingCart().keySet();
            Iterator<Book> looper = keys.iterator();
            while(looper.hasNext()){
               Book book1=looper.next();
               if(book1.getBid().equals(book.getBid())){
                   num=books.get(book1);
                   books.remove(book1);
               }
            }
            File file =new File(DataPath.SHOPPINGCARTSDATABASE+"\\"+user.getName()+".txt");
            file.delete();
            FileWriter fileWriter = new FileWriter(DataPath.SHOPPINGCARTSDATABASE+"\\"+user.getName()+".txt",true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            while(looper.hasNext()) {
                Book bookk = looper.next();
                bufferedWriter.write(bookk+":"+books.get(bookk));
                bufferedWriter.newLine();
            }
            bufferedWriter.close();
            fileWriter.close();
            for(int i=0;i<bookset.length;i++){
                if(book.getBid().equals(bookset[i].getBid())){
                        user.getShoppingCart().setPrice(user.getShoppingCart().getPrice()-num*book.getPrice());
                        bookset[i].setNumber(bookset[i].getNumber() + num);
                        File file1 = new File(DataPath.BOOKSDATABASE + "\\" + "[" + book.getBid() + "]" + book.getName() + ".txt");
                        file1.delete();
                        FileWriter fileWriter1 = new FileWriter(DataPath.BOOKSDATABASE + "\\" + "[" + book.getBid() + "]" + book.getName() + ".txt");
                        BufferedWriter bufferedWriter1 = new BufferedWriter(fileWriter1);
                        bufferedWriter1.write(bookset[i].toString());
                        bufferedWriter1.close();
                        fileWriter1.close();
                        break;
                }
            }
        } catch (IOException ex) {
            ;
        }
        return true;
    }
}
