package merchandise;

public class Product {
	private long upc;
	private String name;
	private double price;
	private int stock;
	
	public Product() {
		this.upc = 123456789012L;
		this.name = "Hello World Stuff";
		this.price = 9.99;
		this.stock = 10;
	}
	
	public Product(long u, String n, int s, double p) {
		this.upc = u;
		this.name = n;
		this.price = p;
		this.stock = s;
	}
	/**
	 * Get the product price
	 * @return
	 */
	public double getProdPrice() {
		return this.price;
	}
	/**
	 * Get the product name
	 * @return
	 */
	public String getName() {
		return this.name;
	}
	/**
	 * Get the product UPC (identification factor)
	 * @return
	 */
	public long getUPC() {
		return this.upc;
	}
	/**
	 * Get the stock for the item
	 * @return
	 */
	public int getStock() {
		return this.stock;
	}
	
	@Override
	public String toString() {
		String s = String.format("%-20s     $%.2f\n%12d",name,price,upc);
		return s;
	}
}