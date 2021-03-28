package edu.louisville.cse.cse640.finalproject;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.louisville.cse640.cotrollers.DatabaseConnectionController;
import edu.louisville.cse640.cotrollers.NotesController;

/**
 * Servlet implementation class NotesServlet
 */
@WebServlet("/NotesServlet")
public class NotesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private static Connection            dbConnection	= null;
    private DatabaseConnectionController dcc			= null;
    private NotesController              nc				= null;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NotesServlet() {
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
		String url = "/Welcome.jsp";
		String user = request.getParameter("user");
		String noteNickname = request.getParameter("notenickname");
		String note = request.getParameter("note");
		
		if (user == null || user.length() == 0 || noteNickname == null || noteNickname.length() == 0 || note == null || note.length() == 0)
		{
			url = "/NotesServlet.jsp";
			request.setAttribute("error", "ERROR: One of the fields was empty.");
		}
		else
		{
			try
			{
				connect2database();
				int rc = nc.insertNote(user, note, noteNickname);
				System.out.println(rc);
			}
            catch (Exception e)
            {
                e.printStackTrace();
            } // end try catch
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
