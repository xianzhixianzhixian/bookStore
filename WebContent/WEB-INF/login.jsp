<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- 作者：樊钰丰 时间：2017.8.15 功能：用户登陆界面 -->
<!DOCTYPE html>
<html>
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>登陆界面</title>
	</head>
	<body>
		<h1>登陆界面</h1>
		<form action="/Shop/GoHallUI" method="post">
			<table border="1" style="border-collapse: collapse;">
				<tr><td>用户名：</td><td><input type="text" name="id"></td></tr>
				<tr><td>密　码：</td><td><input type="password" name="password"></td></tr>
				<tr><td><input type="submit" value="登录"></td><td><input type="reset" value="重置"></td></tr>
			</table>
		</form>
	</body>
</html>