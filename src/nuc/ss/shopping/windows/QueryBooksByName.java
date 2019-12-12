package nuc.ss.shopping.windows;
import	java.awt.event.WindowEvent;
import	java.awt.event.WindowAdapter;

import nuc.ss.shopping.datapath.DataPath;
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
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author a1399
 */
public class QueryBooksByName extends JFrame {
    CartyDao cartyDao = new CartyDaoImpl();
    BookDataSet bds = new BookDataSet();
    Book[] books = bds.getBooks();

    JLabel labelId = new JLabel("�鼮���");
    JTextField textFieldId = new JTextField(20);
    JButton ButtonQuery = new JButton("��ѯ");
    JButton ButtonBuy = new JButton("����");
    JButton ButtonCart = new JButton("�鿴���ﳵ");
    User user = null;

    public QueryBooksByName(User user) {
        this.user = user;
        init();
    }

    private void init() {
        this.setLayout(new FlowLayout());
        labelId.setBounds(10, 10, 80, 30);
        this.add(labelId);
        textFieldId.setBounds(100, 10, 100, 30);
        this.add(textFieldId);
        ButtonQuery.setBounds(210, 10, 80, 30);
        this.add(ButtonQuery);
        this.add(ButtonBuy);
        this.add(ButtonCart);

        JTable table = new JTable();
        JScrollPane pane = new JScrollPane();
        pane.getViewport().add(table);

        ButtonQuery.addActionListener(e->{
            initTableModel(table);
        });

        ButtonCart.addActionListener(e->{
            ShowShoppingCart showShoppingCart = new ShowShoppingCart(user);
            showShoppingCart.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e){
                    initTableModel(table);
                }
            }
            );
        });

        ButtonBuy.addActionListener(e->{
            if(table.getSelectedRow()!=-1) {
                String tempId = table.getValueAt(table.getSelectedRow(),0).toString();
                for(int i =0; i <books.length ; i++) {
                    if(books[i]==null) {
                        break;
                    }
                    AtomicInteger num = new AtomicInteger();
                    if(tempId.equals(books[i].getBid())){
                        String inputContent = JOptionPane.showInputDialog(
                                "�������Ҫ���������:",
                                "10"
                        );
                            num.set(Integer.parseInt(inputContent));
                        try {
                            cartyDao.buy(user,books[i],num.get());
                        } catch (CartyException ex) {
                            ex.printStackTrace();
                        }
                            initTableModel(table);
                    }
                }
            }
            else{
                JOptionPane.showMessageDialog(null, "��ѡ��һ���鼮", "���ӹ��ﳵʧ��", JOptionPane.WARNING_MESSAGE);
            }
        });

        this.setTitle("���鼮id����");
        pane.setBounds(10, 50, 330, 300);
        this.getContentPane().add(pane);
        this.setSize(600, 400);
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
            if(books[i].getBid().equals(textFieldId.getText())){
                book = new Object[]{books[i].getBid(),books[i].getName(),books[i].getAuthor(),books[i].getPrice(),books[i].getNumber()};
                model.insertRow(0, book);
                flag = true;
                break;
            }
        }
        if(!flag){
            JOptionPane.showMessageDialog(null, "δ�ҵ���������ı����ƥ����鼮������", "����ʧ��", JOptionPane.WARNING_MESSAGE);
        }
        t.setModel(model);
    }
    public Book[] getAllBooks(){
        BookDataSet bds = new BookDataSet();
        return bds.getBooks();
    }
}