<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Add a new Note</title>
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
	//If userName is null, then the user is NOT allowed to be on this page! We need to redirect them immediately.
	if (userN == null)
	{
		String url = "./Welcome.jsp";
		RequestDispatcher dispatcher = request.getRequestDispatcher(url);
		dispatcher.forward(request, response);
	}
	%>
	<font color="red"><%=error%></font>
	<h2>Add a new note for <%=userName%></h2>
	<form action="./AddNoteServlet" METHOD=GET>
		<table Style="border-collapse: separate; border-spacing: 4px;">
		<tr>
			<td>Enter the name of your note:</td>
			<td><input name="notename" type="text" size="20"></td>
		</tr>
		</table>
		<textarea id="note" name="note" rows="4" cols="50"></textarea>
		<input type="submit" value="Add Note to List">
	</form>
</body>
</html>