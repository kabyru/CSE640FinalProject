package edu.louisville.cse.cse640.finalproject;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

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
 * Servlet implementation class UpdateNoteServlet
 */
@WebServlet("/UpdateNoteServlet")
public class UpdateNoteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private static Connection            dbConnection	= null;
    private DatabaseConnectionController dcc			= null;
    private NotesController              nc				= null;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateNoteServlet() {
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
		String noteName = "";
		String newNoteContent = "";
		
		//Idea: we get a list of NOTENAMEs for the userName, we then FOR LOOP through
		//to find a noteName that leads to a non-null note value.
		
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
			ResultSet rs;
			Statement st = dbConnection.createStatement();
			st = dbConnection.createStatement();
			String query = "SELECT NOTESNAME FROM NOTES_TABLE WHERE ID ='" + userName + "'";
			rs = st.executeQuery(query);
			
			while(rs.next())
			{
				if (request.getParameter(rs.getString(1)) != null && request.getParameter(rs.getString(1)).length() != 0)
				{
					noteName = rs.getString(1);
					newNoteContent = request.getParameter(rs.getString(1));
					break;
				}
			}
			
			//At this point, we have found the noteName relevant to the note we want to update.
			st.close();
			rs.close();
			
			if (nc.updateNoteContent(userName, noteName, newNoteContent) != 1)
			{
				System.out.println("ERROR: There was an error updating the database...");
			}
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
