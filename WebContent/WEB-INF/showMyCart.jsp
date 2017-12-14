<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.shop.service.MyCart" %>
<%@ page import="com.shop.domain.Book"%>
<!-- 作者：樊钰丰 时间：2017.8.16 功能：购物车 -->
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>我的购物车</title>
		<link rel="stylesheet" type="text/css" href="./css/common.css">
	</head>
	<body>
	<h1>我的购物车</h1>
	<form action="/Shop/ShopController?action=update" method="post">
		<table border="1" style="border-collapse: collapse;font-size: 14px;">
			<tr><th>BookID</th><th>书名</th><th>价格</th><th>出版社</th><th>数量</th><th>是否删除</th></tr>
			<%
				ArrayList<Book> al=(ArrayList<Book>) request.getAttribute("bookList");
				for(int i=0;i<al.size();i++){
					
					Book book=al.get(i);
					%>
					<tr><td><%=book.getId() %><input type="hidden" name="id" value=<%=book.getId() %>></td><td><%=book.getBookname() %></td><td><%=book.getPrice() %></td>
					<td><%=book.getPublishhouse() %></td><td><input type="text" name="num" value=<%=book.getBuyNums() %>></td><td>
					<a href="/Shop/ShopController?id=<%=book.getId() %>&action=delete">删除</a></td></tr>
					<%
				}
			%>
			<tr>
				<td colspan="2"><input type="submit" value="更新"></td>
				<td colspan="2"><a href="/Shop/GoHallUI">返回购物大厅</a></td>
				<td colspan="2"><a href="/Shop/GotoMyOrder">提交订单</a></td>
			</tr>
			<tr><td colspan="6">所有商品的总价格：<%=request.getAttribute("totalPrice") %>元</td></tr>
		</table>
	</form>
	</body>
</html>