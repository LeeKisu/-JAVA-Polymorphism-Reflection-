package com.scsa.product;

public class CPU extends Product{
	private	int core;
	private double clock; 
	
	public CPU() {
	}
	
	public CPU(int id, String name, int price, int core, double clock){
		super(id, name, price);
		this.core = core;
		this.clock = clock;
	}
	
	public CPU(String args[]){
		this(Integer.parseInt(args[0]),
				args[1],
				Integer.parseInt(args[2]),
				Integer.parseInt(args[3]),
				Double.parseDouble(args[4]));
	}

	public int getCore() {
		return core;
	}
	public void setCore(int core) {
		this.core = core;
	}
	public double getClock() {
		return clock;
	}
	public void setClock(double clock) {
		this.clock = clock;
	}

	@Override
	public String toString() {
		return super.toString() + "," + core + "," + clock;
	}
	
}
