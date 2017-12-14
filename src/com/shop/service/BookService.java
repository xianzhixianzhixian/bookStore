/**
 * ���ߣ����ڷ�
 * ʱ�䣺2017.8.22
 * ���ܣ�ҵ���߼��࣬���ڴ���book�����ҵ��
 */

package com.shop.service;

import java.util.ArrayList;

import com.shop.domain.Book;
import com.shop.utils.SQLHelper;

public class BookService {

	//�õ����е��鼮��Ϣ(��ҳ)
	public ArrayList getAllBook(){
		
		//������е��鼮
		String sql="select * from books";
		ArrayList al=SQLHelper.executeQuery(sql, null);
		ArrayList<Book> bookAl=new ArrayList<Book>();
		//����ҵ���װ
		for(int i=0;i<al.size();i++){
			
			Object[] obj=(Object[])al.get(i);
			Book book=new Book();
			book.setId((int) obj[0]);
			book.setBookname((String) obj[1]);
			book.setAuthor((String) obj[2]);
			book.setPublishhouse((String) obj[3]);
			book.setPrice((double) obj[4]);
			book.setNums((int) obj[5]);
			
			//��ӵ�ArrayList��
			bookAl.add(book);
		}
		
		return bookAl;
	}
	
	//����id�õ��鼮
	public Book getBookById(String id){
		
		//���ĳ���鼮
		String sql="select * from books where id=?";
		Object[] parameters={id};
		
		ArrayList al=SQLHelper.executeQuery(sql, parameters);
		
		//�鼮��������
		if(al.size()==1){
			
			Book book=new Book();
			//����ҵ���װ
			Object[] obj=(Object[])al.get(0);
			book.setId((int) obj[0]);
			book.setBookname((String) obj[1]);
			book.setAuthor((String) obj[2]);
			book.setPublishhouse((String) obj[3]);
			book.setPrice((double) obj[4]);
			book.setNums((int) obj[5]);

			return book;
				
		}else{ //�鼮������
			
			return null;
		}
		
	}
}
