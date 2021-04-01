<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.util.ArrayList" import="java.util.Iterator"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>My Bookmarks List</title>
</head>
<body>
	<%
	String userName = "";
	String error = "";
	Object username = request.getAttribute("username");
	Object errorMessage = request.getAttribute("error");
	if (username != null)
	{
		userName = username.toString();
	}
	if (errorMessage != null)
	{
		error = (String) errorMessage;
	}
	Class.forName("com.ibm.db2.jcc.DB2Driver");
	Object rss = request.getAttribute("results");
	ArrayList<ArrayList<String>> results = (ArrayList<ArrayList<String>>) rss;
	%>
	<h2>Welcome, <%=userName%>! Here are your current bookmarks:</h2>
	
	<font color="red"><%=error%></font>
	
	<form action="./AddBookmark.jsp">
		<input type="submit" value="Add a Bookmark to the List">
	</form>
	
	<br>
	<table cellpadding="10" cellspacing="10" border="4px solid black" display="inline-block">
	<tr>
	<th>BOOKMARK NAME</th>
	<th>URL</th>
	<th>YEAR</th>
	<th>MONTH</th>
	<th>DAY</th>
	<th>LAST MODIFIED</th>
	</tr>
	
	<%
	Iterator<ArrayList<String>> iter = results.iterator();
	
	while (iter.hasNext())
	{
		ArrayList temp = (ArrayList) iter.next();
		
		out.print("<tr>");
		
		out.print("<td>" + temp.get(6) + "</td>");
		out.print("<td>" + "<a href=\""+ temp.get(1) +"\">" + temp.get(1) + "</a>");
		out.print("<td>" + temp.get(2) + "</td>");
		out.print("<td>" + temp.get(3) + "</td>");
		out.print("<td>" + temp.get(4) + "</td>");
		out.print("<td>" + temp.get(5) + "</td>");
		
		out.print("</tr>");
	}
	%>
	</table>
	
	<br>
	
	Filter the table by selecting a column and an order:
	<form action="./BookmarksRedirectServlet">
		<select name="sortchoice">
			<option value="bookmarkname">Note Name</option>
			<option value="datetime">Date and Time</option>
		</select>
		<select name="sortorder">
			<option value="DESC">Descending</option>
			<option value="ASC">Ascending</option>
		</select>
		<input type="submit" value="Order List">
	</form>
	
	<br>
	
	Enter a term to filter the list. Filter with no keyword to reset the list:
	<form action="./BookmarksRedirectServlet">
		<input name="searchterm" type="text" size="20">
		<input type="submit" value="Filter List">
	</form>
	
	<br>
	
	Select Bookmark to Remove from the List:
	<form action="./RemoveBookmarkServlet">
		<select name="removeitem">
		<%
		iter = results.iterator();
		while (iter.hasNext())
		{
			ArrayList temp = (ArrayList) iter.next();
			out.print("<option value= \"" + temp.get(6) + "\">" + temp.get(6) + "</option>");
		}
		%>
		</select>
		<input type="submit" value="Remove Selected Bookmark from List">
	</form>
	
	<br>
	
	<form action="./GoBackToWelcomeServlet">
		<input type="submit" value="Go back to the Welcome page">
	</form>

</body>
</html>