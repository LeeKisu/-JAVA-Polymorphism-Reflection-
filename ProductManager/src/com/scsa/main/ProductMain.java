package com.scsa.main;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.List;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import com.scsa.product.CPU;
import com.scsa.product.GPU;
import com.scsa.product.Monitor;
import com.scsa.product.Product;
import com.scsa.productmanager.IdExistException;
import com.scsa.productmanager.ProductManager;

public class ProductMain {
	private ProductManager pm;
	private Frame frame;
	private Panel top;
	private Panel main;
	private Panel main_left;
	private List main_left_list;
	private Panel main_right;
	private CardLayout cl;
	private ProductInfoPannel cur;
	private Panel bottom;
	private Panel bottom_buttons;
	private Button bottom_search;
	private Button bottom_add;
	private Button bottom_modify;
	private Button bottom_remove;
	private Button bottom_upload;
	private Label bottom_error;
	
	public ProductMain() {
		bottom_error = new Label();
		pm = new ProductManager();
		try {
			File file = new File("pm.dat");
			if(file.exists()) {
				ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
				try {
					pm = (ProductManager) in.readObject();
				} catch (ClassNotFoundException e1) {
					ShowException(e1);
				}
			}
		} catch (IOException e2) {
			ShowException(e2);
		}
		
		frame = new Frame("Product Management");
		frame.setSize(600, 800);
		frame.setLayout(new BorderLayout());
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				File file = new File("pm.dat");
				if(!file.exists()) {
					try {
						file.createNewFile();
					} catch (IOException e1) {
						ShowException(e1);
					}
				}
				try {
					ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
					out.writeObject(pm);
				} catch (IOException e1) {
					ShowException(e1);
				}
				System.exit(0);
			}
		});
		
		top = new Panel();
		top.setLayout(new FlowLayout());
		
		main = new Panel();
		main.setLayout(new GridLayout(0, 1));
		main_left = new Panel();
		main_left.setLayout(new BorderLayout());
		main_left_list = new List();
		main_left_list.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				String str = main_left_list.getSelectedItem().toString();
				Product p = pm.searchProduct(Integer.parseInt(str.split(",")[0]));
				if(p != null) {
					cur.setFields(p);
				}
			}
		});
		main_left.add(main_left_list);
		main_right = new Panel();
		cl = new CardLayout();
		main_right.setLayout(cl);
		main.add(main_left);
		main.add(main_right);
		
		bottom = new Panel();
		bottom.setLayout(new GridLayout(0, 1));
		bottom_buttons = new Panel();
		bottom_buttons.setLayout(new FlowLayout());
		bottom_search = new Button("Search");
		bottom_add = new Button("Add");
		bottom_remove = new Button("Remove");
		bottom_upload = new Button("Upload");
		bottom_modify = new Button("Modify");
		bottom_search.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Product p = pm.searchProduct(Integer.parseInt(cur.getField("id")), cur.getType());
				cur.setFields(p);
			}
		});
		bottom_add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					pm.addProduct(cur.addProduct());
					ListRefresh(cur.getType());
				} catch (Exception e1) {
					ShowException(e1);
				}
			}
		});
		bottom_remove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pm.removeProduct(Integer.parseInt(cur.getField("id")));
				ListRefresh(cur.getType());
			}
		});
		bottom_upload.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Socket s = new Socket("127.0.0.1", 5555);
					ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
					out.writeObject(pm);
				} catch (IOException e1) {
					ShowException(e1);
				}
			}
		});
		bottom_modify.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Product p = pm.searchProduct(Integer.parseInt(cur.getField("id")), cur.getType());
				if(p != null) {
					System.out.println("in");
					try {
						pm.modifyProduct(cur.addProduct(), cur.getType());
					} catch (Exception e1) {
						ShowException(e1);
					}
				}
			}
		});
		bottom_buttons.add(bottom_search);
		bottom_buttons.add(bottom_add);
		bottom_buttons.add(bottom_remove);
		bottom_buttons.add(bottom_upload);
		bottom_buttons.add(bottom_modify);
		bottom.add(bottom_buttons);
		bottom.add(bottom_error);
		
		frame.add(top, BorderLayout.NORTH);
		frame.add(bottom, BorderLayout.SOUTH);
		frame.add(main, BorderLayout.CENTER);
		
		cur = addCategory(Product.class);
		ListRefresh(Product.class);
		
		addCategory(CPU.class);
		addCategory(GPU.class);
		addCategory(Monitor.class);
		
		frame.setVisible(true);
	}
	
	private ProductInfoPannel addCategory(Class type) {
		ProductInfoPannel pip = new ProductInfoPannel(type);
		Button button = new Button(type.getSimpleName());
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ListRefresh(type);
				cl.show(main_right, type.getSimpleName());
				cur = pip;
			}
		});
		top.add(button);
		main_right.add(pip.getProductInfo(), type.getSimpleName());
		return pip;
	}
	
	private void ListRefresh(Class type) {
		main_left_list.removeAll();
		for(Product p : pm.getList(type)){
			main_left_list.add(Integer.toString(p.getId()) + ", " + p.getName());
		}
	}
	
	private void ShowException(Exception e) {
		bottom_error.setText(e.toString());
	}
}
