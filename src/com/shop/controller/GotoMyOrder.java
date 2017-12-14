/**
 * 作者：樊钰丰
 * 时间：2017.8.23
 * 功能：用于跳转到我的订单页面
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
import com.shop.service.MyCart;

/**
 * Servlet implementation class GotoMyOrder
 */
@WebServlet("/GotoMyOrder")
public class GotoMyOrder extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GotoMyOrder() {
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
		
		//得到购物车
		MyCart myCart=(MyCart) request.getSession().getAttribute("myCart");
		ArrayList<Book> al=myCart.getAllBook();
		double totalPrice=myCart.getTotalPrice();
		request.setAttribute("orderInfo", al);
		request.setAttribute("totalPrice", totalPrice);
		request.getRequestDispatcher("/WEB-INF/myOrder.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
