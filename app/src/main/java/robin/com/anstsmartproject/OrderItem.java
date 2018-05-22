package robin.com.anstsmartproject;

public class OrderItem {
	private int id;
	private String name;
	private float prices; 
	private int storage;
	private int type;
	private int recommend;
	private int picture;
	private int totalCount = 0;
	private float totalPrices;
	private boolean isOrder = false;
	
	
	public int getId() {
		return id;
	}
	public int getPicture() {
		return picture;
	}
	public void setPicture(int picture) {
		this.picture = picture;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public float getPrices() {
		return prices;
	}
	public void setPrices(float prices) {
		this.prices = prices;
	}
	public int getStorage() {
		return storage;
	}
	public void setStorage(int storage) {
		this.storage = storage;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getRecommend() {
		return recommend;
	}
	public void setRecommend(int recommend) {
		this.recommend = recommend;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	public float getTotalPrices() {
		return totalPrices;
	}
	public void setTotalPrices(float totalPrices) {
		this.totalPrices = totalPrices;
	}
	public boolean isOrder() {
		return isOrder;
	}
	public void setOrder(boolean isOrder) {
		this.isOrder = isOrder;
	}
	
	

}
