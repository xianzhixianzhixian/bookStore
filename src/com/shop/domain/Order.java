/**
 * ���ߣ����ڷ�
 * ʱ�䣺2017.8.25
 * ���ܣ�����ʵ��
 */

package com.shop.domain;

import java.util.ArrayList;

public class Order {

	private int id; //����id
	private double totalPrice; //�����ܼ۸�
	private String orderDate; //�������ʱ��
	private ArrayList<Book> bookList=new ArrayList<Book>(); //������Ʒ�б�
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public double getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(double totalPrice2) {
		this.totalPrice = totalPrice2;
	}
	public String getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}
	public ArrayList<Book> getBookList() {
		return bookList;
	}
	public void setBookList(ArrayList<Book> bookList) {
		this.bookList = bookList;
	}
	
}
