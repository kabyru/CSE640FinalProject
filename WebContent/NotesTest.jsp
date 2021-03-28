<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Webpage to test NotesController</title>
</head>
<body>
	<%
	String error_message = "";
	Object error = request.getAttribute("error");
	if (error != null)
		error_message = error.toString();
	%>
	<form action="./NotesServlet" METHOD=GET>
		<table Style="border-collapse: separate; border-spacing: 4px;">
			<tr>
				<td style="color: red"><%=error_message%></td>
				<td>Note Nickname:</td>
				<td><input name="notenickname" type="text" size="20"></td>
			</tr>
			<tr>
				<td style="color: red"><%=error_message%></td>
				<td>Note:</td>
				<td><input name="note" type="text" size="20"></td>
			</tr>
			<tr>
				<td style="color: red"><%=error_message%></td>
				<td>Username:</td>
				<td><input name="user" type="text" size="20"></td>
			</tr>
			<tr>
				<td></td>
				<td><input type="submit" value="Add Note"></td>
				<td></td>
			</tr>
		</table>
	</form>
</body>
</html>