package nuc.ss.shopping.entity;

public class Category {
	private String firstLevel;
	private String secondLevel;
	public Category() {
		super();
	}
	public Category(String firstLevel, String secondLevel) {
		super();
		this.firstLevel = firstLevel;
		this.secondLevel = secondLevel;
	}
	public String getFirstLevel() {
		return firstLevel;
	}
	public void setFirstLevel(String firstLevel) {
		this.firstLevel = firstLevel;
	}
	public String getSecondLevel() {
		return secondLevel;
	}
	public void setSecondLevel(String secondLevel) {
		this.secondLevel = secondLevel;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.firstLevel + ":" + this.secondLevel;
	}

}
