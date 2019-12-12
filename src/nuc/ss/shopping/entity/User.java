package nuc.ss.shopping.entity;

import nuc.ss.shopping.datapath.DataPath;

import java.io.IOException;

/**
 * @author a1399
 */
public class User implements Cloneable{
	private String id;
	private String name;
	private String password;
	private String sex;
	private String city;
	private ShoppingCart shoppingCart;
	private boolean  admin=false;

	public boolean isAdmin() {
		return admin;
	}

	public User(String[] UserInfo) {
		this.id = UserInfo[0];
		this.name = UserInfo[1];
		this.password = UserInfo[2];
		this.sex = UserInfo[3];
		this.city = UserInfo[4];
		this.admin = Boolean.valueOf(UserInfo[5]);
		this.shoppingCart = new ShoppingCart(DataPath.SHOPPINGCARTSDATABASE +"\\"+ this.name + ".txt",this);
	}

	public User(String id, String name, String password, String sex, String city, boolean admin) {
		this.id = id;
		this.name = name;
		this.password = password;
		this.sex = sex;
		this.city = city;
		this.shoppingCart = shoppingCart;
		this.admin = admin;
		this.shoppingCart = new ShoppingCart(DataPath.SHOPPINGCARTSDATABASE +"\\"+ this.name + ".txt",this);
	}

	public ShoppingCart getShoppingCart() {
		return shoppingCart;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Override
	public String toString() {
		return this.getId() + ":" + this.getName()+":"+this.password
				+ ":" + this.getSex() + ":" + this.getCity()+":"+this.isAdmin();
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
}
