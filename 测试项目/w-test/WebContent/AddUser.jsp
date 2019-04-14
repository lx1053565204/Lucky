<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<h1 align="center">AddUser</h1>
<form action="jack/add" method="post" enctype="multipart/form-data">
	<table align="center">
	<tr>
		<td align="right">Photo：</td>
		<td align="left"><input type="file" name="photo"></td>
	</tr>
	<tr>
		<td align="right">username：</td>
		<td align="left"><input type="text" name="username" placeholder="username"></td>
	</tr>
		<tr>
		<td align="right">password：</td>
		<td align="left"><input type="text" name="password" placeholder="password"></td>
	</tr>
	<tr>
		<td align="right">sex：</td>
		<td align="left">
		<select name="sex">
		<option value="男">♂</option>
		<option value="女">♀</option>
		</select>
		</td>
	</tr>
	<tr>
		<td colspan="2" align="center"><input type="submit" value="Add"></td>
	</tr>
	</table>
</form>

</body>
</html>