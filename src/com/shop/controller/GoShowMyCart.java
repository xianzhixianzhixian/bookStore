/**
 * ���ߣ����ڷ�
 * ʱ�䣺2017.8.23
 * ���ܣ���ֹҳ��ˢ��
 */

package com.shop.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.shop.service.MyCart;

/**
 * Servlet implementation class GoShowMyCart
 */
@WebServlet("/GoShowMyCart")
public class GoShowMyCart extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GoShowMyCart() {
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
		
		//ʲôʱ�򴴽����ﳵ����(���û���¼�ɹ��󼴴���)
		//ȡ�����ﳵ������鼮
		MyCart myCart=(MyCart) request.getSession().getAttribute("myCart");
		
		//��Ҫ��ʾ�����ݷ���request��ȥ
		request.setAttribute("bookList", myCart.getAllBook());
		request.setAttribute("totalPrice", myCart.getTotalPrice());
		
		request.getRequestDispatcher("/WEB-INF/showMyCart.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
