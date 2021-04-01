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
import edu.louisville.cse640.cotrollers.BookmarksController;

/**
 * Servlet implementation class AddBookmarkServlet
 */
@WebServlet("/AddBookmarkServlet")
public class AddBookmarkServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private static Connection            dbConnection	= null;
    private DatabaseConnectionController dcc			= null;
    private BookmarksController          bc				= null;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddBookmarkServlet() {
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
                bc = new BookmarksController(dbConnection);
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
		String url = "./BookmarksRedirectServlet";
		String userName = "";
		String bookmarkURL = "";
		String bookmarkName = "";
		bookmarkName = request.getParameter("bookmarkname");
		bookmarkURL = request.getParameter("bookmarkURL");
		//System.out.println(bookmarkURL.substring(0,8));
		//System.out.println(bookmarkURL.substring(0,7));
		
		if (bookmarkName == null || bookmarkName.length() == 0 || bookmarkURL == null || bookmarkURL.length() == 0)
		{
			url = "./AddBookmark.jsp";
			request.setAttribute("error", "Note content or Note Name cannot be empty.");
		}
		//First, ensure the given URL is a real website. // https://
		else if (!(bookmarkURL.substring(0,8).equals("https://")))
		{
			if (!(bookmarkURL.substring(0,7).equals("http://")))
			{
				url = "./AddBookmark.jsp";
				System.out.println("ERROR: The URL given is not a proper http:// or https:// URL.");
				request.setAttribute("error", "ERROR: The URL given is not a proper http:// or https:// URL.");
			}
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
				if (bc.findBookmarkName(userName, bookmarkName))
				{
					System.out.println("ERROR: Duplicate bookmark names aren't allowed in the Database...");
					url = "./AddBookmark.jsp";
					request.setAttribute("error", "ERROR: Duplicate bookmark names aren't allowed in the Database...");
				}
				else
				{
					if (bc.findBookmarkContent(userName,bookmarkURL))
					{
						System.out.println("ERROR: Duplicate bookmark URLs aren't allowed in the Database...");
						url = "./AddBookmark.jsp";
						request.setAttribute("error", "ERROR: Duplicate bookmark URLs aren't allowed in the Database...");
					}
					else
					{
						if (bc.insertBookmark(userName, bookmarkURL, bookmarkName) != 1)
						{
							System.out.println("ERROR: There was an error adding the bookmark to the Database...");
						}
					}
				}
				//At this point, we have userName, bookmarkName, and bookmarkURL. We can now call bookmarksController
				
				dcc.disconnectFromDatabase();
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
