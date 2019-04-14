<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Login</title>
</head>
<body>
<h1 align="center">Login</h1>
<form action="jack/login" method="post">
	<table align="center">
	<tr>
		<td align="right">username：</td>
		<td align="left"><input type="text" name="name" placeholder="username"></td>
	</tr>
		<tr>
		<td align="right">password：</td>
		<td align="left"><input type="text" name="pass" placeholder="password"></td>
	</tr>
	<tr>
		<td colspan="2" align="center"><input type="submit" value="Login"></td>
	</tr>
	</table>
</form>
</body>
</html>