package nuc.ss.shopping.windows;

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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.atomic.AtomicInteger;

public class QueryBooksByCategory extends JFrame {
    CartyDao cartyDao = new CartyDaoImpl();
    BookDataSet bds = new BookDataSet();
    Book[] books = bds.getBooks();

    JLabel labelId = new JLabel("书籍类型");
    JComboBox<String> jComboBoxCategory1 = new JComboBox<String>();
    JComboBox<String> jComboBoxCategory2 = new JComboBox<String>();
    JButton ButtonQuery = new JButton("查询");
    JButton ButtonBuy = new JButton("购买");
    JButton ButtonCart = new JButton("查看购物车");
    User user = null;

    public QueryBooksByCategory(User user) {
        this.user = user;
        init();
    }

    private void init() {
        this.setLayout(new FlowLayout());
        labelId.setBounds(10, 10, 80, 30);
        this.add(labelId);
       jComboBoxCategory1.setBounds(100, 10, 100, 30);
        jComboBoxCategory1.addItem("工具类");
        jComboBoxCategory1.addItem("文学类");
        initJcomboBox();
        jComboBoxCategory1.addActionListener(e->{
            initJcomboBox();
        });
        this.add(jComboBoxCategory1);
        this.add(jComboBoxCategory2);
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
                    initTableModel(table); }
            });
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
                                "输入你的要购买的数量:",
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
                JOptionPane.showMessageDialog(null, "请选中一本书籍", "添加购物车失败", JOptionPane.WARNING_MESSAGE);
            }
        });

        this.setTitle("按类别查询");
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
        Object[] columnNames = {"编号","名称","作者","价格","库存"};
        DefaultTableModel model = new DefaultTableModel(rowDate, columnNames);
        Book[] books = getAllBooks();
        Object[] book;
        //target为false代表一级目录模糊查找
        boolean target=false;
        String targetString = "";
        if(jComboBoxCategory2.getSelectedItem()==null){
            targetString = String.valueOf(jComboBoxCategory1.getSelectedItem());
        }
        else{
            targetString = String.valueOf(jComboBoxCategory2.getSelectedItem());
            target=true;
        }
        for(int i=0;i<books.length;i++){
            if(books[i]==null){
                break;
            }
            if(target) {
                if (books[i].getCategory().getSecondLevel().equals(targetString)) {
                    book = new Object[]{books[i].getBid(), books[i].getName(), books[i].getAuthor(), books[i].getPrice(), books[i].getNumber()};
                    model.insertRow(0, book);
                    flag = true;
                }
            }
            else{
                if (books[i].getCategory().getFirstLevel().equals(targetString)) {
                    book = new Object[]{books[i].getBid(), books[i].getName(), books[i].getAuthor(), books[i].getPrice(), books[i].getNumber()};
                    model.insertRow(0, book);
                    flag = true;
                }
            }
        }
        if(!flag){
            JOptionPane.showMessageDialog(null, "未找到与您输入的类型相匹配的书籍，请检查", "查找失败", JOptionPane.WARNING_MESSAGE);
        }
        t.setModel(model);
    }
    public Book[] getAllBooks(){
        BookDataSet bds = new BookDataSet();
        return bds.getBooks();
    }

    private void initJcomboBox(){
        if("工具类".equals(jComboBoxCategory1.getSelectedItem())){
            jComboBoxCategory2.removeAllItems();
            jComboBoxCategory2.addItem(null);
            jComboBoxCategory2.addItem("软件开发");
            jComboBoxCategory2.addItem("机械设计");
            jComboBoxCategory2.addItem("电路设计");
            jComboBoxCategory2.addItem("软件编程");
        } else {
            jComboBoxCategory2.removeAllItems();
            jComboBoxCategory2.addItem(null);
            jComboBoxCategory2.addItem("古典文学");
            jComboBoxCategory2.addItem("少儿文学");
            jComboBoxCategory2.addItem("历史故事");
            jComboBoxCategory2.addItem("历史");
        }
    }
}
