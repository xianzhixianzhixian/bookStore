/**
 * 作者：樊钰丰
 * 时间：2017.8.23
 * 功能：处理下订单的请求
 */

package com.shop.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.shop.domain.Book;
import com.shop.domain.User;
import com.shop.service.MyCart;
import com.shop.service.OrderService;
import com.shop.service.SendMail;

/**
 * Servlet implementation class SubmitOrder
 */
@WebServlet("/SubmitOrder")
public class SubmitOrder extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SubmitOrder() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8"); 
		
		PrintWriter out=response.getWriter();
		
		//获得用户信息
		User user=(User) request.getSession().getAttribute("user");
		//获得购物车
		MyCart myCart=(MyCart) request.getSession().getAttribute("myCart");

		try{
			
			OrderService orderService=new OrderService();
			orderService.submitOrder(myCart,user);
			
		}catch(Exception e){
			
			e.printStackTrace();
			request.getRequestDispatcher("/WEB-INF/error.jsp");
		}
		
		//创建一个SendMail实例
		SendMail sendMail=new SendMail();
		
		String mailbody="<html><head><meta charset=utf-8><title>购物清单</title>"
				+"</head><body>"
				+ "<table border='1' style='border-collapse: collapse;font-size: 14px;'>"
				+ "<tr><th colspan='5'>您的购物清单</th></tr>"
				+ "<tr><th>书名</th><th>作者</th><th>出版社</th><th>价格</th><th>购买数量</th></tr>";
		ArrayList<Book> al=myCart.getAllBook();
		for(int i=0;i<al.size();i++){
			
			Book book=al.get(i);
			mailbody=mailbody
				+"<tr><td>"+book.getBookname()
				+"</td><td>"+book.getAuthor()
				+"</td><td>"+book.getPublishhouse()
				+"</td><td>"+book.getPrice()
				+"</td><td>"+book.getBuyNums()
				+"</td></tr>";
		}
		mailbody+="<tr><td colspan='5'>总价格："+myCart.getTotalPrice()+"元</td></tr></table></body></html>";
		
		sendMail.send(user.getUsername()+"购物清单", mailbody, user.getEmail(), "mr___robot@163.com", "fyf1995318", "smtp.163.com");
		
		request.getRequestDispatcher("/WEB-INF/orderResult.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
