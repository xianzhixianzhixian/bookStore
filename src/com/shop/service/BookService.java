/**
 * 作者：樊钰丰
 * 时间：2017.8.22
 * 功能：业务逻辑类，用于处理book表相关业务
 */

package com.shop.service;

import java.util.ArrayList;

import com.shop.domain.Book;
import com.shop.utils.SQLHelper;

public class BookService {

	//得到所有的书籍信息(分页)
	public ArrayList getAllBook(){
		
		//获得所有的书籍
		String sql="select * from books";
		ArrayList al=SQLHelper.executeQuery(sql, null);
		ArrayList<Book> bookAl=new ArrayList<Book>();
		//二次业务封装
		for(int i=0;i<al.size();i++){
			
			Object[] obj=(Object[])al.get(i);
			Book book=new Book();
			book.setId((int) obj[0]);
			book.setBookname((String) obj[1]);
			book.setAuthor((String) obj[2]);
			book.setPublishhouse((String) obj[3]);
			book.setPrice((double) obj[4]);
			book.setNums((int) obj[5]);
			
			//添加到ArrayList中
			bookAl.add(book);
		}
		
		return bookAl;
	}
	
	//根据id得到书籍
	public Book getBookById(String id){
		
		//获得某本书籍
		String sql="select * from books where id=?";
		Object[] parameters={id};
		
		ArrayList al=SQLHelper.executeQuery(sql, parameters);
		
		//书籍正常存在
		if(al.size()==1){
			
			Book book=new Book();
			//二次业务封装
			Object[] obj=(Object[])al.get(0);
			book.setId((int) obj[0]);
			book.setBookname((String) obj[1]);
			book.setAuthor((String) obj[2]);
			book.setPublishhouse((String) obj[3]);
			book.setPrice((double) obj[4]);
			book.setNums((int) obj[5]);

			return book;
				
		}else{ //书籍不存在
			
			return null;
		}
		
	}
}
