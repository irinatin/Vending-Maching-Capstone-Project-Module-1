package com.techelevator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

public class VendingMachine {

	private BigDecimal customerBalance = new BigDecimal(0.00);

	private BigDecimal customerMoneyEntered = new BigDecimal(0.00);

	private BigDecimal machineBalance = new BigDecimal(0.00);

	private final double QUARTER = 25;

	private final double NICKEL = 5;

	private final double DIME = 10;
	
	private double helper = 100;

	private int numOfQuarters = 0;

	private int numOfNickels = 0;

	private int numOfDimes = 0;

	private BigDecimal change = new BigDecimal(0);

//	private String changeSplit = "";

	Inventory newInventory = new Inventory();

	File sourceFile = newInventory.getInputFile();

	Map<String, StockedItems> itemMap = newInventory.stockInventory(sourceFile);

	public void runMainMenu() {

		System.out.println("(1) Display Vending Machine Menu, (2) Purchase, (3) Exit");

	}

	public void displayMenuItems() {

		for (String key : itemMap.keySet()) {
			if (itemMap.get(key).getQuantity() > 0) {
				System.out.printf("%-12s %-20s %.2f %2d %n", key, itemMap.get(key).getName(),
						itemMap.get(key).getPrice(), itemMap.get(key).getQuantity());
			} else {
				System.out.printf("%-12s %-20s %2s %n", key, itemMap.get(key).getName(), "SOLD OUT");
			}
		}
	}

	public void purchaseMethod(String key) {
		if (!itemMap.containsKey(key)) {
			System.out.println("Sorry, that slot doesn't exist!");
		} else if (itemMap.get(key).getQuantity() == 0) {
			System.out.println("SOLD OUT");
		} else if (machineBalance.compareTo(itemMap.get(key).getPrice()) == -1) { // if balance is less than purchase
																					// price
			System.out.println("Please, add more money!");
		} else if (machineBalance.compareTo(itemMap.get(key).getPrice()) >= 0) {
			System.out.println("Enjoy your " + itemMap.get(key).getName() + "! " + itemMap.get(key).getSound());

			itemMap.get(key).itemPurchased();
		}

	}

	public void displayPurchaseMenu() {

		System.out.println("(1) Feed Money, (2) Select Product, (3) Finish Transaction, " + "$"
				+ machineBalance.setScale(2, RoundingMode.CEILING));

	}

	public void feedMoney() {
		System.out.println("Please enter the amount: ");
	}

	public BigDecimal getCustomerMoneyEntered() {
		return customerMoneyEntered;
	}

	// vending machine balance after the application is open. Has a $0.00 balance by
	// default
	public BigDecimal getBalance() {
		return machineBalance.setScale(2, RoundingMode.CEILING);
	}

	// UPDATED BALANCE AFTER CUSTOMER'S MONEY INPUT
	public void setBalance(BigDecimal customerMoneyEntered) {
		this.machineBalance = machineBalance.add(customerMoneyEntered);
	}

	// Updated balance after purchase
	public void balanceAfterPurchase(BigDecimal itemPrice) {
		this.machineBalance = machineBalance.subtract(itemPrice);
	}

	// dispense the change
	public void dispenseTheChange() {
		numOfQuarters = (int) ((int) (this.machineBalance.doubleValue() * helper) / QUARTER); // amount of quarters for change
		double newBalance = this.machineBalance.doubleValue() * helper % QUARTER; //remainder in pennies
		
		numOfDimes = (int) (newBalance / DIME); //amount of dimes in int for change
		double newBalance1 = newBalance % DIME; // remainder in pennies
		
		numOfNickels = (int) (newBalance1 / NICKEL); //amount of nickels for change

		System.out.println("Your change is: $" + this.machineBalance + "\n" + numOfQuarters + ": Quarters\n"
				+ numOfDimes + ": Dimes\n" + numOfNickels + ": Nickels");

		this.machineBalance = new BigDecimal(0);

	}

	public void logAudit(String message) {
		String fileName = "Log.txt";
		File newFile = new File(fileName);

		try (FileWriter fileWriter = new FileWriter(newFile, true)) {
			PrintWriter writer = new PrintWriter(fileWriter);

			writer.println(message);

		} catch (IOException e) {
			System.out.println("File not found");
		}

	}
	
	public void logAuditSameLine(String message) {
		String fileName = "Log.txt";
		File newFile = new File(fileName);

		try (FileWriter fileWriter = new FileWriter(newFile, true)) {
			PrintWriter writer = new PrintWriter(fileWriter);

			writer.print(message);

		} catch (IOException e) {
			System.out.println("File not found");
		}

	}

}
