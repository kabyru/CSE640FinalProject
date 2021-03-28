package edu.louisville.cse.cse640.finalproject;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.louisville.cse640.cotrollers.DatabaseConnectionController;
import edu.louisville.cse640.cotrollers.NotesController;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;

/**
 * Servlet implementation class NotesRedirectServlet
 */
@WebServlet("/NotesRedirectServlet")
public class NotesRedirectServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private static Connection            dbConnection	= null;
    private DatabaseConnectionController dcc			= null;
    private NotesController              nc				= null;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NotesRedirectServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    private void connect2database()
    {
        dcc = new DatabaseConnectionController("COMPANY");
        if (dcc != null)
        {
            dbConnection = dcc.getDbConnection();
            if (dbConnection == null)
            {
                System.out.println("Connection Failed");
            }
            else
            {
                nc = new NotesController(dbConnection);
            } // end if
        }
        else
        {
            System.out.println("Driver Failed");
        }
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		String userName = "";
		//out.println("You've made it to the Notes Servlet!");
		
		try
		{
			connect2database();
			HttpSession session = request.getSession(true);
			if (session != null)
			{
				userName = (String) session.getAttribute("user");
			}
			if (session == null)
			{
				System.out.println("Session is null!");
			}
			Statement st = dbConnection.createStatement();
			String query = "SELECT * FROM NOTES_TABLE WHERE ID ='" + userName + "'";
			ResultSet rs = st.executeQuery(query);
			
			out.print("Welcome, " + userName + "! Here are your current notes:\n");
			
			out.print("<table cellpadding=\"10\" cellspacing=\"10\" border=\"4px solid black\" display=\"inline-block\">");
			
			out.print("<tr>");
			out.print("<th>ID</th>");
			out.print("<th>NOTE</th>");
			out.print("<th>YEAR</th>");
			out.print("<th>MONTH</th>");
			out.print("<th>DAY</th>");
			out.print("<th>TIMECREATED</th>");
			out.print("<th>NOTESNAME</th>");
			out.print("</tr>");
			
			while (rs.next())
			{
				out.print("<tr>");
				
				out.print("<td>" + rs.getString(1) + "</td>");
				out.print("<td>" + rs.getString(2) + "</td>");
				out.print("<td>" + rs.getInt(3) + "</td>");
				out.print("<td>" + rs.getInt(4) + "</td>");
				out.print("<td>" + rs.getInt(5) + "</td>");
				out.print("<td>" + rs.getString(6) + "</td>");
				out.print("<td>" + rs.getString(7) + "</td>");
				
				out.print("</tr>");
			}
			out.print("</table>");
			rs.close();
			st.close();
			dbConnection.close();
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}