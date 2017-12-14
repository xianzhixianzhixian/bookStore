<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.shop.domain.*" %>
<%@ page import="java.util.*" %>
<!-- 作者：樊钰丰 时间：2017.8.23 功能：显示我的订单 -->
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<script type="text/javascript" >
			
			function goSubmitOrder(){
				
				window.location.href="/Shop/SubmitOrder";
			}
		</script>
		<title>我的订单</title>
	</head>
	<body>
	<%
		User user=(User) request.getSession().getAttribute("user");
		ArrayList<Book> al=(ArrayList<Book>) request.getAttribute("orderInfo");
		double totalPrice=(double) request.getAttribute("totalPrice");
	%>
		<h1>我的订单</h1>
		<table border="1" style="border-collapse: collapse;font-size: 14px;">
			<tr><th colspan="2">用户信息</th></tr>
			<tr><td>用户名</td><td><%=user.getUsername() %></td></tr>
			<tr><td>电子邮件</td><td><%=user.getEmail() %></td></tr>
			<tr><td>用户级别</td><td><%=user.getGrade() %></td></tr>
		</table><br/><br/><br/>
		<table border="1" style="border-collapse: collapse;font-size: 14px;">
			<tr><th colspan="5">订单信息</th></tr>
			<tr><th>BookID</th><th>书名</th><th>出版社</th><th>价格</th><th>数量</th>
			<%
				for(int i=0;i<al.size();i++){
					
					Book book=al.get(i);
					%>
					<tr><td><%=book.getId() %></td><td><%=book.getBookname() %></td><td><%=book.getPublishhouse() %></td><td><%=book.getPrice() %></td><td><%=book.getBuyNums() %></td></tr>
					<%
				}
			%>
			<tr><td colspan="5">总价格：<%=totalPrice %></td></tr>
		</table><hr/>
		<input type="button" onclick="goSubmitOrder()" value="确认购买">
	</body>
</html>