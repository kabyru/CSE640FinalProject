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
	Object username = request.getAttribute("username");
	if (username != null)
	{
		userName = username.toString();
	}
	Class.forName("com.ibm.db2.jcc.DB2Driver");
	Object rss = request.getAttribute("results");
	ArrayList<ArrayList<String>> results = (ArrayList<ArrayList<String>>) rss;
	%>
	
	<h2>Welcome, <%=userName%>! Here are your current notes:</h2>
	
	<form action="./AddNote.jsp">
		<input type="submit" value="Add a Note to the List">
	</form>
	
	<br>
	
	<table cellpadding="10" cellspacing="10" border="4px solid black" display="inline-block">
	<tr>
	<th>ID</th>
	<th>NOTE</th>
	<th>YEAR</th>
	<th>MONTH</th>
	<th>DAY</th>
	<th>TIMECREATED</th>
	<th>NOTESNAME</th>
	</tr>
	<%
	Iterator<ArrayList<String>> iter = results.iterator();
	
	while (iter.hasNext())
	{
		ArrayList temp = (ArrayList) iter.next();
		
		out.print("<tr>");
		
		out.print("<td>" + temp.get(0) + "</td>");
		out.print("<td>" + temp.get(1) + "</td>");
		out.print("<td>" + temp.get(2) + "</td>");
		out.print("<td>" + temp.get(3) + "</td>");
		out.print("<td>" + temp.get(4) + "</td>");
		out.print("<td>" + temp.get(5) + "</td>");
		out.print("<td>" + temp.get(6) + "</td>");
		
		out.print("</tr>");
	}
	%>
	</table>
	
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

</body>
</html>