package com.scsa.productmanager;

import java.io.Serializable;
import java.util.ArrayList;

import com.scsa.product.Product;

public class ProductManager implements Serializable{
	ArrayList<Product> list;
	
	public ProductManager(){
		list = new ArrayList<Product>();
	}
	
	public void addProduct(Product p) throws IdExistException{
		if(searchProduct(p.getId(), Product.class) != null) {
			throw new IdExistException();
		}
		list.add(p);
	}
	
	public Product searchProduct(int id, Class type) {
		for(Product p : list) {
			if(type.isInstance(p) && p.getId() == id) {
				return p;
			}
		}
		return null;
	}
	
	public Product searchProduct(int id) {
		return searchProduct(id, Product.class);
	}
	
	public void modifyProduct(Product p, Class type) throws IdExistException{
		if(searchProduct(p.getId(), type) != null) {
			removeProduct(p.getId());
			addProduct(p);
		}
	}
	
	public void modifyProduct(Product p) throws IdExistException{
		modifyProduct(p, Product.class);
	}
	
	public void removeProduct(int id){
		for(Product p : list) {
			if(p.getId() == id) {
				list.remove(p);
				return;
			}
		}
	}
	
	public ArrayList<Product> getList(Class type){
		ArrayList<Product> list_t = new ArrayList<Product>();
		for(Product p : list) {
			if(type.isInstance(p)) {
				list_t.add(p);
			}
		}
		return list_t;
	}
	
	public ArrayList<Product> getList(){
		return getList(Product.class);
	}
}
