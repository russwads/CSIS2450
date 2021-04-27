package merchandise;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.LinkedList;

public class Order {
	private double tax;
	private double priceTotal;
	private LocalDate dat;
	private LocalTime tim;
	private Discount guest_ovr;
	private LinkedList<Product> products = new LinkedList<Product>();
	
	public Order() {
		this.tax = 0.0;
		this.priceTotal = 0.0;
		this.dat = LocalDate.now();
		this.tim = LocalTime.now();
		this.guest_ovr = Discount.ZERO;
	}
	
	public Order(Product p) {
		this.tax = 0.0;
		this.priceTotal = 0.0;
		this.dat = LocalDate.now();
		this.tim = LocalTime.now();
		this.guest_ovr = Discount.ZERO;
		this.products.add(p);
	}
	/**
	 * Returns the order price
	 * @return
	 */
	public double getPrice() {
		return priceTotal;
	}
	/**
	 * Calculates the total cost of the order by adding subtotal and tax.
	 */
	public void priceCalc() {
		// Calculate state taxes
		this.tax = Math.round((this.priceTotal * 0.06199) * 100.0) / 100.0; // Cedar City tax code for Sales Tax is 6.2% as of Apr 22, 2021
		// Calculate discounts implemented
		switch(guest_ovr) {
		case ONE:
			this.priceTotal = this.priceTotal - Math.round((this.priceTotal * 0.2)* 100.0) / 100.0;
		case TWO:
			this.priceTotal = this.priceTotal - Math.round((this.priceTotal * 0.4)* 100.0) / 100.0;
		case THREE:
			this.priceTotal = 0.0;
		default:
		}
		// Add taxes to total price
		this.priceTotal += this.tax;
	}
	/**
	 * Changes the order prices to implement discounts
	 * @param d discount code to override
	 * @return
	 */
	public boolean discountOverride(Discount d) {
		// No discount = no override
		if (this.guest_ovr == Discount.ZERO) {
			return false;
		}
		// Discount input overrides the ZERO discount
		else {
			this.guest_ovr = d;
			return true;
		}
	}
	/**
	 * Returns the date and time to use for the receipt.
	 * @return
	 */
	public String getDateAndTime() {
		return dat.toString() + " " + tim.toString();
	}
	/**
	 * Adds products to the order
	 * @param p product to add
	 * @return
	 */
	public boolean addProduct(Product p) {
		products.add(p);
		priceTotal = priceTotal + p.getProdPrice();
		return true;
	}
	@Override
	/** toString function in Orders is used for inserting values of order into `Orders` DB	 */
	public String toString() {
		return priceTotal + ", \"" + dat + "\", \""
				+ tim + "\", " + guest_ovr.ordinal() + ", 1000);";
	}
	/**
	 * Empties the current order.
	 */
	public void empty() {
		products.clear();
		this.tax = 0.0;
		this.priceTotal = 0.0;
		this.dat = LocalDate.now();
		this.tim = LocalTime.now();
		this.guest_ovr = Discount.ZERO;
	}
}