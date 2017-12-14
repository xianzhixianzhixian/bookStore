package com.shop.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.shop.domain.User;
import com.shop.service.BookService;
import com.shop.service.MyCart;
import com.shop.service.UserService;

/**
 * Servlet implementation class GoHallUI
 */
@WebServlet("/GoHallUI")
public class GoHallUI extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GoHallUI() {
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
		
		//先判断该用户是否已登陆，如果已登陆则直接跳转到购物大厅
		if(request.getSession().getAttribute("user")!=null){
			
			//给下一个页面hall.jsp准备要显示的数据
			BookService bookService=new BookService();
			ArrayList al=bookService.getAllBook();
			
			//把显示的数据放入request，因为request对象的生命周期最短
			request.setAttribute("allBook", al);
			
			//用户合法跳转到购物大厅
			request.getRequestDispatcher("/WEB-INF/hall.jsp").forward(request, response);
			
			//return不能少
			return;
		}
		
		//得到从登陆页面传来的用户名和密码
		String id=request.getParameter("id");
		String password=request.getParameter("password");
		
		//创建User对象
		User user=new User();
		user.setId(Integer.parseInt(id));
		user.setPassword(password);
		
		//使用业务逻辑类完成验证
		UserService userService=new UserService();
		if(userService.checkUser(user)){
			
			//给下一个页面hall.jsp准备要显示的数据
			BookService bookService=new BookService();
			ArrayList al=bookService.getAllBook();
			
			//创建购物车
			MyCart myCart=new MyCart();
			request.getSession().setAttribute("myCart", myCart);
			request.getSession().setAttribute("user", user);
			
			//把显示的数据放入request，因为request对象的生命周期最短
			request.setAttribute("allBook", al);
			
			//用户合法跳转到购物大厅
			request.getRequestDispatcher("/WEB-INF/hall.jsp").forward(request, response);
		}else{
			
			//不合法
			request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);;
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
