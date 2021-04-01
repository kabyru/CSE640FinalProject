<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Add a new Bookmark</title>
</head>
<body>
	<%
	String userName = "";
	String error = "";
	
	Object userN = session.getAttribute("user");
	Object err = request.getAttribute("error");
	if (userN != null)
	{
		userName = (String) userN;	
	}
	if (err != null)
	{
		error = (String) err;
	}
	%>
	<font color="red"><%=error%></font>
	<h2>Add a new bookmark for <%=userName%></h2>
	<form action="./AddBookmarkServlet" METHOD=GET>
		<table Style="border-collapse: separate; border-spacing: 4px;">
		<tr>
			<td>Enter the name of your note:</td>
			<td><input name="bookmarkname" type="text" size="20"></td>
		</tr>
		<tr>
			<td>Enter the URL of the bookmark:</td>
			<td><input name="bookmarkURL" type="text" size="20"></td>
		</tr>
		</table>
		<input type="submit" value="Add Bookmark to List">
	</form>
</body>
</html>