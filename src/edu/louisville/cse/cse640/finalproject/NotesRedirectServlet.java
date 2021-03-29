package edu.louisville.cse.cse640.finalproject;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
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
import java.util.ArrayList;
import java.util.List;
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
		String error = "";
		//out.println("You've made it to the Notes Servlet!");
		
		try
		{
			connect2database();
			
			Object errorMessage = request.getAttribute("error");
			if (errorMessage != null)
			{
				error = (String) errorMessage;
				request.setAttribute("error", error);
			}
			
			HttpSession session = request.getSession(true);
			if (session != null)
			{
				userName = (String) session.getAttribute("user");
				request.setAttribute("username", userName);
			}
			if (session == null)
			{
				System.out.println("Session is null!");
			}
			
			//The next piece of code depends on if a keyword was provided or not.
			String searchTerm = "";
			searchTerm = request.getParameter("searchterm");
			ResultSet rs;
			Statement st = dbConnection.createStatement();
			if (searchTerm == null || searchTerm.length() == 0)
			{
				st = dbConnection.createStatement();
				String query = "SELECT * FROM NOTES_TABLE WHERE ID ='" + userName + "'";
				rs = st.executeQuery(query);
			}
			else
			{
				rs = nc.getFilteredNotes(userName, searchTerm);
			}
			
			ArrayList<ArrayList<String>> biDemArrList = new ArrayList<ArrayList<String>>();
			
			
			while(rs.next())
			{
				ArrayList<String> temp = new ArrayList<String>();
				temp.add(rs.getString(1));
				temp.add(rs.getString(2));
				temp.add(String.valueOf(rs.getInt(3)));
				temp.add(String.valueOf(rs.getInt(4)));
				temp.add(String.valueOf(rs.getInt(5)));
				temp.add(rs.getString(6));
				temp.add(rs.getString(7));
				biDemArrList.add(temp);
			}
			
			
			request.setAttribute("results",biDemArrList);
			//rs.close();
			st.close();
			dcc.disconnectFromDatabase();
			String url = "/NotesList.jsp";
	        RequestDispatcher dispatcher = request.getRequestDispatcher(url);
	        dispatcher.forward(request, response);
			
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