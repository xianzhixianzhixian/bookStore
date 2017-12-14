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
		
		//���жϸ��û��Ƿ��ѵ�½������ѵ�½��ֱ����ת���������
		if(request.getSession().getAttribute("user")!=null){
			
			//����һ��ҳ��hall.jsp׼��Ҫ��ʾ������
			BookService bookService=new BookService();
			ArrayList al=bookService.getAllBook();
			
			//����ʾ�����ݷ���request����Ϊrequest����������������
			request.setAttribute("allBook", al);
			
			//�û��Ϸ���ת���������
			request.getRequestDispatcher("/WEB-INF/hall.jsp").forward(request, response);
			
			//return������
			return;
		}
		
		//�õ��ӵ�½ҳ�洫�����û���������
		String id=request.getParameter("id");
		String password=request.getParameter("password");
		
		//����User����
		User user=new User();
		user.setId(Integer.parseInt(id));
		user.setPassword(password);
		
		//ʹ��ҵ���߼��������֤
		UserService userService=new UserService();
		if(userService.checkUser(user)){
			
			//����һ��ҳ��hall.jsp׼��Ҫ��ʾ������
			BookService bookService=new BookService();
			ArrayList al=bookService.getAllBook();
			
			//�������ﳵ
			MyCart myCart=new MyCart();
			request.getSession().setAttribute("myCart", myCart);
			request.getSession().setAttribute("user", user);
			
			//����ʾ�����ݷ���request����Ϊrequest����������������
			request.setAttribute("allBook", al);
			
			//�û��Ϸ���ת���������
			request.getRequestDispatcher("/WEB-INF/hall.jsp").forward(request, response);
		}else{
			
			//���Ϸ�
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
