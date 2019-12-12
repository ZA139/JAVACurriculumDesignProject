package nuc.ss.shopping.tools;

import nuc.ss.shopping.windows.SignIn;

import javax.swing.*;
import java.awt.*;

public class SetWindowCenter {
    public static void setCenter(JFrame window) {
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        int width = window.getWidth();
        int height = window.getHeight();
        window.setBounds((d.width - width) / 2,
                (d.height - height) / 2,
                width,
                height);
    }
}
