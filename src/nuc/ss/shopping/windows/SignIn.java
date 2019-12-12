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
    private final String ADMIN = "����Ա";
    private JFrame jFrame = new JFrame("��ӭ��¼�鼮���̹���ƽ̨");

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

        userNameLabel.setText("�û���");
        userTypeLabel.setText("�û�����");
        userPasswordLabel.setText("�û�����");

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

        userType.addItem("��ͨ�û�");
        userType.addItem("����Ա");

        rememberName.setText("��ס�û�");
        rememberPassword.setText("��ס����");
        displayPassword.setText("��ʾ����");
        rememberName.setSelected(false);
        displayPassword.setSelected(false);
        rememberPassword.setSelected(false);

        resetBtn.setText("����");
        signInBtn.setText("��¼");
        signUpBtn.setText("ע��");

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
                    JOptionPane.showMessageDialog(null, "�����˺�������������", "��½ʧ��", JOptionPane.WARNING_MESSAGE);user=null;
                }else {
                    MainWindow mainWindow = new MainWindow(user);
                }
            } else {
                JOptionPane.showMessageDialog(null, "��������˺Ż���������������", "��½ʧ��", JOptionPane.WARNING_MESSAGE);
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
            //IOд���ļ�
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
