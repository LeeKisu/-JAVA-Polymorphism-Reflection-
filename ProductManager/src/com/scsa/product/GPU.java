package com.scsa.product;

public class GPU extends Product{
	private	int memory;
	private double clock; 
	
	public GPU() {
	}
	
	public GPU(int id, String name, int price, int memory, double clock){
		super(id, name, price);
		this.memory = memory;
		this.clock = clock;
	}
	
	public GPU(String args[]){
		this(Integer.parseInt(args[0]),
				args[1],
				Integer.parseInt(args[2]),
				Integer.parseInt(args[3]),
				Double.parseDouble(args[4]));
	}

	public int getMemory() {
		return memory;
	}
	public void setMemory(int memory) {
		this.memory = memory;
	}
	public double getClock() {
		return clock;
	}
	public void setClock(double clock) {
		this.clock = clock;
	}

	@Override
	public String toString() {
		return super.toString() + "," + memory + "," + clock;
	}
}
