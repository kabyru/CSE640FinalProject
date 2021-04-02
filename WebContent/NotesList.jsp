<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.util.ArrayList" import="java.util.Iterator"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>My Notes List</title>
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
	
	<h2>Welcome, <%=userName%>! Here are your current notes:</h2>
	
	<font color="red"><%=error%></font>
	
	<form action="./AddNote.jsp">
		<input type="submit" value="Add a Note to the List">
	</form>
	
	<br>
	<table cellpadding="10" cellspacing="10" border="4px solid black" display="inline-block">
	<tr>
	<th>NOTE NAME</th>
	<th>NOTE</th>
	<th>YEAR</th>
	<th>MONTH</th>
	<th>DAY</th>
	<th>LAST MODIFIED</th>
	<th>UPDATE</th>
	</tr>
	<%
	Iterator<ArrayList<String>> iter = results.iterator();
	
	while (iter.hasNext())
	{
		ArrayList temp = (ArrayList) iter.next();
		
		out.print("<tr>");
		out.print("<form action=\"./UpdateNoteServlet\">");
		
		String noteNameParameter = temp.get(6) + "_input";
		
		out.print("<td>" + "<input name=\"" + noteNameParameter + "\" type=\"text\" size=\"20\" value = \"" + temp.get(6) + "\">" + "</td>");
		out.print("<td>" + "<textarea id=\""+ temp.get(6) +"\" name=\""+ temp.get(6) +"\" rows=\"4\" cols=\"50\">" + temp.get(1) + "</textarea></td>");
		out.print("<td>" + temp.get(2) + "</td>");
		out.print("<td>" + temp.get(3) + "</td>");
		out.print("<td>" + temp.get(4) + "</td>");
		out.print("<td>" + temp.get(5) + "</td>");
		out.print("<td>" + "<input type=\"submit\" value=\"Update Note\">" + "</td>");
		
		out.print("</form>");
		
		out.print("</tr>");
	}
	%>
	</table>
	
	<br>
	Filter the table by selecting a column and an order:
	<form action="./NotesRedirectServlet">
		<select name="sortchoice">
			<option value="notename">Note Name</option>
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
	<form action="./NotesRedirectServlet">
		<input name="searchterm" type="text" size="20">
		<input type="submit" value="Filter List">
	</form>
	
	<br>
	
	Select Note to Remove from the List:
	<form action="./RemoveNoteServlet">
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
		<input type="submit" value="Remove Selected Note from List">
	</form>
	
	<br>
	
	<form action="./GoBackToWelcomeServlet">
		<input type="submit" value="Go back to the Welcome page">
	</form>

</body>
</html>