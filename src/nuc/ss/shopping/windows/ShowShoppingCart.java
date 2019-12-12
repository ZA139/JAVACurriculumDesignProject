package nuc.ss.shopping.windows;

import nuc.ss.shopping.entity.Book;
import nuc.ss.shopping.entity.BookDataSet;
import nuc.ss.shopping.entity.User;
import nuc.ss.shopping.exception.CartyException;
import nuc.ss.shopping.service.CartyDao;
import nuc.ss.shopping.service.CartyDaoImpl;
import nuc.ss.shopping.tools.SetWindowCenter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public class ShowShoppingCart extends JFrame {
    CartyDao cartyDao = new CartyDaoImpl();
    BookDataSet bds = new BookDataSet();
    Book[] books = bds.getBooks();

    JButton ButtonBuy = new JButton("结账");
    JButton ButtonRemove = new JButton("移除");
    JLabel costLabel = null;
    User user = null;

    public ShowShoppingCart(User user) throws HeadlessException {
        super(user.getName()+"的购物车");
        this.user = user;
        costLabel = new JLabel("您当前花费："+String.valueOf(user.getShoppingCart().getPrice()));
        init();
    }

    private void init() {
        this.setLayout(new FlowLayout());

        JTable table = new JTable();
        JScrollPane pane = new JScrollPane();
        pane.getViewport().add(table);
        initTableModel(table);

        pane.setBounds(10, 50, 330, 300);
        this.getContentPane().add(pane);

        ButtonBuy.addActionListener(e->{
            CheckOut checkOut = new CheckOut(user);
        });

        ButtonRemove.addActionListener(e->{
            if(table.getSelectedRow()!=-1) {
                String tempId = table.getValueAt(table.getSelectedRow(),0).toString();
                for(int i =0; i <books.length ; i++) {
                    if(books[i]==null) {
                        break;
                    }
                    if(books[i].getBid().equals(tempId)){
                        books[i].setNumber(0);
                        try {
                            cartyDao.remove(user,books[i]);
                            costLabel.setText("您当前花费："+String.valueOf(user.getShoppingCart().getPrice()));
                        } catch (CartyException ex) {
                            ex.printStackTrace();
                        }
                        initTableModel(table);
                    }
                }
            }
        });

        this.setSize(800, 415);
        SetWindowCenter.setCenter(this);
        this.setResizable(false);
        this.setVisible(true);
        this.add(ButtonBuy);
        this.add(ButtonRemove);
        this.add(costLabel);
    }
    public void initTableModel(JTable t){
        Object[][] rowDate = null;
        Object[] columnNames = {"编号","名称","作者","价格","购买数量"};
        DefaultTableModel model = new DefaultTableModel(rowDate, columnNames);
        Map<Book,Integer> books = user.getShoppingCart();
        Set<Book> keys = user.getShoppingCart().keySet();
        Iterator<Book> looper = keys.iterator();
        while(looper.hasNext()){
            Book b=looper.next();
            Object[] book;
            book = new Object[]{b.getBid(),b.getName(),b.getAuthor(),b.getPrice(),books.get(b)};
            model.insertRow(0, book);
        }
        t.setModel(model);
    }
}
