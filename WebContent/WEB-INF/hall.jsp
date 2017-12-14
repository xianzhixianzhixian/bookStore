<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.shop.domain.Book" %>
<!-- 作者：樊钰丰 时间：2017.8.15 功能：购物大厅 -->
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>购物大厅</title>
	</head>
	<body>
		<h1>欢迎光临购物大厅</h1>
		<table border="1" style="border-collapse: collapse;">
		<tr><th>书名</th><th>价格</th><th>出版社</th><th>点击购买</th></tr>
		<%
			//取出request中的ArrayList
			ArrayList<Book> al=(ArrayList<Book>)request.getAttribute("allBook");
			for(int i=0;i<al.size();i++){
				
				Book book=al.get(i);
		%>		
				<tr><td><%=book.getBookname() %></td><td><%=book.getPrice() %></td><td><%=book.getPublishhouse() %></td>
				<td><a href="/Shop/ShopController?id=<%=book.getId() %>&action=buy">购买</a></td></tr>
		<%		
			}
		%>
			<tr>
				<td><input type="button" onclick="goMyCart()" value="查看购物车"></td>
				<td><input type="button" onclick="goHistoricalOrder()" value="查看历史订单"></td>
				<td colspan="2"><a href="/Shop/index.jsp">返回重新登录</a></td>
			</tr>
		</table>
	</body>
	<script type="text/javascript" >
		
		function goMyCart(){
			
			window.location.href="/Shop/ShopController?action=goMyCart";
		}
		
		function goHistoricalOrder(){
			
			window.location.href="/Shop/GoHistoricalOrder";
		}
	</script>
</html>