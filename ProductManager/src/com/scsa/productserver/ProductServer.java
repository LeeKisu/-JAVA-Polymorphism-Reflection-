package com.scsa.productserver;

import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

import com.scsa.product.Product;
import com.scsa.productmanager.ProductManager;

public class ProductServer {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		ServerSocket s = new ServerSocket(5555);
		ProductManager pm;
		Socket client = s.accept();
		ObjectInputStream in = new ObjectInputStream(client.getInputStream());
		pm = (ProductManager)in.readObject();
		for(Product p : pm.getList()) {
			System.out.println(p);
		}
	}

}
