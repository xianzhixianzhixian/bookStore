package com.shop.domain;

public class Book {

	private int id; //�鼮ID
	private String bookname; //�鼮����
	private String author; //�鼮����
	private String publishhouse; //������
	private double price; //�۸�
	private int nums; //���
	private int buyNums=1; //��������
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getBookname() {
		return bookname;
	}
	public void setBookname(String bookname) {
		this.bookname = bookname;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getPublishhouse() {
		return publishhouse;
	}
	public void setPublishhouse(String publishhouse) {
		this.publishhouse = publishhouse;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public int getNums() {
		return nums;
	}
	public void setNums(int nums) {
		this.nums = nums;
	}
	public int getBuyNums() {
		return buyNums;
	}
	public void setBuyNums(int buyNums) {
		this.buyNums = buyNums;
	}
}
