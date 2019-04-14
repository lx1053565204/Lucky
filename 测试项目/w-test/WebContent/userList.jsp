<%@page import="org.apache.jasper.tagplugins.jstl.core.ForEach"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*,com.fk.pojo.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>UserList</title>
</head>
<body>
<%
 String n="";
String v="";
String o="";
String info_u=""; 
List<User> list=(List<User>)request.getAttribute("userlist");
 User u=(User)session.getAttribute("User"); 
 if(request.getAttribute("info_u")!=null){
	info_u=(String)request.getAttribute("info_u");
}
if(request.getAttribute("info_s")!=null){
	String p=(String)request.getAttribute("info_s");
	if("1".equals(p))
		n="selected";
	if("2".equals(p))
		v="selected";
}else{
	o="selected";
} 
%>
<h2 align="center">UserList</h2>
<h6 align="center">user:<%=u.getUname() %></h6>
<div align="center">
<form action="search" method="post">
<label>username:</label><input name="username" type="text" placeholder="username-keyword" value=<%=info_u %>>
<label>sex:</label>
<select name="sex">
<option value="" <%=o %> >all</option>
<option value="男" <%=n %>>♂</option>
<option value="女" <%=v %>>♀</option>
</select>
<input type="submit" value=" search">
</form>
</div>
<br>
<table border="1" align="center" width="500px">
<tr align="center">
<td>Photo</td>
<td>Username</td>
<td>Sex</td>
<td>Password</td>
<td>Operation</td>
</tr>
<%
for(User user:list){
%>
<tr align="center">
<td><img src=../<%=user.getPrint()%> width="30px" height="30px"></td>
<td><%=user.getUname() %></td>
<td><%=user.getSsex() %></td>
<td><%=user.getPassword() %></td>
<td>
	<a href="get?id=<%=user.getUid() %>">Edit</a>&nbsp;&nbsp;&nbsp;&nbsp;
	<a href="del?id=<%=user.getUid() %>">Del</a>&nbsp;&nbsp;&nbsp;&nbsp;
	<a href=download?file=<%=user.getPrint() %>>Download</a>
</td>
</tr>
<%
}
%>
<tr align="center">
<td align="center" colspan="5"><input type="button" value="add user" onclick="location.href='../AddUser.jsp'"></td>
</tr>
</table>

</body>
</html>