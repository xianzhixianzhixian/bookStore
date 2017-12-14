<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.shop.domain.*" %>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>我的历史订单</title>
	</head>
	<body>
	<%
		User user=(User) request.getSession().getAttribute("user");
		HashMap<String,Order> hm=(HashMap<String,Order>) request.getAttribute("myHistoricalOrder");
	%>
		<h1>我的历史订单</h1>
		<table border="1" style="border-collapse: collapse;font-size: 14px;">
			<tr><th colspan="2">用户信息</th></tr>
			<tr><td>用户名</td><td><%=user.getUsername() %></td></tr>
			<tr><td>电子邮件</td><td><%=user.getEmail() %></td></tr>
			<tr><td>用户级别</td><td><%=user.getGrade() %></td></tr>
		</table><hr/>
		<table border="1" style="border-collapse: collapse;font-size: 14px;">
			<tr><th colspan="6">历史订单</th></tr>
			<tr><th>订单编号</th><th>订单时间</th><th>商品名称</th><th>单价</th><th>购买数量</th><th>总价格(元)</th></tr>
		<%
			Iterator<String> it=hm.keySet().iterator();
			while(it.hasNext()){
				
				String orderId=it.next().toString(); //获得带订单id
				Order order=hm.get(orderId); //获得订单
				ArrayList<Book> al=order.getBookList(); //获得订单商品信息
				%>
				<tr><td rowspan="<%=al.size() %>"><%=order.getId() %></td><td rowspan="<%=al.size() %>"><%=order.getOrderDate() %></td>
				<%
				for(int i=0;i<al.size();i++){
					
					Book book=al.get(i);
					if(i==0){
					%>
						<td><%=book.getBookname() %></td><td><%=book.getPrice() %></td><td><%=book.getBuyNums() %></td><td><%=book.getPrice()*book.getBuyNums() %></td>
					<%
					}else{
					%>
						<tr><td><%=book.getBookname() %></td><td><%=book.getPrice() %></td><td><%=book.getBuyNums() %></td><td><%=book.getPrice()*book.getBuyNums() %></td>
					<%
					}
				}
				%>
				</tr>
				<tr><td colspan="6"><%=order.getId() %>号订单总价格：<%=order.getTotalPrice() %></td></tr>
				<%
			}
		%>
		</table>
	</body>
</html>