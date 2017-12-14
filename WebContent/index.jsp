<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
	<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>欢迎页面</title>
	</head>
	<body>
	<!-- 通常把jsp页面放在WEB-INF目录下来防止用户直接访问,使用WebContent中的index.jsp中用jsp:forword来进行跳转 -->
		<jsp:forward page="./WEB-INF/login.jsp"></jsp:forward>
	</body>
</html>