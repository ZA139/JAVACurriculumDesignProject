package nuc.ss.shopping;

import java.awt.*;
import java.util.Scanner;

import com.sun.tools.javac.Main;
import nuc.ss.shopping.entity.Book;
import nuc.ss.shopping.entity.User;
import nuc.ss.shopping.entity.UserDataSet;
import nuc.ss.shopping.exception.CartyException;
import nuc.ss.shopping.exception.LogInSuccessException;
import nuc.ss.shopping.service.BookDaoImpl;
import nuc.ss.shopping.service.UserDao;
import nuc.ss.shopping.service.UserDaoImpl;
import nuc.ss.shopping.tools.SetWindowCenter;
import nuc.ss.shopping.windows.MainWindow;
import nuc.ss.shopping.windows.SignIn;

import javax.swing.*;

public class MainClass {
	public static void main(String[] args){
		UserDataSet.init();
		SignIn signIn = new SignIn();
	}
}
