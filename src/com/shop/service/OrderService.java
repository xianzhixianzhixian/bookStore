/**
 * 作者：樊钰丰
 * 时间：2017.8.23
 * 功能：处理订单信息并存入数据库
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
	
	//下订单涉及到两张相互关联的表
	//将订单信息写入两张表
	public void submitOrder(MyCart myCart,User user){
		
		String sql="insert into orders(userId,totalPrice) values(?,?)";
		//添加订单复杂！这种操作很特别，可以不使用SQLHelper，专门针对这种操作对应的数据库操作
		try{
			
			ct=SQLHelper.getConnection();
			//为了保证订单号是稳定的，所以将其事务隔离级别升级为可串行化
			ct.setAutoCommit(false);
			ct.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			
			ps=ct.prepareStatement(sql);
			ps.setInt(1, user.getId());
			ps.setDouble(2, myCart.getTotalPrice());
			ps.executeUpdate();
			
			//如何得到刚才插入订单的订单号
			sql="select last_insert_id() from orders";
			ps=ct.prepareStatement(sql);
			rs=ps.executeQuery();
			int id=0;
			if(rs.next()){
				
				id=rs.getInt(1);
			}
			
			//把订单细节表生成
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
			//整体提交事务
			ct.commit();
			
		}catch(Exception e){
			
			e.printStackTrace();
			
			//出现问题时回滚
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

	//读取订单信息
	public HashMap<String,Order> getOrdersByUserId(int userId){
		
		//订单编号，时间，商品名称、数量、单价，订单总价格
		HashMap<String,Order> hm=new HashMap<String,Order>();
		
		String sql="select id,totalPrice,orderDate from orders where userId=?";
		//添加订单复杂！这种操作很特别，可以不使用SQLHelper，专门针对这种操作对应的数据库操作
		try{
			
			ct=SQLHelper.getConnection();
			
			ps=ct.prepareStatement(sql);
			ps.setInt(1, userId);
			
			rs=ps.executeQuery();
			
			//此处获得的是订单的id
			while(rs.next()){
				
				int orderdId=rs.getInt(1); //订单编号
				double totalPrice=rs.getDouble(2); //订单总价
				String orderDate=rs.getString(3); //订单完成时间
				
				Order order=new Order();
				order.setId(orderdId);
				order.setTotalPrice(totalPrice);
				order.setOrderDate(orderDate);
				
				
				//此处时获得跟订单相关的书籍的信息
				ArrayList<Book> al=order.getBookList();
				
				sql="select bookId,bookNum from ordersitem where orderdId=?";
				ps=ct.prepareStatement(sql);
				ps.setInt(1, orderdId);
				
				rs1=ps.executeQuery();
				BookService bookService=new BookService();
				
				while(rs1.next()){
					
					int bookId=rs1.getInt(1); //书本id
					int buyNums=rs1.getInt(2); //购买数量
					
					Book book=bookService.getBookById(bookId+"");
					book.setBuyNums(buyNums);
					
					//将书本添加到订单中去
					al.add(book);
				}
				
				//将订单信息放入HashMap中去
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