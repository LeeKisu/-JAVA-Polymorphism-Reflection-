package com.scsa.product;

public class Monitor extends Product{
	private	String ratio;
	
	public Monitor() {
	}
	
	public Monitor(int id, String name, int price, String ratio){
		super(id, name, price);
		this.ratio = ratio;
	}
	
	public Monitor(String args[]){
		this(Integer.parseInt(args[0]),
				args[1],
				Integer.parseInt(args[2]),
				args[3]);
	}

	public String getRatio() {
		return ratio;
	}
	public void setRatio(String ratio) {
		this.ratio = ratio;
	}

	@Override
	public String toString() {
		return super.toString() + "," + ratio;
	}
}
