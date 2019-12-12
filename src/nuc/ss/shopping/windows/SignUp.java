package nuc.ss.shopping.windows;

import nuc.ss.shopping.entity.User;
import nuc.ss.shopping.service.UserDao;
import nuc.ss.shopping.service.UserDaoImpl;
import nuc.ss.shopping.tools.SetWindowCenter;

import javax.swing.*;
import java.awt.*;

public class SignUp extends JFrame{
    JFrame signUpWindow = new JFrame("用户注册");

    JLabel idLabel = new JLabel("用户ID");
    JLabel nameLabel = new JLabel("名字");
    JLabel sexLabel = new JLabel("性别");
    JLabel passwordLabel = new JLabel("密码");
    JLabel passwordConfirmLabel = new JLabel("确认密码");
    JLabel cityLabel = new JLabel("城市");

    JTextField idTextfield = new JTextField();
    JTextField nameTextfield = new JTextField();

    JPasswordField passwordField = new JPasswordField();
    JPasswordField passwordConfirmField = new JPasswordField();

    JButton btnSignUp = new JButton("注册");
    JButton btnBack = new JButton("返回");

    JComboBox cityComboBox = new JComboBox();
    JComboBox sexComboBox = new JComboBox();

    public SignUp() {
        init();
    }

    private void init(){
        cityComboBox.addItem("北京");
        cityComboBox.addItem("广东");
        cityComboBox.addItem("太原");
        cityComboBox.addItem("长沙");

        sexComboBox.addItem("男");
        sexComboBox.addItem("女");

        signUpWindow.setLayout(new GridLayout(7,2));
        signUpWindow.add(idLabel);
        signUpWindow.add(idTextfield);
        signUpWindow.add(nameLabel);
        signUpWindow.add(nameTextfield);
        signUpWindow.add(passwordLabel);
        signUpWindow.add(passwordField);
        signUpWindow.add(passwordConfirmLabel);
        signUpWindow.add(passwordConfirmField);
        signUpWindow.add(sexLabel);
        signUpWindow.add(sexComboBox);
        signUpWindow.add(sexComboBox);
        signUpWindow.add(cityLabel);
        signUpWindow.add(cityComboBox);
        signUpWindow.add(btnSignUp);
        signUpWindow.add(btnBack);

        addListener();
        signUpWindow.setSize(250, 400);
        SetWindowCenter.setCenter(signUpWindow);
        signUpWindow.setResizable(false);
        signUpWindow.setVisible(true);
    }


    private void addListener() {
        btnSignUp.addActionListener(e->{
            //正则表达式
            if(!String.valueOf(passwordField.getPassword()).equals(String.valueOf(passwordConfirmField.getPassword()))){
                JOptionPane.showMessageDialog(null,"您两次输入的密码不一致，请检查","注册失败",JOptionPane.WARNING_MESSAGE);
                passwordConfirmField.setText("");
                passwordField.setText("");
            }
            else{
                String userId = idTextfield.getText();
                String userPassword =String.valueOf(passwordField.getPassword());
                String userName =nameTextfield.getText();
                String userSex = (String)sexComboBox.getSelectedItem();
                String userCity =(String)cityComboBox.getSelectedItem();
                User user = new User(userId, userName, userPassword, userSex, userCity,false);

                UserDao udi = new UserDaoImpl();
                int code = udi.register(user);
                if(code == 0) {
                    JOptionPane.showMessageDialog(null,"注册成功","注册成功",JOptionPane.INFORMATION_MESSAGE);
                }else if(code == 1){
                    JOptionPane.showMessageDialog(null,"该ID已经被注册，请重新注册","注册失败",JOptionPane.ERROR_MESSAGE);
                }else if(code == 2) {
                    JOptionPane.showMessageDialog(null,"很抱歉，本平台会员用户数量已满","注册失败",JOptionPane.ERROR_MESSAGE);
                }
                else {
                    JOptionPane.showMessageDialog(null,"未知错误，请联系管理员","注册失败",JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        btnBack.addActionListener(e->{
            signUpWindow.dispose();
        });
    }
}
