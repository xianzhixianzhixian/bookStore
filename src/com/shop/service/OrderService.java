/**
 * ���ߣ����ڷ�
 * ʱ�䣺2017.8.23
 * ���ܣ���������Ϣ���������ݿ�
 */

package com.shop.service;

import com.shop.domain.Book;
import com.shop.domain.Order;
import com.shop.domain.User;
import com.shop.utils.SQLHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class OrderService {
	
	private Connection ct=null;
	private PreparedStatement ps=null;
	private ResultSet rs=null,rs1=null;
	
	//�¶����漰�������໥�����ı�
	//��������Ϣд�����ű�
	public void submitOrder(MyCart myCart,User user){
		
		String sql="insert into orders(userId,totalPrice) values(?,?)";
		//��Ӷ������ӣ����ֲ������ر𣬿��Բ�ʹ��SQLHelper��ר��������ֲ�����Ӧ�����ݿ����
		try{
			
			ct=SQLHelper.getConnection();
			//Ϊ�˱�֤���������ȶ��ģ����Խ���������뼶������Ϊ�ɴ��л�
			ct.setAutoCommit(false);
			ct.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			
			ps=ct.prepareStatement(sql);
			ps.setInt(1, user.getId());
			ps.setDouble(2, myCart.getTotalPrice());
			ps.executeUpdate();
			
			//��εõ��ղŲ��붩���Ķ�����
			sql="select last_insert_id() from orders";
			ps=ct.prepareStatement(sql);
			rs=ps.executeQuery();
			int id=0;
			if(rs.next()){
				
				id=rs.getInt(1);
			}
			
			//�Ѷ���ϸ�ڱ�����
			ArrayList<Book> al=myCart.getAllBook();
			for(int i=0;i<al.size();i++){
				
				Book book=al.get(i);
				sql="insert into ordersitem(orderdId,bookId,bookNum) values(?,?,?)";
				ps=ct.prepareStatement(sql);
				ps.setInt(1, id);
				ps.setInt(2, book.getId());
				ps.setInt(3, book.getBuyNums());
				ps.executeUpdate();
			}
			//�����ύ����
			ct.commit();
			
		}catch(Exception e){
			
			e.printStackTrace();
			
			//��������ʱ�ع�
			try {
				ct.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				throw new RuntimeException(e.getMessage());
			}
			
			throw new RuntimeException(e.getMessage());
			
		}finally{
			
			try {
				
				if(rs!=null) rs.close(); rs=null;
				if(ps!=null) ps.close(); ps=null;
				if(ct!=null) ct.close(); ct=null;
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new RuntimeException(e.getMessage());
			}
		}
	}

	//��ȡ������Ϣ
	public HashMap<String,Order> getOrdersByUserId(int userId){
		
		//������ţ�ʱ�䣬��Ʒ���ơ����������ۣ������ܼ۸�
		HashMap<String,Order> hm=new HashMap<String,Order>();
		
		String sql="select id,totalPrice,orderDate from orders where userId=?";
		//��Ӷ������ӣ����ֲ������ر𣬿��Բ�ʹ��SQLHelper��ר��������ֲ�����Ӧ�����ݿ����
		try{
			
			ct=SQLHelper.getConnection();
			
			ps=ct.prepareStatement(sql);
			ps.setInt(1, userId);
			
			rs=ps.executeQuery();
			
			//�˴���õ��Ƕ�����id
			while(rs.next()){
				
				int orderdId=rs.getInt(1); //�������
				double totalPrice=rs.getDouble(2); //�����ܼ�
				String orderDate=rs.getString(3); //�������ʱ��
				
				Order order=new Order();
				order.setId(orderdId);
				order.setTotalPrice(totalPrice);
				order.setOrderDate(orderDate);
				
				
				//�˴�ʱ��ø�������ص��鼮����Ϣ
				ArrayList<Book> al=order.getBookList();
				
				sql="select bookId,bookNum from ordersitem where orderdId=?";
				ps=ct.prepareStatement(sql);
				ps.setInt(1, orderdId);
				
				rs1=ps.executeQuery();
				BookService bookService=new BookService();
				
				while(rs1.next()){
					
					int bookId=rs1.getInt(1); //�鱾id
					int buyNums=rs1.getInt(2); //��������
					
					Book book=bookService.getBookById(bookId+"");
					book.setBuyNums(buyNums);
					
					//���鱾��ӵ�������ȥ
					al.add(book);
				}
				
				//��������Ϣ����HashMap��ȥ
				hm.put(orderdId+"", order);
			}
			
			
		}catch(Exception e){
			
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
			
		}finally{
			
			try {
				
				if(rs!=null) rs.close(); rs=null;
				if(ps!=null) ps.close(); ps=null;
				if(ct!=null) ct.close(); ct=null;
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new RuntimeException(e.getMessage());
			}
		}
	    
		return hm;
	}
}