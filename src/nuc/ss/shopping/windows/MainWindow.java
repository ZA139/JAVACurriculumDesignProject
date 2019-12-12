package nuc.ss.shopping.windows;

import nuc.ss.shopping.entity.User;
import nuc.ss.shopping.tools.SetWindowCenter;

import javax.swing.*;
import java.awt.*;

/**
 * @author a1399
 */
public class MainWindow extends JFrame {
    private User user=null;
    private JMenuBar menuBar = new JMenuBar();
    StringBuffer title =new StringBuffer();

    public MainWindow(User user){
        this.user=user;
        title.append("���̹���ƽ̨ϵͳ[��ӭ");
        if(user.isAdmin()){
            title.append("����Ա");
        }
        else {
            title.append("��ͨ�û�");
        }
        title.append(":"+user.getName()+"]��½ϵͳ");
        this.setTitle(title.toString());
        init(user.isAdmin());
    }

    private void init(boolean isAdmin){
        menuBar.add(createBooksManage());
        this.setJMenuBar(menuBar);
        this.setSize(600, 400);
        SetWindowCenter.setCenter(this);
        this.setResizable(false);
        this.setVisible(true);
        menuBar.add(createListsManager());
        if(isAdmin){
            menuBar.add(createCharacterManager());
        }
        else{
            menuBar.add(createPersonalInformationManager());
        }
    }


    private JMenu createBooksManage(){
        JMenu menu = new JMenu("��Ʒ����");
        JMenu l1Menu = new JMenu("��ѯ��Ʒ��Ϣ");

        JMenuItem item = new JMenuItem("��ѯ������Ʒ");
        item.addActionListener(e->{
            ShowBooks showBooks = new ShowBooks("��ѯ������Ʒ",user);
        });
        l1Menu.add(item);
        item = new JMenuItem("����Ʒ����ѯ");
        l1Menu.add(item);
        item.addActionListener(e->{
            QueryBooksByCategory queryBooksByCategory = new QueryBooksByCategory(user);
        });
        item = new JMenuItem("����Ʒid��ѯ");
        l1Menu.add(item);
        item.addActionListener(e->{
            QueryBooksByName queryBooksByName = new QueryBooksByName(user);
        });
        menu.add(l1Menu);
        item = new JMenuItem("�����Ʒ");
        if(!user.isAdmin()){
            item.setEnabled(false);
        }
        menu.add(item);
        item.addActionListener(e->{
            AddBooks addBooks = new AddBooks(user);
        });
        item = new JMenuItem("ɾ����Ʒ");
        if(!user.isAdmin()){
            item.setEnabled(false);
        }
        menu.add(item);
        item.addActionListener(e->{
            RemoveBooks removeBooks = new RemoveBooks(user);
        });
        return menu;
    }

    private JMenu createListsManager(){
        JMenu menu = new JMenu("��������");
        return menu;
    }

    private JMenu createPersonalInformationManager(){
        JMenu menu = new JMenu("������Ϣ����");
        return menu;
    }

    private JMenu createCharacterManager(){
        JMenu menu = new JMenu("��ɫ����");
        return menu;
    }
}
