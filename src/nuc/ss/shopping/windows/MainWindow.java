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
        title.append("电商购物平台系统[欢迎");
        if(user.isAdmin()){
            title.append("管理员");
        }
        else {
            title.append("普通用户");
        }
        title.append(":"+user.getName()+"]登陆系统");
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
        JMenu menu = new JMenu("商品管理");
        JMenu l1Menu = new JMenu("查询商品信息");

        JMenuItem item = new JMenuItem("查询所有商品");
        item.addActionListener(e->{
            ShowBooks showBooks = new ShowBooks("查询所有商品",user);
        });
        l1Menu.add(item);
        item = new JMenuItem("按商品类别查询");
        l1Menu.add(item);
        item.addActionListener(e->{
            QueryBooksByCategory queryBooksByCategory = new QueryBooksByCategory(user);
        });
        item = new JMenuItem("按商品id查询");
        l1Menu.add(item);
        item.addActionListener(e->{
            QueryBooksByName queryBooksByName = new QueryBooksByName(user);
        });
        menu.add(l1Menu);
        item = new JMenuItem("添加商品");
        if(!user.isAdmin()){
            item.setEnabled(false);
        }
        menu.add(item);
        item.addActionListener(e->{
            AddBooks addBooks = new AddBooks(user);
        });
        item = new JMenuItem("删除商品");
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
        JMenu menu = new JMenu("订单管理");
        return menu;
    }

    private JMenu createPersonalInformationManager(){
        JMenu menu = new JMenu("个人信息管理");
        return menu;
    }

    private JMenu createCharacterManager(){
        JMenu menu = new JMenu("角色管理");
        return menu;
    }
}
