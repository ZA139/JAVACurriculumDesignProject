package nuc.ss.shopping.windows;

import nuc.ss.shopping.entity.Book;
import nuc.ss.shopping.entity.BookDataSet;
import nuc.ss.shopping.entity.User;
import nuc.ss.shopping.exception.CartyException;
import nuc.ss.shopping.service.BookDao;
import nuc.ss.shopping.service.BookDaoImpl;
import nuc.ss.shopping.service.CartyDao;
import nuc.ss.shopping.service.CartyDaoImpl;
import nuc.ss.shopping.tools.SetWindowCenter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.atomic.AtomicInteger;

public class RemoveBooks extends JFrame {
    BookDaoImpl bookDao = new BookDaoImpl();
    BookDataSet bds = new BookDataSet();
    Book[] books = bds.getBooks();

    JButton ButtonQuery = new JButton("��ѯ");
    JButton ButtonBuy = new JButton("ɾ��");
    User user = null;

    public RemoveBooks(User user) {
        this.user = user;
        init();
    }

    private void init() {
        this.setTitle("ɾ���鼮������Աģʽ��");
        this.setLayout(new FlowLayout());
        ButtonQuery.setBounds(210, 10, 80, 30);
        this.add(ButtonQuery);
        this.add(ButtonBuy);

        JTable table = new JTable();
        JScrollPane pane = new JScrollPane();
        pane.getViewport().add(table);

        ButtonQuery.addActionListener(e->{
            initTableModel(table);
        });
        ButtonBuy.addActionListener(e->{
            if(table.getSelectedRow()!=-1) {
                String tempId = table.getValueAt(table.getSelectedRow(),0).toString();
                for(int i =0; i <books.length ; i++) {
                    if(books[i]==null) {
                        break;
                    }
                    if(tempId.equals(books[i].getBid())){
                        bookDao.remove(books[i]);
                        initTableModel(table);
                    }
                }
            }
            else{
                JOptionPane.showMessageDialog(null, "��ѡ��һ���鼮", "ɾ��ʧ��", JOptionPane.WARNING_MESSAGE);
            }
        });
        pane.setBounds(10, 50, 330, 300);
        this.getContentPane().add(pane);
        this.setSize(600, 410);
        SetWindowCenter.setCenter(this);
        this.setResizable(false);
        this.setVisible(true);
    }
    public void initTableModel(JTable t){
        boolean flag = false;
        Object[][] rowDate = null;
        Object[] columnNames = {"���","����","����","�۸�","���"};
        DefaultTableModel model = new DefaultTableModel(rowDate, columnNames);
        Book[] books = getAllBooks();
        Object[] book;
        for(int i=0;i<books.length;i++){
            if(books[i]==null) {
                break;
            }
            book = new Object[]{books[i].getBid(),books[i].getName(),books[i].getAuthor(),books[i].getPrice(),books[i].getNumber()};
            model.insertRow(0, book);
            flag = true;
        }
        if(!flag){
            JOptionPane.showMessageDialog(null, "��Ǹ����ƽ̨��ʱû���鼮", "����ʧ��", JOptionPane.WARNING_MESSAGE);
        }
        t.setModel(model);
    }
    public Book[] getAllBooks(){
        BookDataSet bds = new BookDataSet();
        return bds.getBooks();
    }
}
