package nuc.ss.shopping.windows;
import	java.io.BufferedReader;

import nuc.ss.shopping.datapath.DataPath;
import nuc.ss.shopping.entity.User;
import nuc.ss.shopping.exception.LogInSuccessException;
import nuc.ss.shopping.service.UserDao;
import nuc.ss.shopping.service.UserDaoImpl;
import nuc.ss.shopping.tools.SetWindowCenter;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.Objects;

/**
 * @author a1399
 */
public class SignIn extends JFrame {
    private final String ADMIN = "管理员";
    private JFrame jFrame = new JFrame("欢迎登录书籍电商购物平台");

    private JButton signInBtn = new JButton();
    private JButton signUpBtn = new JButton();
    private JButton resetBtn = new JButton();

    private JLabel userNameLabel = new JLabel();
    private JLabel userTypeLabel = new JLabel();
    private JLabel userPasswordLabel = new JLabel();

    private JTextField userNameTxtField = new JTextField();
    private JPasswordField userPasswordTxtField = new JPasswordField();
    private String rememberedUserName="";
    private String rememberedUserPassword="";
    private JComboBox userType = new JComboBox();

    private JCheckBox rememberName = new JCheckBox();
    private JCheckBox rememberPassword = new JCheckBox();
    private JCheckBox displayPassword = new JCheckBox();

    private JPanel inputArea = new JPanel();
    private JPanel btnAndCheckBoxArea = new JPanel();

    private User user =null;
    public SignIn(){
        init();
    }

    private void init() {

        userNameLabel.setText("用户名");
        userTypeLabel.setText("用户类型");
        userPasswordLabel.setText("用户密码");

        try {
            FileReader fileReader = new FileReader(DataPath.DATABASE + "\\Remember\\Name.txt");
            BufferedReader reader = new BufferedReader(fileReader);
            rememberedUserName = reader.readLine();
            fileReader.close();
            reader.close();
            fileReader = new FileReader(DataPath.DATABASE + "\\Remember\\Password.txt");
            reader = new BufferedReader(fileReader);
            rememberedUserPassword = reader.readLine();
            fileReader.close();
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        userNameTxtField.setText(rememberedUserName);
        userPasswordTxtField.setEchoChar('*');
        userPasswordTxtField.setText(rememberedUserPassword);

        userType.addItem("普通用户");
        userType.addItem("管理员");

        rememberName.setText("记住用户");
        rememberPassword.setText("记住密码");
        displayPassword.setText("显示密码");
        rememberName.setSelected(false);
        displayPassword.setSelected(false);
        rememberPassword.setSelected(false);

        resetBtn.setText("重置");
        signInBtn.setText("登录");
        signUpBtn.setText("注册");

        inputArea.setLayout(new GridLayout(3, 2));
        inputArea.add(userNameLabel);
        inputArea.add(userNameTxtField);
        inputArea.add(userTypeLabel);
        inputArea.add(userType);
        inputArea.add(userPasswordLabel);
        inputArea.add(userPasswordTxtField);
        inputArea.setVisible(true);

        btnAndCheckBoxArea.setLayout(new GridLayout(2, 3));
        btnAndCheckBoxArea.add(rememberName);
        btnAndCheckBoxArea.add(rememberPassword);
        btnAndCheckBoxArea.add(displayPassword);
        btnAndCheckBoxArea.add(signInBtn);
        btnAndCheckBoxArea.add(resetBtn);
        btnAndCheckBoxArea.add(signUpBtn);
        btnAndCheckBoxArea.setVisible(true);

        addListener();

        jFrame.setLayout(new BorderLayout());
        jFrame.add(inputArea, BorderLayout.CENTER);
        jFrame.add(btnAndCheckBoxArea, BorderLayout.SOUTH);
        jFrame.setSize(250, 200);
        SetWindowCenter.setCenter(jFrame);
        jFrame.setResizable(false);
        jFrame.setVisible(true);
    }

    public User getUser() {
        return user;
    }

    private void addListener(){
        signInBtn.addActionListener(e-> {
            UserDao udi = new UserDaoImpl();
            String userId = userNameTxtField.getText();
            String userPassword = String.valueOf(userPasswordTxtField.getPassword());
            user = udi.login(userId, userPassword);
            if (user != null) {
                boolean admin=false;
                if(Objects.equals(userType.getSelectedItem(), ADMIN)){
                    admin=true;
                }
                if(user.isAdmin()!=admin){
                    JOptionPane.showMessageDialog(null, "您的账号类型有误，请检查", "登陆失败", JOptionPane.WARNING_MESSAGE);user=null;
                }else {
                    MainWindow mainWindow = new MainWindow(user);
                }
            } else {
                JOptionPane.showMessageDialog(null, "您输入的账号或者密码有误，请检查", "登陆失败", JOptionPane.WARNING_MESSAGE);
            }
        });
        resetBtn.addActionListener(e->{
            userNameTxtField.setText("");
            userPasswordTxtField.setText("");
            userType.setSelectedIndex(0);
        });

        signUpBtn.addActionListener(e->{
           SignUp signUp= new SignUp();
        });

        displayPassword.addActionListener(e->{
            if(displayPassword.isSelected()){
                userPasswordTxtField.setEchoChar('\0');
            }
            else{
                userPasswordTxtField.setEchoChar('*');
            }
        });

        rememberName.addActionListener(e->{
            if(rememberName.isSelected()){
                try {
                    FileWriter fileWriter = new FileWriter(DataPath.DATABASE+"\\Remember\\Name.txt");
                    fileWriter.write(userNameTxtField.getText());
                    fileWriter.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }else{
                try {
                    rememberPassword.setSelected(false);
                    FileWriter fileWriter = new FileWriter(DataPath.DATABASE+"\\Remember\\Name.txt");
                    fileWriter.write("");
                    fileWriter.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            //IO写入文件
        });

        rememberPassword.addActionListener(e->{
            if(rememberPassword.isSelected()){
                rememberName.setSelected(true);
                try {
                    FileWriter fileWriter = new FileWriter(DataPath.DATABASE+"\\Remember\\Password.txt");
                    fileWriter.write(userPasswordTxtField.getPassword());
                    fileWriter.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                try {
                    FileWriter fileWriter = new FileWriter(DataPath.DATABASE+"\\Remember\\Name.txt");
                    fileWriter.write(userNameTxtField.getText());
                    fileWriter.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }else{
                try {
                    FileWriter fileWriter = new FileWriter(DataPath.DATABASE+"\\Remember\\Password.txt");
                    fileWriter.write("");
                    fileWriter.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }
}
