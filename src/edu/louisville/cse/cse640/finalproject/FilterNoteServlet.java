package edu.louisville.cse.cse640.finalproject;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.louisville.cse640.cotrollers.DatabaseConnectionController;
import edu.louisville.cse640.cotrollers.NotesController;

/**
 * Servlet implementation class FilterNoteServlet
 */
@WebServlet("/FilterNoteServlet")
public class FilterNoteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private static Connection            dbConnection	= null;
    private DatabaseConnectionController dcc			= null;
    private NotesController              nc				= null;
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FilterNoteServlet() {
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
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		String url = "./NotesRedirectServlet";
		String userName = "";
		String searchTerm = "";
		
		searchTerm = request.getParameter("searchterm");
		if (searchTerm == null || searchTerm.length() == 0)
		{
			request.setAttribute("error", "Note content or Note Name cannot be empty.");
			RequestDispatcher dispatcher = request.getRequestDispatcher(url);
			dispatcher.forward(request, response);
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
		
		try
		{
			connect2database();
			//At this point, we now have userName and searchTerm. We can now call notesController.
			
			
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
