<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>CSE 640 Final Project Welcome Page</title>
</head>
<body>
	<h1>Welcome to Kaleb Byrum's CSE 640 Final Project</h1>
	<h2>This web-app will interact with DB2 tables to store and manipulate notes and bookmarks.</h2>
	
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
		
		out.print("<form action=\"./LogOutServlet\" METHOD=GET>");
		out.print("<input type=\"submit\" value=\"Log out\">");
		out.print("</form>");
	}
	%>
	
</body>
</html>