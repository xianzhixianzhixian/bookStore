/**
 * 作者：樊钰丰
 * 时间：2017.8.22
 * 功能：响应用户购买商品的请求
 */

package com.shop.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.shop.domain.Book;
import com.shop.service.BookService;
import com.shop.service.MyCart;

/**
 * Servlet implementation class ShopController
 */
@WebServlet("/ShopController")
public class ShopController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShopController() {
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
		BookService bookService=new BookService();
		
		//接收用户想购买商品的id
		String id=request.getParameter("id");
		String action=request.getParameter("action");
		
		//什么时候创建购物车对象(当用户登录成功后即创建)
		//取出购物车并添加书籍
		MyCart myCart=(MyCart) request.getSession().getAttribute("myCart");
		
		if(action.equals("buy")){
			//购买书籍
			Book book=bookService.getBookById(id);
			book.setBuyNums(1);
			myCart.addBook2(id);
		}else if(action.equals("delete")){
			//删除书籍
			myCart.deleteBook(id);
		}else if(action.equals("update")){
			
			//更新，得到用户更新的书号和数量
			String[] bookIds=request.getParameterValues("id");
			//得到对应数量
			String[] nums=request.getParameterValues("num");
			
			for(int i=0;i<bookIds.length;i++){
				
				//System.out.println(bookIds[i]+" "+nums[i]);
				myCart.updateBook(bookIds, nums);
			}
		}else if(action.equals("goMyCart")){
			
		}
		
		//把要显示的数据放入request中去
//		request.setAttribute("bookList", myCart.getAllBook());
//		request.setAttribute("totalPrice", myCart.getTotalPrice());
		
		//跳转到显示购物车
//		request.getRequestDispatcher("/WEB-INF/showMyCart.jsp").forward(request, response);
		
		//为了防止页面刷新，我们可以sendRedirect()
		response.sendRedirect("/Shop/GoShowMyCart");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
