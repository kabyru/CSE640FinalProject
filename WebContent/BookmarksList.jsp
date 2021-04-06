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
	//If userName is null, then the user is NOT allowed to be on this page! We need to redirect them immediately.
	if (userName == null)
	{
		String url = "./Welcome.jsp";
		RequestDispatcher dispatcher = request.getRequestDispatcher(url);
		dispatcher.forward(request, response);
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
	<%
	//sortchoice and sortorder
	//sortchoice can either be "", "notename", "datetime"
	//sortorder can either be "", "DESC", "ASC"
	//Retrieve these variables from getAttributes and set the default "selected" as such.
	
	Object sortchoice = request.getAttribute("sortchoice");
	Object sortorder = request.getAttribute("sortorder");
	String sortChoice = "";
	String sortOrder = "";
	if (sortchoice != null)
	{
		sortChoice = sortchoice.toString();
	}
	if (sortorder != null)
	{
		sortOrder = (String) sortorder;
	}
	
	//selected = "selected" needs to be applied depending on the values of sortChoice and sortOrder
	out.print("<form action=\"./BookmarksRedirectServlet\">");
	out.print("<select name=\"sortchoice\">");
	
	if (sortChoice.equals(""))
	{
		out.print("<option value=\"bookmarkname\">Bookmark Name</option>");
		out.print("<option value=\"datetime\">Date and Time</option>");
	}
	else if (sortChoice.equals("bookmarkname"))
	{
		out.print("<option value=\"bookmarkname\" selected=\"selected\">Bookmark Name</option>");
		out.print("<option value=\"datetime\">Date and Time</option>");
	}
	else
	{
		out.print("<option value=\"bookmarkname\">Bookmark Name</option>");
		out.print("<option value=\"datetime\" selected=\"selected\">Date and Time</option>");
	}
	out.print("</select>");
	
	out.print("<select name=\"sortorder\">");
	if (sortOrder.equals(""))
	{
		out.print("<option value=\"DESC\">Descending</option>");
		out.print("<option value=\"ASC\">Ascending</option>");
	}
	else if (sortOrder.equals("ASC"))
	{
		out.print("<option value=\"DESC\">Descending</option>");
		out.print("<option value=\"ASC\" selected=\"selected\">Ascending</option>");
	}
	else
	{
		out.print("<option value=\"DESC\" selected=\"selected\">Descending</option>");
		out.print("<option value=\"ASC\">Ascending</option>");
	}
	out.print("</select>");
	out.print("<input type=\"submit\" value=\"Order List\">");
	out.print("</form>");
	%>
	
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