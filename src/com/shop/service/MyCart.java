/**
 * 作者：樊钰丰
 * 时间：2017.8.22
 * 功能：我的购物车
 */

package com.shop.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import com.shop.domain.Book;

public class MyCart {

	HashMap<String,Book> hm=new HashMap<String,Book>();
	
	//添加书
	public void addBook(String id,Book book){
		
		if(!hm.containsKey(id)){
			
			hm.put(id, book);
		}else{
			
			//第二次购买，书籍数量加一，更新HashMap中的数据
			book.setBuyNums((book.getBuyNums()+1));
			hm.put(id, book);
		}
		
	}
	
	//添加书的第二个方法
	public void addBook2(String id){
		
		if(hm.containsKey(id)){
			
			//该书本存在
			Book book=hm.get(id);
			//第二次购买，书籍数量加一，更新HashMap中的数据
			book.setBuyNums((book.getBuyNums()+1));
		}else{
			
			hm.put(id, new BookService().getBookById(id));
		}
	}
	
	//删除书
	public void deleteBook(String id){
		
		hm.remove(id);
	}
	
	//得到所有书籍
	public ArrayList<Book> getAllBook(){
		
		ArrayList<Book> al=new ArrayList<Book>();
		
		//得到HashMap中所有的书籍
		Iterator it=hm.keySet().iterator();
		while(it.hasNext()){
			
			String key=it.next().toString();
			Book book=hm.get(key);
			al.add(book);
		}
		
		return al;
	}
	
	//得到购物车中的书籍总价格
	public double getTotalPrice(){
		
		double totalPrice=0; //购物车书籍的总价格
		
		Iterator it=hm.keySet().iterator();
		while(it.hasNext()){
			
			String key=it.next().toString();
			//取出书号对应的Book对象
			Book book=hm.get(key);
			totalPrice+=book.getPrice()*book.getBuyNums();
		}
		
		return totalPrice;
	}
	
	//更新书(对于购物车的更新)
	public void updateBook(String[] id,String[] nums){
		
		for(int i=0;i<id.length;i++){
			
			Book book=hm.get(id[i]);
			book.setBuyNums(Integer.parseInt(nums[i]));
		}
	}
	
	//清空书，清空购物车
	public void clearBook(){
		
		hm.clear();
	}
}
