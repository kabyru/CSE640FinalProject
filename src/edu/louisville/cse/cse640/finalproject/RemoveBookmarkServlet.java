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
import edu.louisville.cse640.cotrollers.BookmarksController;

/**
 * Servlet implementation class RemoveBookmarkServlet
 */
@WebServlet("/RemoveBookmarkServlet")
public class RemoveBookmarkServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private static Connection            dbConnection	= null;
    private ConnectionPool				 pool			= null;
    private BookmarksController          bc				= null;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RemoveBookmarkServlet() {
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
		String bookmarkName = "";
		
		bookmarkName = request.getParameter("removeitem");
		if (bookmarkName == null || bookmarkName.length() == 0)
		{
			request.setAttribute("error", "Bookmark Name cannot be empty.");
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
			//At this point, we now have userName and bookmarkName. We can now call bookmarksController.
			if (bc.removeBookmark(userName,bookmarkName) != 1)
			{
				System.out.println("ERROR: There was an error removing the bookmark to the Database...");
			}
			pool.freeConnection(dbConnection);
		}
		catch (Exception e)
		{
			e.printStackTrace();
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
