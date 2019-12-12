package nuc.ss.shopping.service;

import nuc.ss.shopping.entity.Book;
import nuc.ss.shopping.entity.User;
import nuc.ss.shopping.exception.CartyException;

public interface CartyDao {
    public boolean buy(User user, Book book,int num)throws CartyException;
    public boolean remove(User user, Book book)throws CartyException;
}
