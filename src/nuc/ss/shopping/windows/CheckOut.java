package nuc.ss.shopping.windows;

import nuc.ss.shopping.datapath.DataPath;
import nuc.ss.shopping.entity.User;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class CheckOut extends JFrame {
    User user =null;
    JLabel paymentImage = null;
    JLabel payment =null;

    public CheckOut(User user) throws HeadlessException {
        super("请使用微信支付进行结账");
        this.user = user;
        paymentImage = new JLabel(new ImageIcon(DataPath.DATABASE+"\\Payment.png"));
        payment = new JLabel("您本次消费为:"+String.valueOf(Objects.requireNonNull(user).getShoppingCart().getPrice()));
        this.setSize(1000,800);
        init();
        this.setVisible(true);
    }

    private void init(){
        JSplitPane jSplitPane =new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,paymentImage,payment);
        this.add(jSplitPane);
        setContentPane(jSplitPane);
    }
}
