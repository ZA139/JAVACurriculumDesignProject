package nuc.ss.shopping.windows;

import nuc.ss.shopping.entity.User;
import nuc.ss.shopping.service.UserDao;
import nuc.ss.shopping.service.UserDaoImpl;
import nuc.ss.shopping.tools.SetWindowCenter;

import javax.swing.*;
import java.awt.*;

public class SignUp extends JFrame{
    JFrame signUpWindow = new JFrame("�û�ע��");

    JLabel idLabel = new JLabel("�û�ID");
    JLabel nameLabel = new JLabel("����");
    JLabel sexLabel = new JLabel("�Ա�");
    JLabel passwordLabel = new JLabel("����");
    JLabel passwordConfirmLabel = new JLabel("ȷ������");
    JLabel cityLabel = new JLabel("����");

    JTextField idTextfield = new JTextField();
    JTextField nameTextfield = new JTextField();

    JPasswordField passwordField = new JPasswordField();
    JPasswordField passwordConfirmField = new JPasswordField();

    JButton btnSignUp = new JButton("ע��");
    JButton btnBack = new JButton("����");

    JComboBox cityComboBox = new JComboBox();
    JComboBox sexComboBox = new JComboBox();

    public SignUp() {
        init();
    }

    private void init(){
        cityComboBox.addItem("����");
        cityComboBox.addItem("�㶫");
        cityComboBox.addItem("̫ԭ");
        cityComboBox.addItem("��ɳ");

        sexComboBox.addItem("��");
        sexComboBox.addItem("Ů");

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
            //������ʽ
            if(!String.valueOf(passwordField.getPassword()).equals(String.valueOf(passwordConfirmField.getPassword()))){
                JOptionPane.showMessageDialog(null,"��������������벻һ�£�����","ע��ʧ��",JOptionPane.WARNING_MESSAGE);
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
                    JOptionPane.showMessageDialog(null,"ע��ɹ�","ע��ɹ�",JOptionPane.INFORMATION_MESSAGE);
                }else if(code == 1){
                    JOptionPane.showMessageDialog(null,"��ID�Ѿ���ע�ᣬ������ע��","ע��ʧ��",JOptionPane.ERROR_MESSAGE);
                }else if(code == 2) {
                    JOptionPane.showMessageDialog(null,"�ܱ�Ǹ����ƽ̨��Ա�û���������","ע��ʧ��",JOptionPane.ERROR_MESSAGE);
                }
                else {
                    JOptionPane.showMessageDialog(null,"δ֪��������ϵ����Ա","ע��ʧ��",JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        btnBack.addActionListener(e->{
            signUpWindow.dispose();
        });
    }
}
