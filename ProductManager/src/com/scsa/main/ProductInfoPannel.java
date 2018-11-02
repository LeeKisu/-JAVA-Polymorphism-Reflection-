package com.scsa.main;

import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextArea;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import com.scsa.product.Product;

public class ProductInfoPannel {
	private Class type;
	private Panel ProductInfo;
	private TextArea[] tas;
	
	public ProductInfoPannel(Class type) {
		this.type = type;
		ProductInfo = new Panel();
		ProductInfo.setLayout(new GridLayout(0, 1));
		Label lable;
		ArrayList<String> fileds = getFileds(type);
		tas = new TextArea[fileds.size()];
		for(int i = 0; i < fileds.size(); i++) {
			lable = new Label(fileds.get(i));
			tas[i] = new TextArea();
			tas[i].setName(fileds.get(i));
			ProductInfo.add(lable);
			ProductInfo.add(tas[i]);
		}
	}
	
	public Class getType(){
		return type;
	}
	
	public Panel getProductInfo(){
		return ProductInfo;
	}
	
	private ArrayList<String> getFileds(Class type){
		ArrayList<String> fileds;
		if(type == Object.class) {
			fileds = new ArrayList<String>();
			return fileds;
		}else {
			fileds = getFileds(type.getSuperclass());
			for(Field f : type.getDeclaredFields()) {
				fileds.add(f.getName());
			}
		}
		return fileds;
	}
	
	public String getField(String field) {
		for(TextArea ta : tas) {
			if(ta.getName().equals(field)) {
				return ta.getText();
			}
		}
		return null;
	}
	
	public void setFields(Product p) {
		String[] values = p.toString().split(",");
		for(int i = 0; i < tas.length; i++) {
			tas[i].setText(values[i]);
		}
	}
	
	public Product addProduct() throws Exception {
		Product p;
		Constructor cons = type.getConstructor(new Class[] {String[].class});
		String[] values = new String[tas.length];
		for(int i = 0; i < tas.length; i++) {
			values[i] = tas[i].getText();
			tas[i].setText("");
		}
		p = (Product) cons.newInstance(new Object[] {values});
		return p;
	}
}
