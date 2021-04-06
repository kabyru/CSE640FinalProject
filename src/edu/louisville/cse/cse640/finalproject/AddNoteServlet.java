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

import edu.louisville.cse640.cotrollers.ConnectionPool;
import edu.louisville.cse640.cotrollers.NotesController;

/**
 * Servlet implementation class AddNoteServlet
 */
@WebServlet("/AddNoteServlet")
public class AddNoteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private static Connection            dbConnection	= null;
    private NotesController              nc				= null;
    private ConnectionPool				 pool			= null;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddNoteServlet() {
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
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		String url = "./NotesRedirectServlet";
		String userName = "";
		String note = "";
		String noteName = "";
		noteName = request.getParameter("notename");
		note = request.getParameter("note");
		
		if (noteName == null || noteName.length() == 0 || note == null || note.length() == 0)
		{
			url = "./AddNote.jsp";
			request.setAttribute("error", "Note content or Note Name cannot be empty.");
		}
		else
		{
			try
			{
				connect2database();
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
				//At this point, we now have userName, noteName, and note. We can now call notesController.
				if (nc.findNotesName(userName, noteName))
				{
					System.out.println("ERROR: Duplicate note names aren't allowed in the Database...");
					request.setAttribute("error", "ERROR: Duplicate note names aren't allowed in the Database...");
				}
				else
				{
					if (nc.insertNote(userName, note, noteName) != 1)
					{
						System.out.println("ERROR: There was an error adding the note to the Database...");
						request.setAttribute("error", "ERROR: There was an error adding the note to the Database...");
					}
				}
				
				pool.freeConnection(dbConnection);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher(url);
		dispatcher.forward(request, response);
	}
		


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
