package com.scsa.product;

import java.io.Serializable;

public class Product implements Serializable{
	private	int id;
	private String name; 
	private int price;
	
	public Product(){
	}
	
	public Product(int id, String name, int price){
		this.id = id;
		this.name = name;
		this.price = price;
	}
	
	public Product(String args[]){
		this(Integer.parseInt(args[0]),
				args[1],
				Integer.parseInt(args[2]));
	}

	public int getId() {
		return id;
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
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return id + "," + name + "," + price;
	}
}
