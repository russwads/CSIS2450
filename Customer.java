package callcenter;

import java.sql.ResultSet;

public class Customer {
	private int customerID;
	private String name;
	private String address;
	private long phone_number;
	private String credit_card;
	private double credit_balance;
	private CallHistory history;
	
	public Customer(int c, String n, String a, long p, long cc, double cb) {
		this.customerID = c;
		this.name = n;
		this.address = a;
		this.phone_number = p;
		this.credit_card = Long.toHexString(cc);
		this.credit_balance = cb;
	}
/*	These functions call upon the interface for the script for each company (Chase, Amex, etc.)
 * public void displayScript(String x) {
		// TODO Finish function
	}
	public boolean callDB(long x) {
		// TODO Finish function
		return false;
	} */
	/**
	 * Checks the customer ID to see if the customers match.
	 * @param id
	 * @return
	 */
	public boolean isMatch(int id) {
		return this.customerID == id;
	}
	/**
	 * Checks either the phone number or the credit card number. 
	 * Makes decision with "notPhone," where FALSE would compare input with phone number and TRUE would compare credit card numbers. 
	 * @param ccn either credit card number or phone number
	 * @param notPhone FALSE would compare input with phone number and TRUE would compare credit card numbers
	 * @return
	 */
	public boolean isMatch(long ccn, boolean notPhone) {
		if (notPhone) {
			return Long.toHexString(ccn) == credit_card;
		}
		else {
			return ccn == phone_number;
		}
	}
	/**
	 * Checks name, address, and phone number to see if the customers match. 
	 * This would be the last resort before the call ends between customer and agent to find account information.
	 * @param n name
	 * @param add address
	 * @param phone phone number
	 * @return
	 */
	public boolean isMatch(String n, String add, long phone) {
		boolean p = this.name == n;
		boolean q = this.address == add;
		boolean r = this.phone_number == phone;
		return p && q && r;
	}
	/**
	 * This updates the address fields in the DB for the given customer.
	 * @param address Must be inputed in the format "123 STREETNAME, CITY, ST, 00000"
	 * @return
	 */
	public boolean update(String address) {
		String[] buff = address.split(", ");
		String street = null, city = null, state = null;
		int zip = 0;
		try {
			street = "billing_address_1 = \"" + buff[0] + "\"";
			city = "billing_city = " + buff[1] + "\"";
			state = "billing_state = " + buff[2] + "\"";
			zip = Integer.parseInt(buff[3]);
		} catch (Exception e) {
			System.out.println("Comma Deliminating Error: Please split street, city, state, and zipcode input by commas.");
			return false;
		}
		String up = "UPDATE customers SET " + street + ", " + city + ", " + state + ", billing_zip = " + zip + " WHERE chase_account_no = " + this.customerID + ";";
		// TODO Execute statement in DB
		// ResultSet rs = stmt.executeQuery(up);
		// return false if query fails 
		return true;
	}
	/**
	 * I may or may not have given up with this function (oops), but this updates the phone number in the DB.
	 * @param phone
	 * @return
	 */
	public boolean update(long phone) {
		String buff = Long.toString(phone);
		int[] digits = new int[buff.length()];
		for (int i = 0; i < digits.length; i++) {
			String y = null;
			char x = buff.charAt(i);
			y = y + x;
			digits[i] = Integer.parseInt(y);
		}
		String newPhone = "(" + digits[0] + digits[1] + digits[2] + ") " + digits[3] + digits[4] + digits[5] + "-" + digits[6] + digits[7] + digits[8] +  digits[9];
		String up = "UPDATE customers SET primary_phone =  \"" + newPhone + "\" WHERE chase_account_no = " + this.customerID + ";";
		// TODO Execute statement in DB
		// ResultSet rs = stmt.executeQuery(up);
		// return false if query fails 
		return true;
	}
	@Override
	public String toString() {
		return "Customer #" + customerID + ": " + name;
	}
}
