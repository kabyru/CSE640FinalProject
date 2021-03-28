<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>My Example Welcome Page</title>
</head>
<body>
	<h1>Welcome to the login page!</h1>
	<h2>This web-app will test connection with the USERS DB2 database table.</h2>
	
	<%
	String fullname = "";
	String message = "";
	boolean loggedIn = false;
	Object name = request.getAttribute("fullname");
	if (name != null)
	{
		fullname = name.toString();
		message = "You have been logged in, " + fullname + "!";
		loggedIn = true;
	}
	%>
	
	<%=message%>
	
	<%
	if (!loggedIn)
	{
		out.print("<form action=\"./WelcomeRedirectServlet\" METHOD=GET>");
		out.print("<table Style=\"border-collapse: separate; border-spacing: 4px;\">");
		out.print("<tr>");
		out.print("<td><input type=\"submit\" value=\"Login\"></td>");
		out.print("</tr>");
		out.print("</table>");
		out.print("</form>");	
	}
	%>
	<%
	if (loggedIn)
	{
		out.print("Please make a selection of which service to start:");
		out.print("<form action=\"./NotesRedirectServlet\" METHOD=GET>");
		out.print("<input type=\"submit\" value=\"My Saved Notes\">");
		out.print("</form>");
		
		out.print("<form action=\"./BookmarksRedirectServlet\" METHOD=GET>");
		out.print("<input type=\"submit\" value=\"My Saved Bookmarks\">");
		out.print("</form>");
	}
	%>
	
</body>
</html>