/**
 * ���ߣ����ڷ�
 * ʱ�䣺2017.8.22
 * ���ܣ���Ӧ�û�������Ʒ������
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
		
		//�����û��빺����Ʒ��id
		String id=request.getParameter("id");
		String action=request.getParameter("action");
		
		//ʲôʱ�򴴽����ﳵ����(���û���¼�ɹ��󼴴���)
		//ȡ�����ﳵ������鼮
		MyCart myCart=(MyCart) request.getSession().getAttribute("myCart");
		
		if(action.equals("buy")){
			//�����鼮
			Book book=bookService.getBookById(id);
			book.setBuyNums(1);
			myCart.addBook2(id);
		}else if(action.equals("delete")){
			//ɾ���鼮
			myCart.deleteBook(id);
		}else if(action.equals("update")){
			
			//���£��õ��û����µ���ź�����
			String[] bookIds=request.getParameterValues("id");
			//�õ���Ӧ����
			String[] nums=request.getParameterValues("num");
			
			for(int i=0;i<bookIds.length;i++){
				
				//System.out.println(bookIds[i]+" "+nums[i]);
				myCart.updateBook(bookIds, nums);
			}
		}else if(action.equals("goMyCart")){
			
		}
		
		//��Ҫ��ʾ�����ݷ���request��ȥ
//		request.setAttribute("bookList", myCart.getAllBook());
//		request.setAttribute("totalPrice", myCart.getTotalPrice());
		
		//��ת����ʾ���ﳵ
//		request.getRequestDispatcher("/WEB-INF/showMyCart.jsp").forward(request, response);
		
		//Ϊ�˷�ֹҳ��ˢ�£����ǿ���sendRedirect()
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
