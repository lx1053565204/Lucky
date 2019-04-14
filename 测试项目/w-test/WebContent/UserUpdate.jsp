<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.fk.pojo.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>UserUpdate</title>
</head>
<body>
<%
String n="";
String v="";
User user=(User)request.getAttribute("user");
if(request.getAttribute("info_s")!=null){
	String p=(String)request.getAttribute("info_s");
	if("1".equals(p))
		n="selected";
	if("2".equals(p))
		v="selected";
}
%>
<h1 align="center">UpdateUser</h1>
<form action="upd" method="post" enctype="multipart/form-data">
<input type="hidden" name="uid" value=<%=user.getUid() %>>
	<table align="center">
	<tr>
		<td align="right">Photo：</td>
		<td align="left">
		<img src=../<%=user.getPrint()%> width="30px" height="30px">
		<input type="file" name="photo">
		</td>
		
	</tr>
	<tr>
		<td align="right">username：</td>
		<td align="left"><input type="text" name="username" value=<%=user.getUname() %>></td>
	</tr>
		<tr>
		<td align="right">password：</td>
		<td align="left"><input type="text" name="password" value=<%=user.getPassword() %>></td>
	</tr>
	<tr>
		<td align="right">sex：</td>
		<td align="left">
		<select name="sex">
		<option value="男" <%=n %>>♂</option>
		<option value="女" <%=v %>>♀</option>
		</select>
		</td>
	</tr>
	<tr>
		<td colspan="2" align="center"><input type="submit" value="submit"></td>
	</tr>
	</table>
</form>
</body>
</html>