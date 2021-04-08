package edu.louisville.cse.cse640.finalproject;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.louisville.cse640.cotrollers.ConnectionPool;
import edu.louisville.cse640.cotrollers.NotesController;

import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.ResultSet;

/**
 * Servlet implementation class NotesRedirectServlet
 */
@WebServlet("/NotesRedirectServlet")
public class NotesRedirectServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private static Connection            dbConnection	= null;
    private ConnectionPool				 pool			= null;
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
    	pool = ConnectionPool.getInstance("jdbc/COMPANY");
    	if (pool != null)
        {
    		dbConnection = pool.getConnection();
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
			String sortChoice;
			String sortOrder;
			searchTerm = request.getParameter("searchterm");
			sortChoice = request.getParameter("sortchoice");
			sortOrder = request.getParameter("sortorder");
			
			//If sortChoice and sortOrder are null, as in, no parameter is given, let's pull their stored values from Session.
			if (sortChoice == null && sortOrder == null)
			{
				Object sortChoiceTemp = session.getAttribute("sortchoice");
				if (sortChoiceTemp != null)
				{
					sortChoice = (String) sortChoiceTemp;
				}
				Object sortOrderTemp = session.getAttribute("sortorder");
				if (sortOrderTemp != null)
				{
					sortOrder = (String) sortOrderTemp;
				}
			}
			
			if (sortChoice != null && sortChoice.length() != 0)
			{
				session.setAttribute("sortchoice", sortChoice);
				request.setAttribute("sortchoice", sortChoice);
			}
			if (sortOrder != null && sortOrder.length() != 0)
			{
				session.setAttribute("sortorder", sortOrder);
				request.setAttribute("sortchoice", sortChoice);
			}
			
			ResultSet rs;
			Statement st = dbConnection.createStatement();
			
			if (sortChoice != null && sortChoice.length() != 0)
			{
				System.out.println(sortChoice);
			}
			else
			{
				System.out.println("sortChoice is NULL or length = 0");
			}
			
			if (sortOrder != null && sortOrder.length() != 0)
			{
				System.out.println(sortOrder);
			}
			else
			{
				System.out.println("sortOrder is NULL or length = 0");
			}
			
			if (searchTerm != null && searchTerm.length() != 0)
			{
				if (sortChoice != null && sortChoice.length() != 0 && sortOrder != null && sortOrder.length() != 0)
				{
					rs = nc.getFilteredNotes(userName, searchTerm, sortChoice, sortOrder);
					request.setAttribute("sortchoice", sortChoice);
					request.setAttribute("sortorder", sortOrder);
				}
				else
				{
					rs = nc.getFilteredNotes(userName, searchTerm);
				}
				
			}
			else if (sortChoice != null && sortChoice.length() != 0 && sortOrder != null && sortOrder.length() != 0)
			{
				rs = nc.getSortedNotes(userName, sortChoice, sortOrder);
				request.setAttribute("sortchoice", sortChoice);
				request.setAttribute("sortorder", sortOrder);
			}
			else
			{
				st = dbConnection.createStatement();
				String query = "SELECT * FROM NOTES_TABLE WHERE ID ='" + userName + "'";
				rs = st.executeQuery(query);
			}
			
			ArrayList<ArrayList<String>> biDemArrList = new ArrayList<ArrayList<String>>();
			
			
			while(rs.next())
			{
				ArrayList<String> temp = new ArrayList<String>();
				temp.add(rs.getString(1));
				temp.add(rs.getString(2));
				temp.add(rs.getString(3));
				temp.add(rs.getString(4));
				biDemArrList.add(temp);
			}
			
			
			request.setAttribute("results",biDemArrList);
			//rs.close();
			st.close();
			pool.freeConnection(dbConnection);
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