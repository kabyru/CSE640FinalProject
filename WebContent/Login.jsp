<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login User</title>
</head>
<body>
	<h2>Enter your username and password for the database.</h2>
	<%
	String error_message = "";
	Object error = request.getAttribute("error");
	if (error != null)
		error_message = error.toString();
	%>
	<form action="./LoginServlet" METHOD=GET>
		<table Style="border-collapse: separate; border-spacing: 4px;">
			<tr>
				<td>Username:</td>
				<td><input name="user" type="text" size="20"></td>
				<td style="color: red"><%=error_message%></td>
			</tr>
			<tr>
				<td>Password:</td>
				<td><input name="pw" type="password" size="20"></td>
			</tr>
			<tr>
				<td></td>
				<td><input type="submit" value="Login"></td>
				<td></td>
			</tr>
		</table>
	</form>
	<form action="./NewUser.jsp">
		<input type="submit" value="Register New User">
	</form>
</body>
</html>