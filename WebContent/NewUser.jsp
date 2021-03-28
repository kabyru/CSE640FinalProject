<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Register New User</title>
</head>
<body>
    <h1>Register New User</h1>
	<%
	String error_message = "";
	Object error = request.getAttribute("error");
	if (error != null)
		error_message = error.toString();
	%>
	<form action="./RegisterServlet" METHOD=GET>
        <table Style="border-collapse: separate; border-spacing: 4px;">
            <tr>
                <td>Enter your first name:</td>
                <td><input name="firstname" type="text" size="20"></td>
                <td style="color: red"><%=error_message%></td>
            </tr>
            <tr>
                <td>Enter your middle initial/name:</td>
                <td><input name="middlename" type="text" size="20"></td>
            </tr>
            <tr>
                <td>Enter your last name:</td>
                <td><input name="lastname" type="text" size="20"></td>
            </tr>
            <tr>
                <td>Enter your new user name:</td>
                <td><input name="username" type="text" size="20"></td>
            </tr>
            <tr>
                <td>Enter your new password:</td>
                <td><input name="pw" type="password" size="20"></td>
            </tr>
            <tr>
				<td></td>
				<td><input type="submit" value="Register"></td>
				<td></td>                
            </tr>
        </table>
	</form>
</body>
</html>