/**
 * ���ߣ����ڷ�
 * ʱ�䣺2017.8.22
 * ���ܣ��ҵĹ��ﳵ
 */

package com.shop.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import com.shop.domain.Book;

public class MyCart {

	HashMap<String,Book> hm=new HashMap<String,Book>();
	
	//�����
	public void addBook(String id,Book book){
		
		if(!hm.containsKey(id)){
			
			hm.put(id, book);
		}else{
			
			//�ڶ��ι����鼮������һ������HashMap�е�����
			book.setBuyNums((book.getBuyNums()+1));
			hm.put(id, book);
		}
		
	}
	
	//�����ĵڶ�������
	public void addBook2(String id){
		
		if(hm.containsKey(id)){
			
			//���鱾����
			Book book=hm.get(id);
			//�ڶ��ι����鼮������һ������HashMap�е�����
			book.setBuyNums((book.getBuyNums()+1));
		}else{
			
			hm.put(id, new BookService().getBookById(id));
		}
	}
	
	//ɾ����
	public void deleteBook(String id){
		
		hm.remove(id);
	}
	
	//�õ������鼮
	public ArrayList<Book> getAllBook(){
		
		ArrayList<Book> al=new ArrayList<Book>();
		
		//�õ�HashMap�����е��鼮
		Iterator it=hm.keySet().iterator();
		while(it.hasNext()){
			
			String key=it.next().toString();
			Book book=hm.get(key);
			al.add(book);
		}
		
		return al;
	}
	
	//�õ����ﳵ�е��鼮�ܼ۸�
	public double getTotalPrice(){
		
		double totalPrice=0; //���ﳵ�鼮���ܼ۸�
		
		Iterator it=hm.keySet().iterator();
		while(it.hasNext()){
			
			String key=it.next().toString();
			//ȡ����Ŷ�Ӧ��Book����
			Book book=hm.get(key);
			totalPrice+=book.getPrice()*book.getBuyNums();
		}
		
		return totalPrice;
	}
	
	//������(���ڹ��ﳵ�ĸ���)
	public void updateBook(String[] id,String[] nums){
		
		for(int i=0;i<id.length;i++){
			
			Book book=hm.get(id[i]);
			book.setBuyNums(Integer.parseInt(nums[i]));
		}
	}
	
	//����飬��չ��ﳵ
	public void clearBook(){
		
		hm.clear();
	}
}
