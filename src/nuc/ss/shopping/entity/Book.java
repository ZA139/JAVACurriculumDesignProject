package nuc.ss.shopping.entity;

public class Book {
	private String bid;
	private String name;
	private String author;
	private int number;
	private double price;
	private Category category;
	
	public Book() {
		super();
	}

	public Book(String bid, String name, String author, int number, Category category,double price) {
		super();
		this.bid = bid;
		this.name = name;
		this.author = author;
		this.number = number;
		this.category = category;
		this.price = price;
	}

	public Book(String[] bookInfo) {
		super();
		this.bid = bookInfo[0];
		this.name = bookInfo[1];
		this.author = bookInfo[2];
		this.number =Integer.parseInt(bookInfo[3]);
		this.category =new Category(bookInfo[4],bookInfo[5]);
		this.price = Double.parseDouble(bookInfo[6]);
	}

	public double getPrice() {
		return price;
	}

	public String getBid() {
		return bid;
	}

	public void setBid(String bid) {
		this.bid = bid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	@Override
	public String toString() {
		return bid + ":" + name + ":" +author+ ":" + number+":"+category+":"+price;
	}
}
