/**
 * 作者：樊钰丰
 * 时间：2017.8.25
 * 功能：查看历史订单
 */

package com.shop.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.shop.domain.Book;
import com.shop.domain.Order;
import com.shop.domain.User;
import com.shop.service.BookService;
import com.shop.service.OrderService;

/**
 * Servlet implementation class GoHistoricalOrder
 */
@WebServlet("/GoHistoricalOrder")
public class GoHistoricalOrder extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GoHistoricalOrder() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		
		PrintWriter out=response.getWriter();
		
		//获得用户
		User user=(User) request.getSession().getAttribute("user");
		//获得历史订单
		OrderService orderService=new OrderService();
		HashMap<String,Order> hm=orderService.getOrdersByUserId(user.getId());
		//将历史订单翻入request中
		request.setAttribute("myHistoricalOrder", hm);
		
		request.getRequestDispatcher("/WEB-INF/myHistoricalOrder.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
