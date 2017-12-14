/**
 * 作者：樊钰丰
 * 时间：2017.8.25
 * 功能：订单实体
 */

package com.shop.domain;

import java.util.ArrayList;

public class Order {

	private int id; //订单id
	private double totalPrice; //订单总价格
	private String orderDate; //订单完成时间
	private ArrayList<Book> bookList=new ArrayList<Book>(); //订单商品列表
	
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
