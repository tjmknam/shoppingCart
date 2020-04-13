package net.class101.model;

import java.util.concurrent.Callable;

public class Item {
    private int itemNumber;
    private String type;
    private String itemDescription;
    private int price;
    private int inventory;
    
    public Item() {
    }


    public Item(int itemNumber, String type, String itemDescription, int price, int inventory) {
        this.itemNumber = itemNumber;
        this.type = type;
        this.itemDescription = itemDescription;
        this.price = price;
        this.inventory = inventory;
    }


	public int getItemNumber() {
		return itemNumber;
	}


	public void setItemNumber(int itemNumber) {
		this.itemNumber = itemNumber;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public String getItemDescription() {
		return itemDescription;
	}


	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}


	public int getPrice() {
		return price;
	}


	public void setPrice(int price) {
		this.price = price;
	}


	public int getInventory() {
		return inventory;
	}


	public void setInventory(int inventory) {
		this.inventory = inventory;
	}
    
    
}