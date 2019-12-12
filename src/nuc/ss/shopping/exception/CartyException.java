package nuc.ss.shopping.exception;

public class CartyException extends Exception {

	public CartyException() {
		super("您要购买的商品数量不足！");
	}

	public CartyException(String message) {
		super(message);
	}

}
