package com.techelevator;

import java.math.BigDecimal;

//creating an abstract class for subclasses to inherit from

public abstract class StockedItems implements Yumable {

	private String name;
	private BigDecimal price = new BigDecimal(0);
	private int quantity = 5;

	public StockedItems(String name, BigDecimal price) {
		this.name = name;
		this.price = price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void itemPurchased() {
		quantity--;
	}

	public String getName() {
		return name;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public boolean isInStock() {
		if (this.quantity >= 1) {
			return true;
		}
		return false;
	}

}
