package nuc.ss.shopping.windows;

import nuc.ss.shopping.entity.Book;
import nuc.ss.shopping.entity.Category;
import nuc.ss.shopping.entity.User;
import nuc.ss.shopping.service.BookDaoImpl;
import nuc.ss.shopping.tools.SetWindowCenter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class AddBooks extends JFrame {
    User user = null;
    private static final long serialVersionUID = 1L;
    private JLabel labelID;
    private JLabel labelCategoryLv1;
    private JLabel labeleCateGoryLv2;
    private JLabel labelName;
    private JLabel labelAuthor;
    private JLabel labelPrice;
    private JLabel labelNum;
    private JTextField textFieldId;
    private JTextField textFieldName;
    private JTextField textFieldAuthor;
    private JTextField textFieldPrice;
    private JTextField textFieldNum;
    JComboBox<String> jComboBoxCategory1;
    JComboBox<String> jComboBoxCategory2;
    JButton buttonAdd;
    JButton buttonReset;

    public AddBooks(User user) {
        this.user = user;
        init();
    }
    private void init() {
        this.setLayout(new GridLayout(8,2,5,5));
        labelID = new JLabel("���", JLabel.CENTER);
        labelCategoryLv1 = new JLabel("һ��Ŀ¼", JLabel.CENTER);
        labeleCateGoryLv2 = new JLabel("����Ŀ¼", JLabel.CENTER);
        labelName = new JLabel("����", JLabel.CENTER);
        labelAuthor = new JLabel("����", JLabel.CENTER);
        labelPrice = new JLabel("����", JLabel.CENTER);
        labelNum = new JLabel("���", JLabel.CENTER);
        textFieldId = new JTextField();
        textFieldName = new JTextField();
        textFieldAuthor = new JTextField();
        textFieldPrice = new JTextField();
        textFieldNum = new JTextField();
        jComboBoxCategory1 = new JComboBox<String>();
        jComboBoxCategory1.addItem("������");
        jComboBoxCategory1.addItem("��ѧ��");
        jComboBoxCategory1.addItemListener(new ItemListener(){

            @Override
            public void itemStateChanged(ItemEvent e) {
                JComboBox<?> c = (JComboBox<?>)e.getSource();
                if(c.getSelectedItem().equals("������")){
                    jComboBoxCategory2.removeAllItems();
                    jComboBoxCategory2.addItem("�������");
                    jComboBoxCategory2.addItem("��е���");
                    jComboBoxCategory2.addItem("��·���");
                    jComboBoxCategory2.addItem("������");
                } else {
                    jComboBoxCategory2.removeAllItems();
                    jComboBoxCategory2.addItem("�ŵ���ѧ");
                    jComboBoxCategory2.addItem("�ٶ���ѧ");
                    jComboBoxCategory2.addItem("��ʷ����");
                    jComboBoxCategory2.addItem("��ʷ");
                }
            }

        });
        jComboBoxCategory2 = new JComboBox<String>();
        buttonAdd = new JButton("���");
        buttonAdd.addActionListener(e->{
            String id = textFieldId.getText();
            String name = textFieldName.getText();
            String category1 = (String) jComboBoxCategory1.getSelectedItem();
            String category2 = (String) jComboBoxCategory2.getSelectedItem();
            String author = textFieldAuthor.getText();
            int num = Integer.parseInt(textFieldNum.getText());
            float price = Float.parseFloat(textFieldPrice.getText());
            if(id == null || name == null || author == null|| num == 0 || price == 0.0){
                showMessage("�뽫�鼮��Ϣ��д����������");
            } else {
                Category category = new Category(category1,category2);
                BookDaoImpl bookDao = new BookDaoImpl();
                if(bookDao.addBook(new Book(id, name, author, num,category,price))){
                    showMessage("��ӳɹ�");
                }
                else{
                    showMessage("���ʧ��");
                }
            }
        });
        buttonReset = new JButton("����");
        this.add(labelID);
        this.add(textFieldId);
        this.add(labelName);
        this.add(textFieldName);
        this.add(labelCategoryLv1);
        this.add(jComboBoxCategory1);
        this.add(labeleCateGoryLv2);
        this.add(jComboBoxCategory2);
        this.add(labelAuthor);
        this.add(textFieldAuthor);
        this.add(labelPrice);
        this.add(textFieldPrice);
        this.add(labelNum);
        this.add(textFieldNum);
        this.add(buttonAdd);
        this.add(buttonReset);

        this.setTitle("����鼮������Աģʽ��");
        this.setSize(600, 400);
        SetWindowCenter.setCenter(this);
        this.setResizable(false);
        this.setVisible(true);
    }

    public void showMessage(String name){
        JOptionPane.showMessageDialog(this, name);
    }
}
