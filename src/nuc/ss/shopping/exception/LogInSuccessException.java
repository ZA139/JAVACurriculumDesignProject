package nuc.ss.shopping.exception;

import nuc.ss.shopping.entity.User;

public class LogInSuccessException extends Exception {
    User user = null;

    public LogInSuccessException(User user) {
        this.user = user;
        System.out.println("登陆成功,用户为"+user);
    }
}
